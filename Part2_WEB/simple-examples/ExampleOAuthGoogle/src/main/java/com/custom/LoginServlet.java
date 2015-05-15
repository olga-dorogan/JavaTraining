package com.custom;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by olga on 14.05.15.
 */
@WebServlet("/googleplus")
public class LoginServlet extends HttpServlet {
    private static final String CLIENT_ID = "696510088921-dt29bgjqgkvcgrtd0qk1t3e2jdtpomoi.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "xwUaE7Xj3BrTIPHfA5egR2Lv";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceBuilder builder = new ServiceBuilder();
        OAuthService service = builder.provider(Google2Api.class)
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback("http://localhost:8080/mywebapp-1.0-SNAPSHOT/oauth2callback")
                .scope(" openid profile email " +
                        "https://www.googleapis.com/auth/plus.login " +
                        "https://www.googleapis.com/auth/plus.me")
                .debug()
                .build(); //Now build the call
        HttpSession sess = req.getSession();
        sess.setAttribute("oauth2Service", service);
        resp.sendRedirect(service.getAuthorizationUrl(null));
    }
}
