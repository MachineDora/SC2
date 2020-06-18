package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.*;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.po.VIPCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    private static final String MEMBER_MESSAGE = "该会员卡已经被购买，无法删除或修改";
    @Autowired
    VIPCardMapper vipCardMapper;

    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public ResponseVO addVIPCard(int userId,int kind) {//增加会员卡号
        VIPInfo vipInfo = vipCardMapper.selectCardByKind(kind);
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        vipCard.setName(vipInfo.getName());
        vipCard.setKind(vipInfo.getId());
        vipCard.setDescription(vipInfo.getDescription());
        vipCard.setMoney1(vipInfo.getMoney1());
        vipCard.setMoney2(vipInfo.getMoney2());
        vipCard.setPrice(vipInfo.getPrice());
        try {
            vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(vipCard.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addVIPInfo(VipForm addMovieForm) {
        try {
            int key=vipCardMapper.insertNewKindCard(addMovieForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateVIPInfo(VipForm vipForm) {
        try {
            int a=vipForm.getId();
            List<Integer> m=vipCardMapper.selectCardByKindId(a);
            if(m.isEmpty()){
                int key=vipCardMapper.updateKindCardById(vipForm);
                return ResponseVO.buildSuccess();
            }
            return ResponseVO.buildFailure(MEMBER_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteVIPInfo(int id) {
        try {
            List<Integer> m=vipCardMapper.selectCardByKindId(id);
            if(m.isEmpty()){
                vipCardMapper.deleteKindCardById(id);
                return ResponseVO.buildSuccess();
            }
            return ResponseVO.buildFailure(MEMBER_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVipPaySum(){
        try {
            List<VIPCardForm> vipCardForms=new ArrayList<>();
            List<VIPCard> vipCards=vipCardMapper.selectAllVipCard();
            for(int i=0;i<100;i++){
                double sum=0;
                String name="";
                int id=0;
                for(int m=0;m<vipCards.size();m++){
                    if(i==vipCards.get(m).getId()){
                        List<Ticket> tickets=ticketMapper.selectPayTicketByUser(vipCards.get(m).getUserId());
                        if(tickets.size()>=0){
                            for(int n=0;n<tickets.size();n++){
                                sum=sum+scheduleMapper.selectScheduleById(tickets.get(n).getScheduleId()).getFare();
                            }
                        }
                        else if(tickets.size()==0){
                            sum=0;
                        }
                        name=accountMapper.selectUserById(vipCards.get(m).getUserId()).getUsername();
                        id=vipCards.get(m).getUserId();
                        VIPCardForm vipCardForm=new VIPCardForm();
                        vipCardForm.setName(name);
                        vipCardForm.setSum(sum);
                        vipCardForm.setVipId(id);
                        vipCardForms.add(vipCardForm);
                    }
                }
            }
            return ResponseVO.buildSuccess(vipCardForms);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        try {
            return ResponseVO.buildSuccess(VIPInfoList2VIPInfoVOList(vipCardMapper.selectAllVIPInfo()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {

        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double balance = vipCard.calculate(vipCardForm.getAmount());
        vipCard.setBalance(vipCard.getBalance() + balance);
        String time = new Timestamp(new Date().getTime()).toString().substring(0, 19);
        vipCard.setChargeHistory(vipCard.getChargeHistory() + ';' + time + ',' + vipCardForm.getAmount());
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            vipCardMapper.updateChargeHistory(vipCardForm.getVipId(), vipCard.getChargeHistory());
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getChargeHistoryByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            if(vipCard.getChargeHistory()==null){
                return ResponseVO.buildSuccess();
            }
            List<VIPChargeHistoryVO> chargeHistoryVOs = new ArrayList<>();
            String[] history = vipCard.getChargeHistory().split(";");
            for(String h : history) {
                if(h.equals("null")) {
                    continue;
                }
                VIPChargeHistoryVO chargeHistoryVO = new VIPChargeHistoryVO();
                String[] timeAndAmount = h.split(",");
                chargeHistoryVO.setTime(timeAndAmount[0]);
                chargeHistoryVO.setAmount(timeAndAmount[1]);
                chargeHistoryVOs.add(chargeHistoryVO);
            }
            return ResponseVO.buildSuccess(chargeHistoryVOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getKindById(int KindId) {
        try {
            VIPInfo vipInfo = vipCardMapper.selectCardByKind(KindId);
            VIPCard vipCard =new VIPCard();

            if(vipInfo==null){
                return ResponseVO.buildFailure("会员卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<VIPInfoVO> VIPInfoList2VIPInfoVOList(List<VIPInfo> VIPList){
        List<VIPInfoVO> VIPVOList = new ArrayList<>();
        for(VIPInfo vipInfo : VIPList){
            VIPVOList.add(new VIPInfoVO(vipInfo));
        }
        return VIPVOList;
    }


}
