package com.custom;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by olga on 31.03.15.
 */
@MessageDriven(name = "topicChat", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "topic/Chat"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class LoggerMDB implements MessageListener {
    private Logger log = Logger.getLogger(LoggerMDB.class.getName());
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String msgText = ((TextMessage) message).getText();
                String date = new SimpleDateFormat("dd.MM -- hh:mm  | ").format(new Date());
                log.info(date+msgText);
            } catch (JMSException e) {
                log.info(e.getMessage());
            }
        }
    }
}
