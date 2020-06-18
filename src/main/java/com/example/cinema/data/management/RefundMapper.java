package com.example.cinema.data.management;

import com.example.cinema.po.Refund;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundMapper {
    int insertRefund(Refund refund);
    Refund getLatestRefund();
}
