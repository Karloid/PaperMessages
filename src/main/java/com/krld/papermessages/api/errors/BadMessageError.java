package com.krld.papermessages.api.errors;

public class BadMessageError extends ApiError {
    public BadMessageError() {
        error = "M_BAD_MESSAGE";
        errorMessage = "Some fields missing or has incorrect format";
    }
}
