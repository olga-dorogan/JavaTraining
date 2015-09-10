package com.custom.login;

import com.custom.model.vo.UserInfoVO;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by olga on 14.05.15.
 */
@WebServlet(urlPatterns = {"/oauth2callback"})
public class OAuth2CallbackServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2CallbackServlet.class);
    @EJB
    private LoginService loginService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        //Check if the user have rejected
        String error = req.getParameter("error");
        if ((null != error) && ("access_denied".equals(error.trim()))) {
            HttpSession sess = req.getSession();
            sess.invalidate();
            resp.sendRedirect(req.getContextPath());
            return;
        }
        //OK the user have consented so lets find out about the user

        HttpSession sess = req.getSession();
        OAuthService service = (OAuthService) sess.getAttribute("oauth2Service");
        //Get the all important authorization code
        String code = req.getParameter("code");
        //Construct the access token
        Token token = service.getAccessToken(null, new Verifier(code));
        //Save the token for the duration of the session
        sess.setAttribute("token", token);
        //Now do something with it - get the user's G+ profile
        OAuthRequest oReq = new OAuthRequest(Verb.GET,
                "https://www.googleapis.com/oauth2/v2/userinfo");
        service.signRequest(token, oReq);
        Response oResp = oReq.send();

        //Read the result
        JsonReader reader = Json.createReader(new ByteArrayInputStream(
                oResp.getBody().getBytes()));
        JsonObject profile = reader.readObject();


        UserInfoVO loginedUser = loginService.login(
                profile.getString("family_name"),
                profile.getString("name"),
                profile.getString("id"),
                profile.getString("email"));

        logger.debug("Username:\t{}",loginedUser.getFirstName());
        logger.debug("Email:\t{}", loginedUser.getEmail());
        logger.debug("Tasks:\t{}", loginedUser.getTasksStates());
        sess.setAttribute("user", loginedUser);
        resp.sendRedirect("welcomeUser.jsp");

    }
}
