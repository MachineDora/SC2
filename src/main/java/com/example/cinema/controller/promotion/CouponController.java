package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    @GetMapping("/all")
    public ResponseVO getAllCoupon(){
        return couponService.getAllCoupon();
    }


    @PostMapping("/insert")
    public ResponseVO insertCoupon(@RequestParam List<Integer> userid, @RequestParam List<Integer> couponid){
        return couponService.insertCoupon(userid, couponid);
    }

}
