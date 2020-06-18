package com.example.cinema.bl.management;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScrollBarVO;

public interface ScrollBarService {
    ResponseVO addScrollBarContent(ScrollBarVO scrollBarVO);
    ResponseVO getScrollBarContent();
}
