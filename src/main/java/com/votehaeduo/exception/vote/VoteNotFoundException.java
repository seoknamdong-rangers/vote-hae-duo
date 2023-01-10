package com.votehaeduo.exception.vote;

import com.votehaeduo.exception.VoteHaeDuoException;
import org.springframework.http.HttpStatus;

public class VoteNotFoundException extends VoteHaeDuoException {

    public static final String EXCEPTION_MESSAGE = "해당 투표를 찾을 수 없습니다.";

    public VoteNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

}