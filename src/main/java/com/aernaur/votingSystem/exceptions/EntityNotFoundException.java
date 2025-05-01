package com.aernaur.votingSystem.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entity, UUID id) {
        super(String.format("No %s found with id: %s", entity, id));
    }
}
