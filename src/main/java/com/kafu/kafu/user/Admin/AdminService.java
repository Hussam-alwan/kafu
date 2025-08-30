package com.kafu.kafu.user.Admin;

import com.kafu.kafu.problem.ProblemRepository;
import com.kafu.kafu.problem.ProblemStatus;
import com.kafu.kafu.user.UserRepository;
import com.kafu.kafu.gov.GovRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final UserRepository userRepository;
    private final GovRepository govRepository;
    private final ProblemRepository problemRepository;
    
    public AdminStatisticsDTO getSystemStatistics() {
        AdminStatisticsDTO stats = new AdminStatisticsDTO();
        Long govCount = govRepository.countAllGov();
        stats.setTotalProblems(problemRepository.countAllProblems());
        stats.setTotalCompletedProblems(problemRepository.countProblemByStatus(ProblemStatus.RESOLVED));
        stats.setTotalGovs(govCount);
        stats.setTotalUsers(userRepository.countAllUsers());
        
        return stats;
    }
}