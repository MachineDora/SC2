package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.vo.VipForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add/{userId}/{kind}")//增加卡
    public ResponseVO addVIP(@PathVariable int userId,@PathVariable int kind){
        return vipService.addVIPCard(userId,kind);
    }

    @PostMapping("/addInfo")//增加卡信息
    public ResponseVO addVIPInfo(@RequestBody VipForm addVIPForm){
        return vipService.addVIPInfo(addVIPForm);
    }

    @PostMapping("/updateInfo")//修改卡信息
    public ResponseVO updateVIPInfo(@RequestBody VipForm addVIPForm){
        return vipService.updateVIPInfo(addVIPForm);
    }

    @DeleteMapping("/deleteInfo")//删除卡信息
    public ResponseVO deleteVIPInfo(@RequestBody int id){
        return vipService.deleteVIPInfo(id);
    }

    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm){
        return vipService.charge(vipCardForm);
    }

    @GetMapping("/getChargeHistory/{userId}")
    public ResponseVO getChargeHistory(@PathVariable int userId){
        return vipService.getChargeHistoryByUserId(userId);
    }

    @GetMapping("/getallVIPInfo")//获得所有vip卡信息
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }

    @GetMapping("/getVIPPay")
    public ResponseVO getVipPaySum(){
        return vipService.getVipPaySum();
    }


}
