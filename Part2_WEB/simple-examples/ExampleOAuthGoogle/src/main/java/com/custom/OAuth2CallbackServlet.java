package com.custom;

import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.AsyncContext;
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
@WebServlet(urlPatterns = {"/oauth2callback"}, asyncSupported = true)
public class OAuth2CallbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        //Check if the user have rejected
        String error = req.getParameter("error");
        if ((null != error) && ("access_denied" .equals(error.trim()))) {
            HttpSession sess = req.getSession();
            sess.invalidate();
            resp.sendRedirect(req.getContextPath());
            return;
        }
        //OK the user have consented so lets find out about the user
        AsyncContext asyncCtx = req.startAsync();
        asyncCtx.start(new GetUserInfo(req, resp, asyncCtx));
    }
}

class GetUserInfo implements Runnable {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private AsyncContext asyncCtx;

    public GetUserInfo(HttpServletRequest req, HttpServletResponse resp, AsyncContext asyncCtx) {
        this.req = req;
        this.resp = resp;
        this.asyncCtx = asyncCtx;
    }

    @Override
    public void run() {
        HttpSession sess = req.getSession();
        OAuthService service = (OAuthService) sess.getAttribute("oauth2Service");
        //Get the all important authorization code
        String code = req.getParameter("code");
        //Construct the access token
        Token token = service.getAccessToken(null, new Verifier(code));
        //Save the token for the duration of the session
        sess.setAttribute("token", token);
        //Perform a proxy login
        try {
            req.login("fred", "fredfred");
        } catch (ServletException e) {
            //Handle error - should not happen
        }
        //Now do something with it - get the user's G+ profile
        OAuthRequest oReq = new OAuthRequest(Verb.GET,
                "https://www.googleapis.com/oauth2/v2/userinfo");
        service.signRequest(token, oReq);
        Response oResp = oReq.send();

        //Read the result
        JsonReader reader = Json.createReader(new ByteArrayInputStream(
                oResp.getBody().getBytes()));
        JsonObject profile = reader.readObject();
        //Save the user details somewhere or associate it with
        sess.setAttribute("name", profile.getString("name"));
        sess.setAttribute("email", profile.getString("email"));

        System.out.println(">>>>>>>>>>>> Username: " + sess.getAttribute("name"));
        System.out.println(">>>>>>>>>>>> Email: " + sess.getAttribute("email"));
        asyncCtx.complete();
    }
}
