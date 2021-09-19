package com.app.rest.model;

import com.app.util.property.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

@JsonInclude(Include.NON_NULL)
public class ServiceResponse {

    @JsonProperty("status")
    private int status;

    @JsonProperty("description")
    private String description;

    public ServiceResponse() {
        // Default is successful
        status = HttpStatus.OK.value();
        description = Constants.STATUS_SUCCESS;
    }

    public ServiceResponse(int status, String description) {
        setStatus(status);
        setDescription(description);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
