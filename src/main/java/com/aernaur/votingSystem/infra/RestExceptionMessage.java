package com.aernaur.votingSystem.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class RestExceptionMessage implements Serializable {

    private final String message;
    private final List<ExceptionDetail> details;

    public RestExceptionMessage(String message) {
        this.message = message;
        this.details = null;
    }

    public record ExceptionDetail(String field, String detail) {}
}
