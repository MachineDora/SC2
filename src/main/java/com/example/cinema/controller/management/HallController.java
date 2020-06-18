package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScheduleViewForm;
import com.example.cinema.vo.SeatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @RequestMapping(value = "hall/id", method = RequestMethod.GET)
    public ResponseVO getHallId(){
        return hallService.getHallId();
    }

    @RequestMapping(value = "hall/get", method = RequestMethod.GET)
    public ResponseVO getHall(int id){
        return hallService.getHall(id);
    }

    @RequestMapping(value = "hall/check", method = RequestMethod.GET)
    public ResponseVO checkHall(String id){
        return hallService.checkHall(id);
    }

    @RequestMapping(value = "/hall/set", method = RequestMethod.POST)
    public ResponseVO setHallseats(@RequestBody Hall hall){
        return hallService.setHallseats(hall);
    }

    @RequestMapping(value = "/hall/modify", method = RequestMethod.POST)
    public ResponseVO modifyHall(@RequestBody Hall hall){
        return hallService.modifyHall(hall);
    }

    @RequestMapping(value = "/hall/delete", method = RequestMethod.POST)
    public ResponseVO deleteHall(@RequestBody Hall hall){
        return hallService.deleteHall(hall);
    }
}
