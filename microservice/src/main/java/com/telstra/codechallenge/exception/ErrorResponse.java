package com.telstra.codechallenge.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

        private String errorCode;
        private String errorMessage;
        private int status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private LocalDateTime timestamp;

        public ErrorResponse(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

    public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

    public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

    public void setStatus(int status) {
            this.status = status;
        }

    public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
}
