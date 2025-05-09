package com.kafu.kafu.problemprogress;

import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.problemphoto.ProblemPhoto;
import com.kafu.kafu.problemphoto.ProblemPhotoService;
import com.kafu.kafu.problemprogress.dto.ProblemProgressDTO;
import com.kafu.kafu.solution.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemProgressService {
    private final ProblemProgressRepository problemProgressRepository;
    private final ProblemService problemService;
    private final SolutionService solutionService;
    private final ProblemPhotoService problemPhotoService;

    public List<ProblemProgress> findByProblemId(Long problemId) {
        return problemProgressRepository.findByProblemId(problemId);
    }

    public ProblemProgress findByIdAndProblemId(Long id, Long problemId) {
        return problemProgressRepository.findByIdAndProblemId(id, problemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found"));
    }

    public ProblemProgress findById(Long id) {
        return problemProgressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found"));
    }

    public void validateProblemId(ProblemProgress progress, Long problemId) {
        if (!progress.getProblem().getId().equals(problemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found for this problem");
        }
    }

    public ProblemProgress findByIdAndValidateProblem(Long id, Long problemId) {
        ProblemProgress progress = findById(id);
        validateProblemId(progress, problemId);
        return progress;
    }

    @Transactional
    public void deleteByIdAndProblemId(Long id, Long problemId) {
        ProblemProgress progress = findByIdAndValidateProblem(id, problemId);
        problemProgressRepository.deleteById(id);
    }

    @Transactional
    public ProblemProgress create(ProblemProgressDTO dto) {
        ProblemProgress progress = new ProblemProgress();
        progress.setProblem(problemService.findById(dto.getProblemId()));
        progress.setSolution(solutionService.findById(dto.getSolutionId()));
        progress.setPercentage(dto.getPercentage());
        progress.setComment(dto.getComment());
        progress.setProgressDate(LocalDateTime.now());
        
        progress = problemProgressRepository.save(progress);
        
        if (dto.getPhotoIds() != null && !dto.getPhotoIds().isEmpty()) {
            for (Long photoId : dto.getPhotoIds()) {
                ProblemPhoto photo = problemPhotoService.findById(photoId);
                photo.setProgress(progress);
                progress.getPhotos().add(photo);
            }
        }
        
        return progress;
    }

    @Transactional
    public ProblemProgress update(Long id, ProblemProgressDTO dto) {
        ProblemProgress progress = findById(id);
        progress.setPercentage(dto.getPercentage());
        progress.setComment(dto.getComment());
        
        if (dto.getPhotoIds() != null) {
            // Clear existing photos
            progress.getPhotos().forEach(photo -> photo.setProgress(null));
            progress.getPhotos().clear();
            
            // Add new photos
            for (Long photoId : dto.getPhotoIds()) {
                ProblemPhoto photo = problemPhotoService.findById(photoId);
                photo.setProgress(progress);
                progress.getPhotos().add(photo);
            }
        }
        
        return problemProgressRepository.save(progress);
    }

    @Transactional
    public void delete(Long id) {
        problemProgressRepository.deleteById(id);
    }
}