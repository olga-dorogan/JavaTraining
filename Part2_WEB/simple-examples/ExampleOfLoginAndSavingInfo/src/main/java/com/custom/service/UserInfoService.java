package com.custom.service;

import com.custom.model.vo.UserInfoVO;

import java.util.List;

/**
 * Created by olga on 15.05.15.
 */
public interface UserInfoService {
    int TASKS_CNT = 5;

    UserInfoVO save(UserInfoVO userInfo);

    List<UserInfoVO> findAll();

    UserInfoVO findByClientId(String clientId);

    UserInfoVO updateTasks(UserInfoVO userInfoVO);
}
