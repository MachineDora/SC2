package com.example.cinema.data.management;

import com.example.cinema.po.ScrollBar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScrollBarMapper {
    int insertOneScrollBar(ScrollBar scrollBar);
    List<ScrollBar> getLatestSixScrollBar();
}
