package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import com.example.cinema.po.Worker;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String ACCOUNT_NOTEXIST="该账号不存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            int flag=0;
            List<Worker> workers=accountMapper.selectAllWorkers();
            for(int i=0;i<workers.size();i++){
                if(userForm.getUsername().equals(workers.get(i).getName())){
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            else{
                accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword());
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO creatWorker(String name,String password) {
        try {
            int flag=0;
            List<User> users=accountMapper.selectAllUser();
            for(int i=0;i<users.size();i++){
                if(name.equals(users.get(i).getUsername())){
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            else{
                accountMapper.createNewWorker(name,password);
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO deleteWorker(String name) {
        try {
            int i=accountMapper.deleteWorker(name);
            if(i==0){
                return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
            }
            else{
                return ResponseVO.buildSuccess();
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }

    @Override
    public ResponseVO searchWorker(String name) {
        try {
            Worker worker=accountMapper.getWorkerByName(name);
            if(worker==null){
                return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
            }
            else{
                return ResponseVO.buildSuccess(worker);
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            Worker worker=accountMapper.getWorkerByName(userForm.getUsername());
            if(null==worker || !worker.getPassword().equals(userForm.getPassword())){
                return null;
            }
            else{
                User newuser=new User();
                newuser.setId(worker.getId());
                newuser.setUsername("worker");
                newuser.setPassword(worker.getPassword());
                UserVO userVO =new UserVO(newuser);
                return  userVO;
            }
        }
        return new UserVO(user);
    }

    @Override
    public ResponseVO getAllWorker(){
        try {
            List<Worker> workers=accountMapper.selectAllWorkers();
            return ResponseVO.buildSuccess(workers);
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }


}
