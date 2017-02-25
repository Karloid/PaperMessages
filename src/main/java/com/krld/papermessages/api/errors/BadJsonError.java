package com.krld.papermessages.api.errors;

public class BadJsonError extends ApiError {
    public BadJsonError() {
        error = "M_BAD_JSON";
        errorMessage = "Bad json";
    }
}
