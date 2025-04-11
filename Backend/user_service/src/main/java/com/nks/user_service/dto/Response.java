package com.nks.user_service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Response {

    private final String message;
    private final String status;

    @JsonCreator
    public Response(@JsonProperty("message") String message,
                    @JsonProperty("status") String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
