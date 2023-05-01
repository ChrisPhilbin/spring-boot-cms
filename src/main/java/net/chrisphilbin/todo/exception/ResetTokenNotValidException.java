package net.chrisphilbin.todo.exception;

public class ResetTokenNotValidException extends RuntimeException { 

    public ResetTokenNotValidException() {
            super("The provided token is not valid. Please try again.");
    }

}