package com.aernaur.votingSystem.exceptions;

public class ProfilePicUploadException extends Exception {

    public ProfilePicUploadException() {
        super("Unable to upload profile pic to AWS.");
    }
}
