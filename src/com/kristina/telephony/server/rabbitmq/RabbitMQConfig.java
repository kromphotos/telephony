package com.kristina.telephony.server.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "telephony.events";
    public static final String ROUTING_KEY = "call.event";
    private static final String FACTORY_USERNAME = "guest";
    private static final String FACTORY_PASSWORD = "guest";
    private static final String FACTORY_HOST = "localhost";
    
    private static final Logger LOGGER = Logger.getLogger(RabbitMQConfig.class.getName());
    
    // Подключение к RabbitMQ
    //все запросы используют одно соединение
    private static Connection connection;
    
    private RabbitMQConfig() {
        
    }
    
    public static synchronized void connect() throws IOException, TimeoutException {
        if (connection != null && connection.isOpen()) {
            return;
        }
        //соединение между телефонией и RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        
        // Адрес RabbitMQ
        factory.setHost(FACTORY_HOST);

        // Порт RabbitMQ
        factory.setPort(5672);

        // Данные для авторизации
        factory.setUsername(FACTORY_USERNAME);
        factory.setPassword(FACTORY_PASSWORD);
        
        connection = factory.newConnection();
        
        // Создаём временный канал для настройки Exchange
        try (Channel channel = connection.createChannel()) {
            //объявление exchange
            channel.exchangeDeclare(EXCHANGE_NAME,
                    BuiltinExchangeType.DIRECT,
                    true);
        } catch (IOException | TimeoutException e) {
            close();
            throw e;
        }
    }
    
    //получение соединения
    public static synchronized Connection getConnection() {
        if (connection == null || !connection.isOpen()) {
            throw new IllegalStateException("RabbitMQ connection is not available!");
        }
        return connection;
    }
    
    //закрытие соединения
    public static synchronized void close() {
        try {
            if (connection != null && connection.isOpen()) {
                connection.close();
                LOGGER.info("RabbitMQ connection closed!");
            }
        } catch (IOException e) {
            LOGGER.severe("Error closing RabbitMQ connection: " + e.getMessage());
        } finally {
            connection = null;
        }
    }
}
 