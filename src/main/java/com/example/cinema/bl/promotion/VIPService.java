package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VipForm;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId,int kind);

    ResponseVO getCardById(int id);

    ResponseVO addVIPInfo(VipForm addMovieForm);

    ResponseVO updateVIPInfo(VipForm vipForm);

    ResponseVO deleteVIPInfo(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getChargeHistoryByUserId(int userId);

    ResponseVO getCardByUserId(int userId);

    ResponseVO getVipPaySum();

    ResponseVO getKindById(int KindId);


}
