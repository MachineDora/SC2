package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.OrderFormService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderFormController {
    @Autowired
    OrderFormService orderFormService;
    @PostMapping("/vip/order")
    public ResponseVO addOrderFormByVipCard(@RequestParam List<Integer> ticketId, @RequestParam int couponId){
        return orderFormService.addOrderFormByVipCard(ticketId,couponId);
    }
    @PostMapping("/order")
    public ResponseVO addOrderForm(@RequestParam List<Integer> ticketId,@RequestParam int couponId){
        return orderFormService.addOrderForm(ticketId,couponId);
    }
    @GetMapping("/get/{userId}")
    public ResponseVO getOrderFormByUserId(@PathVariable int userId){
        return orderFormService.getOrderFormByUser(userId);
    }
    @GetMapping("/get/detail/{Id}")
    public ResponseVO getOrderFormById(@PathVariable int Id){
        return orderFormService.getOrderFormById(Id);
    }
    @GetMapping("/refund/{Id}")
    public ResponseVO refund(@PathVariable int Id){
        return orderFormService.refund(Id);
    }
}
