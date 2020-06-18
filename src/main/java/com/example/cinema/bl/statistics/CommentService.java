package com.example.cinema.bl.statistics;

import com.example.cinema.po.Comment;
import com.example.cinema.vo.ResponseVO;

import java.util.Date;

/**
 * @author wy
 * @date 2019/6/12 1:34 PM
 */
public interface CommentService {

    ResponseVO insertOneComment(Comment comment);

    ResponseVO selectAllComment(int movieId);
}
