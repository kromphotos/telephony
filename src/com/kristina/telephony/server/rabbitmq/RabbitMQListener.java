package com.kristina.telephony.server.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RabbitMQListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(RabbitMQListener.class.getName());

    // Вызывается при запуске веб-приложения
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            RabbitMQConfig.connect();
            LOGGER.info("RabbitMQ connection established!");
        } catch (IOException | TimeoutException e) {
            LOGGER.severe("Cannot connect to RabbitMQ: " + e.getMessage());
            throw new IllegalStateException("RabbitMQ initialization failed!", e);
        }
    }

    // Вызывается при остановке веб-приложения
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        RabbitMQConfig.close();
        LOGGER.info("RabbitMQ connection closed!");
    }
}
