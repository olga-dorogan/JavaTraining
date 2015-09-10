package com.custom.controller.login;

import com.custom.service.role.RoleService;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by olga on 21.05.15.
 */
@WebServlet("/oauth2callback")
public class OAuth2CallbackController extends AbstractAuthorizationCodeCallbackServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2CallbackController.class);

    private static final String REDIRECT_OK_ADMIN = "calendarList";
    private static final String REDIRECT_OK_STUDENT = "calendarStudent";
    private static final String REDIRECT_ACCESS_DENIED = "home.html";

    @Inject
    RoleService roleService;

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
        return Utils.newFlow();
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        String redirectUri = Utils.getRedirectUri(req);
        LOGGER.debug("Redirect URI: {}", redirectUri);
        return redirectUri;
    }

    @Override
    protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
        return req.getSession(true).getId();
    }

    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential) throws ServletException, IOException {
        Enumeration attributes = req.getSession().getAttributeNames();
        LOGGER.debug("Session attributes after successful login: ");
        while (attributes.hasMoreElements()) {
            LOGGER.debug("{}", attributes.nextElement());
        }
        Oauth2 oauth2 = Utils.loadOAuthClient(req);
        Userinfoplus userinfoplus = oauth2.userinfo().get().execute();
        LOGGER.debug("You logged as {} with email == {}", userinfoplus.getFamilyName(), userinfoplus.getEmail());

        if (roleService.isAdmin(userinfoplus.getEmail())) {
            resp.sendRedirect(REDIRECT_OK_ADMIN);
            return;
        }
        resp.sendRedirect(REDIRECT_OK_STUDENT);
    }

    @Override
    protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse) throws ServletException, IOException {
        resp.sendRedirect(REDIRECT_ACCESS_DENIED);
    }
}
