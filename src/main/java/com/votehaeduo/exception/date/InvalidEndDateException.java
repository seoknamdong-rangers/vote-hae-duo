package com.votehaeduo.exception.date;

import com.votehaeduo.exception.VoteHaeDuoException;
import org.springframework.http.HttpStatus;

public class InvalidEndDateException extends VoteHaeDuoException {

    public static final String EXCEPTION_MESSAGE = "투표 마감일을 초과한 날짜로 투표를 진행할 수 없습니다.";

    public InvalidEndDateException() {
        super(EXCEPTION_MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

}
