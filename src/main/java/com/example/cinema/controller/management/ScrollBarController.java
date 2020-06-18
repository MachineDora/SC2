package com.example.cinema.controller.management;


import com.example.cinema.bl.management.ScrollBarService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScrollBarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
public class ScrollBarController {
    @Autowired
    private ScrollBarService scrollBarService;
    @RequestMapping("/scroll/bar/add")
    public ResponseVO addHomePageContent(@RequestBody ScrollBarVO formData){
        return scrollBarService.addScrollBarContent(formData);
    }
    @RequestMapping("/scroll/bar/get")
    public ResponseVO getHomePageContent(){
        return scrollBarService.getScrollBarContent();
    }
}