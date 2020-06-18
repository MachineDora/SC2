package com.example.cinema.bl.management;

import com.example.cinema.po.Refund;
import com.example.cinema.vo.RefundVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.stereotype.Service;


public interface RefundService {
    ResponseVO addRefund(RefundVO refund);
    ResponseVO selectLatestRefund();
}
