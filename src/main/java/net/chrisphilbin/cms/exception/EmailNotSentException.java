package net.chrisphilbin.cms.exception;

public class EmailNotSentException extends RuntimeException { 

    public EmailNotSentException() {
            super("There was an error sending the email. Please try again in a moment.");
    }

}