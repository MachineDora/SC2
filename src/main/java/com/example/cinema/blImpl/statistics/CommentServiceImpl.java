package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.CommentService;

import com.example.cinema.data.statistics.CommentMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fjj
 * @date 2019/5/22 1:34 PM
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public ResponseVO insertOneComment(Comment comment) {
        try{
            commentMapper.insertOneComment(comment);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO selectAllComment(int movieId) {
        try{
            List<Comment> comments=commentMapper.selectAllComment(movieId);
            return ResponseVO.buildSuccess(comments);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
