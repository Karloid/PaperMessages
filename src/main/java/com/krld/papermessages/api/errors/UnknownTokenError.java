package com.krld.papermessages.api.errors;

public class UnknownTokenError extends ApiError {
    public UnknownTokenError() {
        error = "M_UNKNOWN_TOKEN";
        errorMessage = "Unknown token";
    }
}
