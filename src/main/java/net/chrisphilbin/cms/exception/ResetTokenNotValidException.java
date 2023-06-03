package net.chrisphilbin.cms.exception;

public class ResetTokenNotValidException extends RuntimeException { 

    public ResetTokenNotValidException() {
            super("The provided token is not valid. Please try again.");
    }

}