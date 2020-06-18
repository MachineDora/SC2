package com.example.cinema.blImpl.management.movie;

import com.example.cinema.bl.management.MovieService;
import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.blImpl.management.schedule.MovieServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.management.MovieMapper;
import com.example.cinema.data.statistics.MovieLikeMapper;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.Movie;
import com.example.cinema.po.MovieTotalBoxOffice;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/3/12 6:43 PM
 */
@Service
public class MovieServiceImpl implements MovieService, MovieServiceForBl {
    private static final String SCHEDULE_ERROR_MESSAGE = "有电影后续仍有排片或已有观众购票且未使用";
    private static final String NO_TYPE ="无此类型影片";

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private ScheduleServiceForBl scheduleServiceForBl;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private MovieLikeMapper movieLikeMapper;

    @Override
    public ResponseVO addMovie(MovieForm addMovieForm) {
        try {
            movieMapper.insertOneMovie(addMovieForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchOneMovieByIdAndUserId(int id, int userId) {
        try {
            Movie movie = movieMapper.selectMovieByIdAndUserId(id, userId);
            if(movie != null){
                return ResponseVO.buildSuccess(new MovieVO(movie));
            }else{
                return ResponseVO.buildSuccess(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO searchAllMovie() {
        try {
            return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectAllMovie()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchOtherMoviesExcludeOff() {
        try {
            return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectOtherMoviesExcludeOff()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMovieByKeyword(String keyword) {
        if (keyword==null||keyword.equals("")){
            return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectAllMovie()));
        }
        return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectMovieByKeyword(keyword)));
    }



    @Override
    public ResponseVO pullOfBatchOfMovie(MovieBatchOffForm movieBatchOffForm) {
        try {
            List<Integer> movieIdList =  movieBatchOffForm.getMovieIdList();
            ResponseVO responseVO = preCheck(movieIdList);
            if(!responseVO.getSuccess()){
                return responseVO;
            }
            movieMapper.updateMovieStatusBatch(movieIdList);
            return ResponseVO.buildSuccess();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateMovie(MovieForm updateMovieForm) {
        try {
            ResponseVO responseVO = preCheck(Arrays.asList(updateMovieForm.getId()));
            if(!responseVO.getSuccess()){
                return responseVO;
            }
            movieMapper.updateMovie(updateMovieForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Movie getMovieById(int id) {
        try {
            return movieMapper.selectMovieById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 下架和修改电影的前置检查
     * @param movieIdList
     * @return
     */
    public ResponseVO preCheck(List<Integer> movieIdList){
        Date thisTime = new Date();
        List<ScheduleItem> scheduleItems = scheduleServiceForBl.getScheduleByMovieIdList(movieIdList);

        // 检查是否有电影后续有排片及是否有观众购票未使用
        for(ScheduleItem s : scheduleItems){
            if(s.getEndTime().after(thisTime)){
                return ResponseVO.buildFailure(SCHEDULE_ERROR_MESSAGE);
            }
        }
        return ResponseVO.buildSuccess();
    }


    private List<MovieVO> movieList2MovieVOList(List<Movie> movieList){
        List<MovieVO> movieVOList = new ArrayList<>();
        for(Movie movie : movieList){
            movieVOList.add(new MovieVO(movie));
        }
        return movieVOList;
    }

    @Override
    public ResponseVO selectMovieByType(String type,int state) {
        try {
            List<Movie> movies=new ArrayList<>();
            if(type.equals("全部类型")){
                movies=movieMapper.selectAllMovie();
            }
            else{
                movies=movieMapper.selectMovieByType(type);
            }
            if(movies==null){
                return ResponseVO.buildFailure(NO_TYPE);
            }
            else {
                if(state==0){
                    List<Movie> temp=new ArrayList<>();
                    for(int i=0;i<movies.size();i++){
                        if(movies.get(i).getStatus()==0){
                            temp.add(movies.get(i));
                        }
                    }
                    if(temp.size()>0){
                        movies.clear();
                        for(int i=0;i<temp.size();i++){
                            movies.add(temp.get(i));
                        }
                    }
                    else {
                        movies.clear();
                    }
                }
                else if(state==1){
                    List<Movie> temp=new ArrayList<>();
                    for(int i=0;i<movies.size();i++){
                        if(movies.get(i).getStatus()==1){
                            temp.add(movies.get(i));
                        }
                    }
                    if(temp.size()>0){
                        movies.clear();
                        for(int i=0;i<temp.size();i++){
                            movies.add(temp.get(i));
                        }
                    }
                    else {
                        movies.clear();
                    }
                }
                if(movies.size()==0){
                    return ResponseVO.buildFailure(NO_TYPE);
                }
                else{
                    List<MovieVO> movieVOS=new ArrayList<>();
                    for(int i=0;i<movies.size();i++){
                        MovieVO movieVO=new MovieVO(movies.get(i));
                        movieVOS.add(movieVO);
                    }
                    return ResponseVO.buildSuccess(movieVOS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO getBoxOfficeList(){
        try {
            List<MovieTotalBoxOffice> movieTotalBoxOffices=statisticsMapper.selectMovieTotalBoxOffice();
            if(movieTotalBoxOffices!=null){
                return ResponseVO.buildSuccess(movieTotalBoxOffices);
            }
            else {
                return ResponseVO.buildFailure("暂无票房信息！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getLikeDataList(){
        try {
            List<Movie> movies=movieMapper.selectOtherMoviesExcludeOff();
            List<ForwardMovieForm> forwardMovieForms=new ArrayList<>();
            List<ForwardMovieForm> forwardMovieFormList=new ArrayList<>();
            for(int i=0;i<movies.size();i++){
                ForwardMovieForm forwardMovieForm =new ForwardMovieForm();
                forwardMovieForm.setMovieId(movies.get(i).getId());
                forwardMovieForm.setName(movies.get(i).getName());
                forwardMovieForm.setSum(movieLikeMapper.selectLikeNums(movies.get(i).getId()));
                forwardMovieForms.add(forwardMovieForm);
            }
            for(int i=100;i>0;i--){
                if(forwardMovieFormList.size()==10){
                    break;
                }
                else{
                    for(int m=0;m<forwardMovieForms.size();m++){
                        if(forwardMovieForms.get(m).getSum()==i){
                            forwardMovieFormList.add(forwardMovieForms.get(m));
                        }
                    }
                }
            }
            return ResponseVO.buildSuccess(forwardMovieFormList);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


}
