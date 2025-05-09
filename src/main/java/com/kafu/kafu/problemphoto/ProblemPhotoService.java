package com.kafu.kafu.problemphoto;

import com.kafu.kafu.problem.Problem;
import com.kafu.kafu.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemPhotoService {
    private final ProblemPhotoRepository problemPhotoRepository;
    private final ProblemService problemService;

    public List<ProblemPhoto> findByProblemId(Long problemId) {
        return problemPhotoRepository.findByProblemId(problemId);
    }

    public ProblemPhoto findById(Long id) {
        ProblemPhoto photo = problemPhotoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem photo not found"));
        return photo;
    }

    public void validateProblemId(ProblemPhoto photo, Long problemId) {
        if (!photo.getProblem().getId().equals(problemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem photo not found for this problem");
        }
    }

    public ProblemPhoto findByIdAndValidateProblem(Long id, Long problemId) {
        ProblemPhoto photo = findById(id);
        validateProblemId(photo, problemId);
        return photo;
    }

    @Transactional
    public ProblemPhoto create(ProblemPhotoDTO dto) {
        if (dto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new problem photo cannot already have an ID");
        }

        Problem problem = problemService.findById(dto.getProblemId());
        
        ProblemPhoto photo = new ProblemPhoto();
        photo.setProblem(problem);
        photo.setPhotoUrl(dto.getPhotoUrl());
        photo.setPhotoDate(LocalDateTime.now());
        
        return problemPhotoRepository.save(photo);
    }

    @Transactional
    public ProblemPhoto update(Long id, ProblemPhotoDTO dto) {
        ProblemPhoto photo = findByIdAndValidateProblem(id, dto.getProblemId());
        
        if (dto.getPhotoUrl() != null) {
            photo.setPhotoUrl(dto.getPhotoUrl());
        }
        
        return problemPhotoRepository.save(photo);
    }

    @Transactional
    public void deleteByIdAndProblemId(Long id, Long problemId) {
        ProblemPhoto photo = findByIdAndValidateProblem(id, problemId);
        problemPhotoRepository.delete(photo);
    }
}
