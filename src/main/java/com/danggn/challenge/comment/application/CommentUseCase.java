package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.common.security.LoginMember;

public interface CommentUseCase {

    Long save(CreateCommentRequestVo createCommentRequestVo, LoginMember loginMember);
}
