package com.votehaeduo.exception.member;

import com.votehaeduo.exception.VoteHaeDuoException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends VoteHaeDuoException {

    public static final String EXCEPTION_MESSAGE = "해당 유저를 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

}
