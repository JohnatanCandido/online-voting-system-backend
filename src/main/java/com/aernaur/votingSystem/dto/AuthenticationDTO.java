package com.aernaur.votingSystem.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationDTO(@NotEmpty
                                String username,
                                @NotEmpty
                                String password) {}
