package com.kristina.telephony.server.rabbitmq;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class EventPublisher {
    private static final Logger LOGGER = Logger.getLogger(EventPublisher.class.getName());
    private final Gson gson = new Gson();

    private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    //отправка события в RabbitMQ
    private void publish(EventMessage event) throws IOException {
        Connection connection = RabbitMQConfig.getConnection();//получаем соединение. не открываем новое!
        Channel channel = null;
        try {
            channel = connection.createChannel();
            String json = gson.toJson(event);
            channel.basicPublish(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY,
                    null,
                    json.getBytes("UTF-8"));
            LOGGER.info("Published event: " + json);
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    LOGGER.warning("Error closing channel: " + e.getMessage());
                }
            }
        }
    }

    //форматирование даты
    private String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601_FORMAT);//шаблон даты
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public void publishIncoming(String phoneNumber, Date createdAt) throws IOException {
        EventMessage event = new EventMessage(
                EventType.INCOMING,
                phoneNumber,
                null,
                null,
                formatTime(createdAt));
        publish(event);
    }

    public void publishCancelled(String phoneNumber, Date createdAt) throws IOException {
        EventMessage event = new EventMessage(
                EventType.CANCELLED,
                phoneNumber,
                null,
                null,
                formatTime(createdAt));
        publish(event);
    }

    public void publishAnswered(String phoneNumber, Long extensionNumber, String operatorName, Date startedAt) throws IOException {
        EventMessage event = new EventMessage(
                EventType.ANSWERED,
                phoneNumber,
                extensionNumber,
                operatorName,
                formatTime(startedAt));
        publish(event);
    }

    public void publishEnded(String phoneNumber, Long extensionNumber, String operatorName, Date startedAt) throws IOException {
        EventMessage event = new EventMessage(
                EventType.ENDED,
                phoneNumber,
                extensionNumber,
                operatorName,
                formatTime(startedAt));
        publish(event);
    }
}
