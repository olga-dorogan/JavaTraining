package com.custom.dao;

import com.custom.model.entity.Task;
import com.custom.model.entity.UserInfo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by olga on 15.05.15.
 */
@Stateless
public class UserInfoDaoImpl implements UserInfoDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public UserInfo save(UserInfo userInfo) {
        System.out.println(">>>>>>>>><<<>>>>>>>Persisted user tasks: "+userInfo.getTasks());
        em.persist(userInfo);
        return userInfo;
    }

    @Override
    public List<UserInfo> findAll() {
        return em.createQuery("SELECT users FROM UserInfo users", UserInfo.class).getResultList();
    }

    @Override
    public UserInfo findByClientId(String clientId) {
        List<UserInfo> result = em.createQuery(
                "SELECT c FROM UserInfo c WHERE c.clientId LIKE ?1", UserInfo.class)
                .setParameter(1, clientId)
                .getResultList();
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public UserInfo updateUser(UserInfo userInfo) {
        UserInfo userFromDB = em.find(UserInfo.class, userInfo.getId());
        if (userFromDB == null) {
            throw new EntityNotFoundException();
        }
        List<Task> tasks = userFromDB.getTasks();
        System.out.println(">>>>>>>>>><<<>>>>>>>>>User from DB tasks: " + tasks);
       userFromDB.setTasks(userInfo.getTasks());
        return userFromDB;
    }
}
