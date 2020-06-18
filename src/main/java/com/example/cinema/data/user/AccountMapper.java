package com.example.cinema.data.user;

import com.example.cinema.po.User;
import com.example.cinema.po.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
    public int createNewAccount(@Param("username") String username, @Param("password") String password);

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
    public int createNewWorker(@Param("username") String username, @Param("password") String password);

    /**
     * 删除账号
     * @param username
     * @return
     */
    int deleteWorker(@Param("username") String username);


    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    public User getAccountByName(@Param("username") String username);
    //查询所有
    public List<User> selectAllUser();

    /**
     * @param id
     * @return
     */
    User selectUserById(@Param("id") int id);

    List<Worker> selectAllWorkers();

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    Worker getWorkerByName(@Param("username") String username);
}
