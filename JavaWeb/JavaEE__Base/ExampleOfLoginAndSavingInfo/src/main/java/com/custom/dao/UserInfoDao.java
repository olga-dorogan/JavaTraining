package com.custom.dao;

import com.custom.model.entity.UserInfo;

import java.util.List;

/**
 * Created by olga on 15.05.15.
 */
public interface UserInfoDao {
    UserInfo save(UserInfo userInfo);

    List<UserInfo> findAll();

    UserInfo findByClientId(String clientId);

    UserInfo updateUser(UserInfo userInfo);
}
