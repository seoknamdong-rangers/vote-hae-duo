package com.votehaeduo.exception;

public abstract class VoteHaeDuoException extends RuntimeException {

    public VoteHaeDuoException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

}