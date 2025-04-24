package com.kafu.kafu.problem;

import com.kafu.kafu.problem.dto.ProblemSearchCriteria;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ProblemSpecification {
    
    public static Specification<Problem> withSearchCriteria(ProblemSearchCriteria criteria) {
        return (Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (criteria.getSearchText() != null && !criteria.getSearchText().trim().isEmpty()) {
                String searchPattern = "%" + criteria.getSearchText().toLowerCase() + "%";
                predicate = cb.and(predicate,
                    cb.or(
                        cb.like(cb.lower(root.get("title")), searchPattern),
                        cb.like(cb.lower(root.get("description")), searchPattern)
                    )
                );
            }

            if (criteria.getForContribution() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("forContribution"), criteria.getForContribution()));
            }

            if (criteria.getForDonation() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("forDonation"), criteria.getForDonation()));
            }

            if (criteria.getCity() != null) {
                Join<Object, Object> addressJoin = root.join("address");
                predicate = cb.and(predicate, cb.equal(addressJoin.get("city"), criteria.getCity()));
            }

            if (criteria.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getCategoryId() != null) {
                Join<Object, Object> categoryJoin = root.join("category");
                predicate = cb.and(predicate, cb.equal(categoryJoin.get("id"), criteria.getCategoryId()));
            }

            return predicate;
        };
    }
}
