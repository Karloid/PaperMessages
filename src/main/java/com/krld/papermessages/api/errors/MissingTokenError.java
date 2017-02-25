package com.krld.papermessages.api.errors;

public class MissingTokenError extends ApiError {
    public MissingTokenError() {
        error = "M_MISSING_TOKEN";
        errorMessage = "Missing token";
    }
}
