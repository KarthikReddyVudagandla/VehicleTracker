package io.service;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SnsService {

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Value("${vehicle.alert.topic}")
    private String topic;

    @Autowired
    public SnsService(NotificationMessagingTemplate notificationMessagingTemplate) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }

    public void send(String subject, String message) {
        this.notificationMessagingTemplate.sendNotification(topic, message, subject);
    }
}
