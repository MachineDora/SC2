package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.MovieMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;


import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fjj
 * @date 2019/5/22 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) {
        //接口见接口说明
        try {
            List<PlacingRateVO> placingRateVOList=new ArrayList<>();
            List<Hall> hall= hallMapper.selectAllHall();
            double n=hall.size();
            double m=0;
            for(int i=0;i<hall.size();i++){
                int r=hall.get(i).getRow();
                int c=hall.get(i).getColumn();
                m=m+r*c;
            }
            if(date==null){
                date=new Date();
                date.getTime();
            }
            Date startDate = getNumDayAfterDate(date, -6);
            for(int i = -1; i < 6; i++){
                Date firstday=getNumDayAfterDate(startDate, i);
                Date secondday=getNumDayAfterDate(startDate,i+1);
                double totalmovietime=0;
                List<MovieScheduleTime> movieScheduleTimes=statisticsMapper.selectMovieScheduleTimes(firstday,secondday);
                for(int a=0;a<movieScheduleTimes.size();a++){
                    totalmovietime=totalmovietime+movieScheduleTimes.get(a).getTime();
                }
                List<Ticket> tickets=ticketMapper.selectTicketByDate(firstday,secondday);
                double totalviewer=tickets.size();
                double result=0;
                if(totalmovietime==0 || m==0 || n==0 ||totalviewer==0){
                    result=0;
                }
                else{
                    result=(((totalviewer/totalmovietime)/m)/n);
                }
                PlacingRateVO placingRateVO=new PlacingRateVO();
                placingRateVO.setPlacingrate(result);
                placingRateVO.setdate(secondday);
                placingRateVOList.add(placingRateVO);
            }
            return  ResponseVO.buildSuccess(placingRateVOList);

        } catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        //接口见接口说明
        try{
            Date current=new Date();
            current.getTime();
            Calendar cld = Calendar.getInstance();
            cld.setTime(current);
            cld.add(Calendar.DATE, -(days-1));
            Date otherday = cld.getTime();
            List<PopularMovies> popularMoviesList=statisticsMapper.selectPopularMovies(otherday,current);
            List<PopularMoviesVO> popularMoviesVOList=new ArrayList<>();
            for (int i =0;i<movieNum;i++){
                PopularMoviesVO popularMoviesVO=new PopularMoviesVO();
                popularMoviesVO.setId(popularMoviesList.get(i).getMovieId());
                popularMoviesVO.setName(popularMoviesList.get(i).getName());
                popularMoviesVO.setBoxOffice(popularMoviesList.get(i).getBoxOffice());
                popularMoviesVOList.add(popularMoviesVO);
            }
            return ResponseVO.buildSuccess(popularMoviesVOList);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    /**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }



    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }


    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }
}
