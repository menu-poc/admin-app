package br.com.redhat.application.validation;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("errors")
@RegisterForReflection
public class ErrorResponse {

    private List<String> body;

    public ErrorResponse() {
        this.body = new LinkedList<>();
    }

    public ErrorResponse(String error) {
        this();
        this.body.add(error);
    }

    public ErrorResponse(List<String> errors) {
        this.body = errors;
    }
}
