package com.custom;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by olga on 08.05.15.
 */
@WebServlet("/testMail")
@MailSessionDefinition(name = "java:comp/myMailSession",
        host = "smtp.gmail.com",
        transportProtocol = "smtp",
        properties = {
                "mail.smtp.port=587",
                "mail.smtp.starttls.enable=true",
                "mail.debug=true"
        })
public class MainServlet extends HttpServlet {
    @Resource(lookup = "java:comp/myMailSession")
    Session session;

    @Inject
    Credentials creds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            out.println("Sending message from \""
                    + creds.getFrom()
                    + "\" to \""
                    + creds.getTo()
                    + "\"...<br>");

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(creds.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(creds.getTo()));
            message.setSubject("Test msg from JavaApp: "
                    + Calendar.getInstance().getTime());
            message.setText("Test message");

            Transport t = session.getTransport();
            t.connect(creds.getFrom(), creds.getPassword());
            t.sendMessage(message, message.getAllRecipients());

            out.println("message sent!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
