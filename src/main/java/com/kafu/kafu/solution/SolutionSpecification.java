package com.kafu.kafu.solution;

import com.kafu.kafu.solution.dto.SolutionSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class SolutionSpecification {
    public static Specification<Solution> withSearchCriteria(SolutionSearchCriteria criteria) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();
            if (criteria.getDescription() != null && !criteria.getDescription().isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("description")), "%" + criteria.getDescription().toLowerCase() + "%"));
            }
            if (criteria.getProblemId() != null) {
                p = cb.and(p, cb.equal(root.get("problem").get("id"), criteria.getProblemId()));
            }
            if (criteria.getProposedByUserId() != null) {
                p = cb.and(p, cb.equal(root.get("proposedByUserId").get("id"), criteria.getProposedByUserId()));
            }
            if (criteria.getAcceptedByUserId() != null) {
                p = cb.and(p, cb.equal(root.get("acceptedByUserId").get("id"), criteria.getAcceptedByUserId()));
            }
            return p;
        };
    }
}
