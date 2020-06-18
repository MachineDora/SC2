package com.example.cinema.blImpl.management;

import com.example.cinema.bl.management.ScrollBarService;
import com.example.cinema.data.management.ScrollBarMapper;
import com.example.cinema.po.ScrollBar;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScrollBarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrollBarServiceImpl implements ScrollBarService {

    @Autowired
    ScrollBarMapper scrollBarMapper;
    @Override
    public ResponseVO addScrollBarContent(ScrollBarVO scrollBarVO) {
        try{
            ScrollBar scrollBar = new ScrollBar();
            scrollBar.setPicture(scrollBarVO.getPicture());
            scrollBar.setToWeb(scrollBarVO.getToWeb());
            scrollBarMapper.insertOneScrollBar(scrollBar);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getScrollBarContent() {
        try {
            List<ScrollBar> scrollBarList = scrollBarMapper.getLatestSixScrollBar();
            List<ScrollBarVO> scrollBarVOS = new ArrayList<>();
            for(int i =0;i<6;i++){
                ScrollBarVO scrollBarVO = new ScrollBarVO();
                scrollBarVO.setPicture(scrollBarList.get(i).getPicture());
                scrollBarVO.setToWeb(scrollBarList.get(i).getToWeb());
                scrollBarVOS.add(scrollBarVO);
            }
            return ResponseVO.buildSuccess(scrollBarVOS);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
