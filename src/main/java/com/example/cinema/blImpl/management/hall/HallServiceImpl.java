package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.SeatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    private static final String DELETE_ERROR_MESSAGE = "否";
    private static final String DELETE_RIGHT_MESSAGE = "可";

    @Autowired
    private HallMapper hallMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getHallId() {
        try {
            int result=0;
            int flag=0;
            int index=0;
            List<Hall> hallList=hallMapper.selectAllHall();
            for(int i=1;i<100;i++){
                for(int m=0;m<hallList.size();m++){
                    if(i==hallList.get(m).getId()){
                        flag=0;
                        break;
                    }
                    else{
                        flag=1;
                    }
                }
                if(flag==1){
                    index=1;
                    result=i;
                    break;
                }
            }
            if(index==0){
                result=hallMapper.selectAllHall().size()+1;
            }
            return ResponseVO.buildSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO setHallseats(Hall hall) {
        try {
            hallMapper.insertHall(hall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO modifyHall(Hall hall){
        try {
            hallMapper.updataHall(hall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteHall(Hall hall){
        try {
            Hall newhall=new Hall();
            newhall.setId(Integer.valueOf(hall.getName()));
            newhall.setName("none");
            newhall.setColumn(0);
            newhall.setRow(0);
            hallMapper.deleteHall(newhall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO checkHall(String lastid) {
        try {
            Date date=new Date();
            int id=Integer.valueOf(lastid);
            List<ScheduleItem> scheduleItems=scheduleMapper.selectScheduleHall(id,date);
            if(scheduleItems.size()==0){
                return ResponseVO.buildSuccess(DELETE_RIGHT_MESSAGE);
            }
            else{
                return ResponseVO.buildSuccess(DELETE_ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getHall(int id) {
        try {
            return ResponseVO.buildSuccess(hallMapper.selectHallById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }
}
