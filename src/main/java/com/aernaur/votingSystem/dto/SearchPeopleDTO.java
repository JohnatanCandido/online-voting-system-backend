package com.aernaur.votingSystem.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record SearchPeopleDTO(UUID personId,
                              String name,
                              LocalDate birthDate,
                              UUID currentPartyId)
        implements Serializable {}
