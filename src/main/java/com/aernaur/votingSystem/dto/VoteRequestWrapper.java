package com.aernaur.votingSystem.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record VoteRequestWrapper(@NotEmpty List<VoteRequestDTO> votes) {}
