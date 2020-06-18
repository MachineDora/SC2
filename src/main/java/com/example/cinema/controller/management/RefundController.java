package com.example.cinema.controller.management;

import com.example.cinema.bl.management.RefundService;
import com.example.cinema.vo.RefundVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
public class RefundController {
    @Autowired
    private RefundService refundService;
    @RequestMapping("/refund/add")
    public ResponseVO addRefund(@RequestBody RefundVO formData){
        return refundService.addRefund(formData);
    }
    @RequestMapping("/refund/get")
    public ResponseVO getRefund(){
        return refundService.selectLatestRefund();
    }
}
