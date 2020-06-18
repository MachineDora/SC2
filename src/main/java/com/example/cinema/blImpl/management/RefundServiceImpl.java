package com.example.cinema.blImpl.management;

import com.example.cinema.bl.management.RefundService;
import com.example.cinema.data.management.RefundMapper;
import com.example.cinema.po.Refund;
import com.example.cinema.vo.RefundVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    RefundMapper refundMapper;
    @Override
    public ResponseVO addRefund(RefundVO refund1) {
        Refund refund = new Refund();
        refund.setDeadline(refund1.getDeadline());
        refund.setDeadlinePercent(refund1.getDeadlinePercent());
        refund.setTimeOne(refund1.getTimeOne());
        refund.setTimeOnePercent(refund1.getTimeOnePercent());
        refund.setTimeTwo(refund1.getTimeTwo());
        refund.setTimeTwoPercent(refund1.getTimeTwoPercent());
        refundMapper.insertRefund(refund);
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO selectLatestRefund(){

        Refund refund = refundMapper.getLatestRefund();
        if(refund!=null) {
            System.out.println(refund.getDeadline());
            return ResponseVO.buildSuccess(refund.getRefundVO());
        }
        else {
            return ResponseVO.buildSuccess();
        }
    }
}
