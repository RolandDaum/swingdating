package com.swingdating.System;

public class LRResponse {

    public enum ResponseType {
        USERNAME_NOTFOUND("Benutzername konnte nicht gefunden werdne"),
        VALID_LOGIN("Gültiger Login"),
        VALID_USERNAME("Gültiger Benutzername"),
        VALID_PASSWORD("Gültiges Password"),
        INVALID_USERNAME("Ungültiger Benutzername"),
        INVALID_PASSWORD("Ungültiges Passwort"),
        USERNAME_TAKEN("Benutzername bereits vergeben"),
        EMAIL_INVALID("Ungültige E-Mail-Adresse"),
        PASSWORD_TOO_SHORT("Passwort zu kurz"),
        PASSWORDS_DO_NOT_MATCH("Passwörter stimmen nicht überein"),
        ACCOUNT_LOCKED("Konto gesperrt"),
        UNKNOWN_ERROR("Unbekannter Fehler");

        private final String message;

        ResponseType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private final ResponseType responseType;

    public LRResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

    public ResponseType getErrorType() {
        return responseType;
    }

    public String getErrorMessage() {
        return responseType.getMessage();
    }
}
