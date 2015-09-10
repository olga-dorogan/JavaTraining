package com.custom;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailExample {
    private String
            d_email = "dorogan.olga.n@gmail.com",
            d_host = "smtp.gmail.com",
            d_port = "587", // tls
            m_to = "maestrotees@gmail.com", // Target email address
            m_subject = "Testing",
            m_text = "Hey, this is a test email.";

    public JavaMailExample(String d_password) {

        Properties props = new Properties();
        props.put("mail.smtp.user", d_email);
        props.put("mail.smtp.host", d_host);
        props.put("mail.smtp.port", d_port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.debug", "true");
        try {
            Authenticator auth = new SMTPAuthenticator(d_email, d_password);
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(m_text);
            msg.setSubject(m_subject);
            msg.setFrom(new InternetAddress(d_email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You must enter password as command-line argument");
        }
        JavaMailExample javaMailExample = new JavaMailExample(args[0]);
        System.out.println("Message was sent");
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        private final String d_email;
        private final String d_password;

        private SMTPAuthenticator(String d_email, String d_password) {
            this.d_email = d_email;
            this.d_password = d_password;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(d_email, d_password);
        }
    }
}