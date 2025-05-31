package com.aernaur.votingSystem.entity.types;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRole() {
        return "ROLE_" + roleName;
    }
}
