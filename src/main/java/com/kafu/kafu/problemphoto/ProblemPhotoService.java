package com.kafu.kafu.problemphoto;

import com.kafu.kafu.exception.ApplicationErrorEnum;
import com.kafu.kafu.exception.BusinessException;
import com.kafu.kafu.problem.Problem;
import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.problemprogress.ProblemProgress;
import com.kafu.kafu.problemprogress.ProblemProgressService;
import com.kafu.kafu.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemPhotoService {
    private final ProblemPhotoRepository problemPhotoRepository;
    private final ProblemService problemService;
    private final ProblemProgressService problemProgressService;
    private final S3Service s3Service;

    public ProblemPhoto findById(Long id) {
        return problemPhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.PHOTO_NOT_FOUND));
    }

    public ProblemPhoto findByIdWithS3Url(Long id) {
        ProblemPhoto photo = findById(id);
        String presignedUrl = s3Service.generatePresignedGetUrl(photo.getS3Key());
        photo.setS3Key(presignedUrl);
        return photo;
    }

    public List<ProblemPhoto> findByProblemId(Long problemId) {
        return problemPhotoRepository.findByProblem_Id(problemId);
    }
    public List<ProblemPhoto> findByProblemIdWithS3Url(Long problemId) {
        List<ProblemPhoto> photos = findByProblemId(problemId);

        // Generate presigned URLs for all photos
        photos.forEach(photo -> {
            String presignedUrl = s3Service.generatePresignedGetUrl(photo.getS3Key());
            photo.setS3Key(presignedUrl);
        });
        return photos;
    }

    @Transactional
    public List<ProblemPhotoDTO> createPhotos(Long problemId, Long progressId, Integer count, String contentType) {
        Problem problem = problemService.findById(problemId);
        List<ProblemPhotoDTO> photos = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            String key = s3Service.generateUniqueKey();
            String presignedUrl = s3Service.generatePresignedUrl(contentType,key);
        
            ProblemPhoto photo = new ProblemPhoto();
            photo.setProblem(problem);
            photo.setS3Key(key);
            photo.setPhotoDate(LocalDateTime.now());
        
            if (progressId != null) {
                ProblemProgress problemProgress = problemProgressService.findById(progressId);
                photo.setProgress(problemProgress);
            }
        
            photo = problemPhotoRepository.save(photo);
        
            ProblemPhotoDTO dto = ProblemPhotoMapper.toDTO(photo);
            dto.setPresignedUrl(presignedUrl);
            photos.add(dto);
        }
    
        return photos;
    }

    @Transactional
    public void deleteById(Long id) {
        ProblemPhoto photo = problemPhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.PHOTO_NOT_FOUND));
        
        s3Service.deleteObject(photo.getS3Key());

        // Delete from database
        problemPhotoRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByProblemId(Long problemId) {
        List<ProblemPhoto> photos = problemPhotoRepository.findByProblem_Id(problemId);
        
        // Delete all photos from S3
        for (ProblemPhoto photo : photos) {
            s3Service.deleteObject(photo.getS3Key());
        }
        
        // Delete all from database
        problemPhotoRepository.deleteByProblem_Id(problemId);
    }
}
