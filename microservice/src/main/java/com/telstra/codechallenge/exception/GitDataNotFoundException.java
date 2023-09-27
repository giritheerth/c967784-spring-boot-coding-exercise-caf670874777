package com.telstra.codechallenge.exception;

import java.io.Serial;

public class GitDataNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GitDataNotFoundException(String message) {
        super(message);
    }
}