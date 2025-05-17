package com.kafu.kafu.donation;

import com.kafu.kafu.exception.ApplicationErrorEnum;
import com.kafu.kafu.exception.BusinessException;
import com.kafu.kafu.payment.PaymentMethod;
import com.kafu.kafu.payment.PaymentService;
import com.kafu.kafu.payment.WebhookEvent;
import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationService {
    
    private final DonationRepository donationRepository;
    private final UserService userService;
    private final ProblemService problemService;
    private final Map<PaymentMethod, PaymentService> paymentServices;

    public Page<Donation> findAll(Pageable pageable) {
        return donationRepository.findAll(pageable);
    }

    public Page<Donation> findByProblemId(Long problemId, Pageable pageable) {
        return donationRepository.findByProblem_Id(problemId,pageable);
    }

    public List<Donation> findProblemDonationsForCurrentUser(Long problemId) {
        Long donorId = userService.getCurrentUser().getId();
        return donationRepository.findByDonor_IdAndProblem_Id(donorId,problemId);
    }

    public Donation findById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.DONATION_NOT_FOUND));
    }

    public void donate(DonationDTO donationDTO) {

        PaymentService service = paymentServices.get(donationDTO.getPaymentMethod());
        if (service == null) {
            throw new RuntimeException("Unsupported payment method");
        }

        // Check for duplicate order
        Optional<Donation> existingOrder = donationRepository.findByIdempotencyKey(donationDTO.getIdempotencyKey());
        if (existingOrder.isPresent()) {
            throw new RuntimeException("Idempotency Key has been processed before");
        }

        // Save order to DB
        Donation donation = new Donation();
        donation.setIdempotencyKey(donationDTO.getIdempotencyKey());
        donation.setAmount(donationDTO.getAmount());
        donation.setCurrency(donationDTO.getCurrency());
        donation.setStatus(DonationStatus.CREATED);
        donation.setPaymentTransactionId(donationDTO.getPaymentTransactionId());
        donation.setPaymentMethod(donationDTO.getPaymentMethod());
        donation.setIsAnonymous(donationDTO.getIsAnonymous());

        donation.setProblem(problemService.findById(donationDTO.getProblemId()));
        donation.setDonor(userService.getCurrentUser());
        donation.setDonationDate(LocalDateTime.now());
        donationRepository.save(donation);

    }

    public void updateStatus(WebhookEvent event) {
        Donation donation = donationRepository.findByPaymentTransactionId(event.getGatewayOrderId())
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.DONATION_NOT_FOUND));
        donation.setStatus(event.getSuccess());
        donationRepository.save(donation);
    }
}
