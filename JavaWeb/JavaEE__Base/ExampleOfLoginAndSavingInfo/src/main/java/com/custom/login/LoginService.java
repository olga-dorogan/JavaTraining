package com.custom.login;

import com.custom.model.vo.UserInfoVO;
import com.custom.service.UserInfoService;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by olga on 15.05.15.
 */
@Stateless
public class LoginService {
    @EJB
    private UserInfoService userInfoService;

    public UserInfoVO login(String familyName, String name, String clientId, String email) {
        UserInfoVO userFromDB = userInfoService.findByClientId(clientId);
        if (userFromDB == null) {
            userFromDB = new UserInfoVO(familyName, name, email, clientId);
            userFromDB =
                    userInfoService.save(userFromDB);
        }
        return userFromDB;
    }

}
