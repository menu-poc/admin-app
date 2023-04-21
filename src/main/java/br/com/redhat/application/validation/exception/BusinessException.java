package br.com.redhat.application.validation.exception;

import static java.util.Collections.singletonList;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BusinessException extends RuntimeException {
    private final int code;
    private final List<String> messages;
  
    public BusinessException(int code, String message) {
      super(message);
      this.code = code;
      this.messages = new LinkedList<>(singletonList(message));
    }
  
    public BusinessException(int code, List<String> messages) {
      super(String.join(", ", messages));
      this.code = code;
      this.messages = messages;
    }
}
