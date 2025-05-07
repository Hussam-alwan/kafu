package com.kafu.kafu.problemcategory;

import com.kafu.kafu.problemcategory.dto.ProblemCategorySearchCriteria;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ProblemCategorySpecification {
    
    public static Specification<ProblemCategory> withSearchCriteria(ProblemCategorySearchCriteria criteria) {
        return (Root<ProblemCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
                String searchPattern = "%" + criteria.getName().toLowerCase() + "%";
                predicate = cb.and(predicate,
                    cb.like(cb.lower(root.get("name")), searchPattern)
                );
            }
            
            if (criteria.getGovId() != null) {
                Join<Object, Object> govJoin = root.join("gov");
                predicate = cb.and(predicate, cb.equal(govJoin.get("id"), criteria.getGovId()));
            }
            
            if (criteria.getGovName() != null && !criteria.getGovName().trim().isEmpty()) {
                Join<Object, Object> govJoin = root.join("gov");
                String searchPattern = "%" + criteria.getGovName().toLowerCase() + "%";
                predicate = cb.and(predicate,
                    cb.like(cb.lower(govJoin.get("name")), searchPattern)
                );
            }
            
            return predicate;
        };
    }
}