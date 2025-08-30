package com.kafu.kafu.gov;

import com.kafu.kafu.address.Address;
import com.kafu.kafu.address.AddressService;
import com.kafu.kafu.exception.ApplicationErrorEnum;
import com.kafu.kafu.exception.BusinessException;
import com.kafu.kafu.gov.DTO.GovChartDTO;
import com.kafu.kafu.gov.DTO.GovDTO;
import com.kafu.kafu.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GovService {
    private final GovRepository govRepository;
    private final AddressService addressService;
    private final S3Service s3Service;

    public List<Gov> findAll() {
        return govRepository.findAll();
    }

    public Gov findById(Long id) {
        return govRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.GOV_NOT_FOUND));
    }

    @Transactional
    public Gov create(GovDTO govDTO) {
        if (govDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new gov cannot already have an ID");
        }

        if (govRepository.existsByEmail(govDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        Gov gov = GovMapper.toEntity(govDTO);

        if (govDTO.getAddressId() != null) {
            var address = addressService.findById(govDTO.getAddressId());
            gov.setAddress(address);
        }

        // Set parent gov if provided
        if (govDTO.getParentGovId() != null) {
            var parentGov = findById(govDTO.getParentGovId());
            // Check if the parent is not a top parent(ministry)
            if (parentGov.getParentGov() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set a parent gov that already has a parent,multi level hierarchy is not allowed");
            }
            gov.setParentGov(parentGov);
        }

        gov = govRepository.save(gov);
        return gov;
    }

    @Transactional
    public Gov update(Long id, GovDTO govDTO) {
        Gov gov = govRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.GOV_NOT_FOUND));

        if (govDTO.getEmail() != null && !govDTO.getEmail().equals(gov.getEmail()) && 
            govRepository.existsByEmail(govDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Update the address if provided
        if (govDTO.getAddressId() != null) {
            Address address = addressService.findById(govDTO.getAddressId());
            gov.setAddress(address);
        }

        // Update parent gov if provided
        if (govDTO.getParentGovId() != null) {
            var parentGov = findById(govDTO.getParentGovId());
            // Check if the parent is not a top parent(ministry)
            if (parentGov.getParentGov() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set a parent gov that already has a parent,multi level hierarchy is not allowed");
            }
            gov.setParentGov(parentGov);
        }

        GovMapper.updateEntity(gov, govDTO);
        gov = govRepository.save(gov);
        return gov;
    }

    @Transactional
    public void delete(Long id) {
        govRepository.findById(id)
                .orElseThrow((() -> new BusinessException(ApplicationErrorEnum.GOV_NOT_FOUND)));
        
        try {
            govRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete gov as it is being referenced by other entities");
        }
    }


    public String uploadLogo(Long govId, String contentType) {

        if (!isValidPhotoContentType(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid file type. Allowed types: image/jpeg,image/png,image/jpg");
        }

        Gov gov = findById(govId);
        String key = generateProfilePhotoKey(govId);

        gov.setLogoUrl(key);
        save(gov);

        return s3Service.generatePresignedUrl(contentType, key);

    }
    public void save(Gov gov)
    {
        govRepository.save(gov);
    }

    private String generateProfilePhotoKey(Long govId) {
        return String.format("govs/%d/logo/%s", govId, UUID.randomUUID());
    }

    private boolean isValidPhotoContentType(String contentType) {
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/jpg")
        );
    }

    public Gov replaceUrlsWithPresigned(Gov gov)
    {
        if (gov.getLogoUrl() != null)
            gov.setLogoUrl(s3Service.generatePresignedGetUrl(gov.getLogoUrl()));
        return gov;
    }

    // get the chart data for the current gov and year
    //hussam

    public List<GovChartDTO> getGovStatistics(Long govId, Integer year) {
        // Verify gov exists
        findById(govId);
        
        // Get data from repositories
        List<Object[]> receivedProblemsData = govRepository.countReceivedProblemsByMonth(govId, year);
        List<Object[]> completedProblemsData = govRepository.countCompletedProblemsByMonth(govId, year);
        List<Object[]> auctionsData = govRepository.countAuctionsByMonth(govId, year);
        
        // Transform to DTOs
        return createMonthlyStatistics(receivedProblemsData, completedProblemsData, auctionsData);
    }

    public GovChartDTO getYearlyStatistics(Long govId, Integer year) {
        // Verify gov exists
        findById(govId);
        
        // Get yearly totals
        Long totalReceived = govRepository.countReceivedProblemsByYear(govId, year);
        Long totalCompleted = govRepository.countCompletedProblemsByYear(govId, year);
        Long totalAuctions = govRepository.countAuctionsByYear(govId, year);
        
        GovChartDTO yearlyStats = new GovChartDTO();
        yearlyStats.setMonth("Total " + year);
        yearlyStats.setReceivedProblems(totalReceived != null ? totalReceived.intValue() : 0);
        yearlyStats.setCompletedProblems(totalCompleted != null ? totalCompleted.intValue() : 0);
        yearlyStats.setAuctionedProblmes(totalAuctions != null ? totalAuctions.intValue() : 0);
        
        return yearlyStats;
    }

    private List<GovChartDTO> createMonthlyStatistics(
        List<Object[]> receivedProblemsData, 
        List<Object[]> completedProblemsData, 
        List<Object[]> auctionsData) {
        
        // Create map for each month with default values
        Map<Integer, GovChartDTO> monthlyStats = getIntegerGovChartDTOMap();

        // Fill in actual data
        receivedProblemsData.forEach(data -> {
            Integer month = (Integer) data[0];
            Long count = (Long) data[1];
            monthlyStats.get(month).setReceivedProblems(count.intValue());
        });
        
        completedProblemsData.forEach(data -> {
            Integer month = (Integer) data[0];
            Long count = (Long) data[1];
            monthlyStats.get(month).setCompletedProblems(count.intValue());
        });
        
        auctionsData.forEach(data -> {
            Integer month = (Integer) data[0];
            Long count = (Long) data[1];
            monthlyStats.get(month).setAuctionedProblmes(count.intValue());
        });
        
        return new ArrayList<>(monthlyStats.values());
    }

    private static Map<Integer, GovChartDTO> getIntegerGovChartDTOMap() {
        Map<Integer, GovChartDTO> monthlyStats = new HashMap<>();
        String[] months = {"January", "February", "March", "April", "May", "June",
                          "July", "August", "September", "October", "November", "December"};

        for (int i = 1; i <= 12; i++) {
            GovChartDTO dto = new GovChartDTO();
            dto.setMonth(months[i-1]);
            dto.setReceivedProblems(0);
            dto.setCompletedProblems(0);
            dto.setAuctionedProblmes(0);
            monthlyStats.put(i, dto);
        }
        return monthlyStats;
    }
}
