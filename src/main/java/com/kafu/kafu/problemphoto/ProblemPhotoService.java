package com.kafu.kafu.problemphoto;

import com.kafu.kafu.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemPhotoService {
    private final ProblemPhotoRepository problemPhotoRepository;
    private final ProblemPhotoMapper problemPhotoMapper;
    private final ProblemService problemService;

    public Page<ProblemPhotoDTO> findAll(Pageable pageable) {
        return problemPhotoRepository.findAll(pageable)
                .map(problemPhotoMapper::toDTO);
    }

    public List<ProblemPhotoDTO> findByProblemId(Long problemId) {
        return problemPhotoRepository.findByProblemId(problemId)
                .stream()
                .map(problemPhotoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProblemPhotoDTO findById(Long id) {
        return problemPhotoRepository.findById(id)
                .map(problemPhotoMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem photo not found"));
    }

    @Transactional
    public ProblemPhotoDTO create(ProblemPhotoDTO problemPhotoDTO) {
        if (problemPhotoDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new problem photo cannot already have an ID");
        }

        // Verify that the problem exists
        problemService.findById(problemPhotoDTO.getProblemId());

        problemPhotoDTO.setPhotoDate(LocalDateTime.now());
        
        ProblemPhoto problemPhoto = problemPhotoMapper.toEntity(problemPhotoDTO);
        problemPhoto = problemPhotoRepository.save(problemPhoto);
        return problemPhotoMapper.toDTO(problemPhoto);
    }

    @Transactional
    public ProblemPhotoDTO update(Long id, ProblemPhotoDTO problemPhotoDTO) {
        ProblemPhoto problemPhoto = problemPhotoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem photo not found"));

        // Verify that the problem exists if it's being updated
        if (problemPhotoDTO.getProblemId() != null) {
            problemService.findById(problemPhotoDTO.getProblemId());
        }

        problemPhotoMapper.updateEntity(problemPhoto, problemPhotoDTO);
        problemPhoto = problemPhotoRepository.save(problemPhoto);
        return problemPhotoMapper.toDTO(problemPhoto);
    }

    @Transactional
    public void delete(Long id) {
        problemPhotoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem photo not found"));
        
        problemPhotoRepository.deleteById(id);
    }
}
