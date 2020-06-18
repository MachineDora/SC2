package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.vo.VIPInfoVO;
import com.example.cinema.vo.VipForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    int insertOneCard(VIPCard vipCard);

    int insertNewKindCard(VipForm vipInfo);

    int updateKindCardById(VipForm vipInfo);

    void deleteKindCardById(@Param("id") int id);

    VIPCard selectCardById(int id);

    List<Integer> selectCardByKindId(int kind);

    VIPInfo selectCardByKind(int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    void updateChargeHistory(@Param("id") int id,@Param("charge_history") String chargeHistory);

    VIPCard selectCardByUserId(int userId);

    List<VIPInfo> selectAllVIPInfo();

    List<VIPCard> selectAllVipCard();

}
