package com.example.heecoffee.Repository.Specification;

import com.example.heecoffee.Model.Product;
import com.example.heecoffee.Model.Type;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> name(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + name + "%");
        });
    }

    public static Specification<Product> type(String typeName) {
        return (((root, query, criteriaBuilder) -> {
            if (typeName == null || typeName.isEmpty()) {
                return null;
            }
            Join<Product, Type> typeJoin = root.join("types", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(typeJoin.get("types")), "%" + typeName.toLowerCase() + "%");
        }));
    }

    public static Specification<Product> typeId(Integer typeId) {
        return (((root, query, criteriaBuilder) -> {
            if (typeId == null || typeId <= 0) {
                return null;
            }
            Join<Product, Type> typeJoin = root.join("types", JoinType.INNER);
            return criteriaBuilder.equal(typeJoin.get("id"), typeId);
        }));
    }
}
