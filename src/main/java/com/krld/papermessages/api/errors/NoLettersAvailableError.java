package com.krld.papermessages.api.errors;

public class NoLettersAvailableError extends ApiError {
    public NoLettersAvailableError() {
        error = "M_NO_LETTERS_AVAILABLE";
        errorMessage = "No letters available";
    }
}
