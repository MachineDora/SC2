package com.example.cinema.controller.statistics;

import com.example.cinema.bl.statistics.CommentService;
import com.example.cinema.po.Comment;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wy
 * @date 2019/6/12 1:34 PM
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment/insert", method = RequestMethod.POST)
    public ResponseVO insertOneComment(@RequestBody Comment comment){
        return commentService.insertOneComment(comment);
    }

    @RequestMapping(value = "/comment/all/{movieId}", method = RequestMethod.GET)
    public ResponseVO getAllComment(@PathVariable int movieId){
        return commentService.selectAllComment(movieId);
    }












}
