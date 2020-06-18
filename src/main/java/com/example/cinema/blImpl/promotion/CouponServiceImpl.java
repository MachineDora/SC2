package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.po.CouponUser;
import com.example.cinema.po.VIPCard;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            List<Coupon> coupons=new ArrayList<>();
            List<CouponUser> couponUsers=couponMapper.selectCouponByUserId(userId);
            for(int i=0;i<couponUsers.size();i++){
                coupons.add(couponMapper.selectById(couponUsers.get(i).getId()));
            }
            return ResponseVO.buildSuccess(coupons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAllCoupon() {
        try {
            List<CouponForm> couponForms=new ArrayList<>();
            List<Coupon> coupons=couponMapper.selectAllCoupon();
            for(int i=0;i<coupons.size();i++){
                CouponForm couponForm=new CouponForm();
                couponForm.setId(coupons.get(i).getId());
                couponForm.setName(coupons.get(i).getName());
                couponForms.add(couponForm);
            }
            return ResponseVO.buildSuccess(couponForms);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO insertCoupon(List<Integer> userid, List<Integer> couponid) {
        try {
            if(userid.size()==0 && couponid.size()==0){
                List<Coupon> coupons=couponMapper.selectAllCoupon();
                List<VIPCard> vipCards=vipCardMapper.selectAllVipCard();
                for(int i=0;i<coupons.size();i++){
                    for(int m=0;m<vipCards.size();m++){
                        couponMapper.insertCouponUser(coupons.get(i).getId(),vipCards.get(m).getUserId());
                    }
                }
            }
            else if(userid.size()==0){
                List<VIPCard> vipCards=vipCardMapper.selectAllVipCard();
                for(int i=0;i<couponid.size();i++){
                    for(int m=0;m<vipCards.size();m++){
                        couponMapper.insertCouponUser(couponid.get(i),vipCards.get(m).getUserId());
                    }
                }
            }
            else if(couponid.size()==0){
                List<Coupon> coupons=couponMapper.selectAllCoupon();
                for(int i=0;i<coupons.size();i++){
                    for(int m=0;m<userid.size();m++){
                        couponMapper.insertCouponUser(coupons.get(i).getId(),userid.get(m));
                    }
                }
            }
            else{
                for(int i=0;i<couponid.size();i++){
                    for(int m=0;m<userid.size();m++){
                        couponMapper.insertCouponUser(couponid.get(i),userid.get(m));
                    }
                }
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);
            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }
}
