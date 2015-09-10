package com.custom.service;

import com.custom.dao.UserInfoDao;
import com.custom.model.entity.Task;
import com.custom.model.entity.User;
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
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @EJB
    private UserInfoDao userInfoDao;

    @Override
    public UserInfoVO save(UserInfoVO userInfoVO) {
        User user = new User(userInfoVO);
        List<Task> tasks = user.getTasks();
        for (Task task : tasks) {
            task.setUser(user);
        }
        return new UserInfoVO(userInfoDao.save(user));
    }

    @Override
    public List<UserInfoVO> findAll() {
        List<User> users = userInfoDao.findAll();
        List<UserInfoVO> userInfoVOs = new ArrayList<>(users.size());
        for (User user : users) {
            userInfoVOs.add(new UserInfoVO(user));
        }
        return userInfoVOs;
    }

    @Override
    public UserInfoVO findByClientId(String clientId) {
        User foundUser = userInfoDao.findByClientId(clientId);
        if (foundUser == null) {
            return null;
        }
        return new UserInfoVO(foundUser);
    }

    @Override
    public UserInfoVO updateTasks(UserInfoVO userInfoVO) {
        User userForUpdate = new User(userInfoVO);
        LOGGER.debug("Before update tasks: {}", userForUpdate.getTasks());
        userForUpdate = userInfoDao.updateUser(userForUpdate);
        LOGGER.debug("After update tasks: {}", userForUpdate.getTasks());
        return new UserInfoVO(userForUpdate);
    }
}
