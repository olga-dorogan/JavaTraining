package com.custom.service.role;

import javax.ejb.Stateless;

/**
 * Created by olga on 21.05.15.
 */
@Stateless
public class MockRoleServiceImpl implements RoleService {
    private static final String ADMIN_EMAIL = "dorogan.olga.test@gmail.com";

    public boolean isAdmin(String email) {
        return email.equalsIgnoreCase(ADMIN_EMAIL);
    }
}
