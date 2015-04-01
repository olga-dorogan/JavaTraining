/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.custom;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;

public class CommandLineChat implements MessageListener {
    private static final Logger log = Logger.getLogger(CommandLineChat.class.getName());

    // Set up all the default values
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/topic/Chat";
    private static final String DEFAULT_USERNAME = "quickstartUser";
    private static final String DEFAULT_PASSWORD = "quickstartPwd1!";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:8080";

    private String username;

    public CommandLineChat(String username) {
        this.username = username;
    }

    private static Context getInitialContext() throws NamingException {
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);
        env.put(Context.SECURITY_PRINCIPAL, DEFAULT_USERNAME);
        env.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD);
        return new InitialContext(env);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Username is required!");
            System.exit(0);
        }
        String username = args[0];
        Context namingContext = null;

        try {
            namingContext = getInitialContext();
            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) namingContext.lookup(DEFAULT_CONNECTION_FACTORY);

            Topic topic = (Topic) namingContext.lookup(DEFAULT_DESTINATION);
            TopicConnection topicConnection = connectionFactory.createTopicConnection(DEFAULT_USERNAME, DEFAULT_PASSWORD);
            CommandLineChat chat = new CommandLineChat(username);
            chat.subscribe(topicConnection, topic);
            chat.publish(topicConnection, topic);
        } catch (NamingException e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException e) {
                    log.severe(e.getMessage());
                }
            }
        }
    }

    private void publish(TopicConnection topicConnection, Topic topic) throws JMSException, IOException {
        TopicSession publisherSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicPublisher topicPublisher = publisherSession.createPublisher(topic);
        topicConnection.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String msgToSend = reader.readLine();
            if (msgToSend.equalsIgnoreCase("exit")) {
                topicConnection.close();
                System.exit(0);
            }
            TextMessage message = publisherSession.createTextMessage();
            message.setText(username + ": " + msgToSend);
            topicPublisher.publish(message);
        }
    }

    private void subscribe(TopicConnection topicConnection, Topic topic) throws JMSException {
        TopicSession subscriberSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        subscriberSession.createSubscriber(topic).setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String msgText = ((TextMessage) message).getText();
                if (!msgText.startsWith(username)) {
                    System.out.println(msgText);
                }
            } catch (JMSException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

