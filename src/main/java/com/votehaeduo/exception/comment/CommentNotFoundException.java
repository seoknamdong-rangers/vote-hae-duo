package com.votehaeduo.exception.comment;

import com.votehaeduo.exception.VoteHaeDuoException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends VoteHaeDuoException {

    public static final String EXCEPTION_MESSAGE = "댓글을 찾을 수 없습니다.";

    public CommentNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

}
