package com.aernaur.votingSystem.repository.specifictaions;

import com.aernaur.votingSystem.entity.Person;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class PersonSpecification {

    public static Specification<Person> withId(UUID personId) {
        return (root, query, criteriaBuilder) -> personId != null ? criteriaBuilder.equal(root.get("id"), personId) : null;
    }

    public static Specification<Person> nameLike(String name) {
        return (root, query, criteriaBuilder) -> StringUtils.hasText(name) ? criteriaBuilder.like(root.get("name"), name) : null;
    }
}
