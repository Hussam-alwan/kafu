package com.kafu.kafu.problemcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemCategoryRepository extends JpaRepository<ProblemCategory, Long>, JpaSpecificationExecutor<ProblemCategory> {
    boolean existsByNameAndGovId(String name, Long govId);
}
