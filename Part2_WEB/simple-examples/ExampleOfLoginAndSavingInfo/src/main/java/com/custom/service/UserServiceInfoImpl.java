package com.custom.service;

import com.custom.dao.UserInfoDao;
import com.custom.model.entity.Task;
import com.custom.model.entity.UserInfo;
import com.custom.model.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 15.05.15.
 */
@Stateless
public class UserServiceInfoImpl implements UserInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceInfoImpl.class);
    private static final List<Task> tasks = new ArrayList<>(TASKS_CNT);
    static {
        for (int i = 0; i < TASKS_CNT; i++) {
            tasks.add(new Task(false));
        }
    }

    @EJB
    private UserInfoDao userInfoDao;

    @Override
    public UserInfoVO save(UserInfoVO userInfoVO) {
        UserInfo userInfo = new UserInfo(userInfoVO);
        userInfo.setTasks(new ArrayList<Task>(tasks));
        return new UserInfoVO(userInfoDao.save(userInfo));
    }

    @Override
    public List<UserInfoVO> findAll() {
        List<UserInfo> userInfos = userInfoDao.findAll();
        List<UserInfoVO> userInfoVOs = new ArrayList<>(userInfos.size());
        for (UserInfo userInfo : userInfos) {
            userInfoVOs.add(new UserInfoVO(userInfo));
        }
        return userInfoVOs;
    }

    @Override
    public UserInfoVO findByClientId(String clientId) {
        UserInfo foundUser = userInfoDao.findByClientId(clientId);
        if (foundUser == null) {
            return null;
        }
        return new UserInfoVO(foundUser);
    }

    @Override
    public UserInfoVO updateTasks(UserInfoVO userInfoVO) {
        UserInfo userInfoForUpdate = new UserInfo(userInfoVO);
        LOGGER.debug("Before update tasks: {}", userInfoForUpdate.getTasks());
        userInfoForUpdate = userInfoDao.updateUser(userInfoForUpdate);
        LOGGER.debug("After update tasks: {}", userInfoForUpdate.getTasks());
        return new UserInfoVO(userInfoForUpdate
        );
    }
}
