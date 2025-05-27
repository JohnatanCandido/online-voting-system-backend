package com.aernaur.votingSystem.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public record VoteRequestDTO(@NotNull
                             UUID subElectionId,
                             Integer candidateNumber) implements Serializable {}
