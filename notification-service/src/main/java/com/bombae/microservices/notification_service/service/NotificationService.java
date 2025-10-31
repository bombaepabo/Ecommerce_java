package com.bombae.microservices.notification_service.service;

import com.bombae.microservices.notification_service.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed",groupId = "notification-service")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("==========================================================");
        log.info("KAFKA MESSAGE RECEIVED!");
        log.info("Order Number: {}", orderPlacedEvent.getOrderNumber());
        log.info("Email: {}", orderPlacedEvent.getEmail());
        log.info("==========================================================");

        // Validate
        if (orderPlacedEvent == null ||
                orderPlacedEvent.getEmail() == null ||
                orderPlacedEvent.getOrderNumber() == null) {
            log.error("Invalid event received");
            return;
        }

        try {
            sendEmail(orderPlacedEvent);
            log.info("✓ Email sent successfully to: {}", orderPlacedEvent.getEmail());
        } catch (Exception e) {
            log.error("✗ Failed to send email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private void sendEmail(OrderPlacedEvent event) {
        log.info("Preparing email for: {}", event.getEmail());

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo(event.getEmail().toString());
            messageHelper.setSubject(String.format("Order %s Confirmed", event.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Hi,
                    
                    Your order with order number %s has been placed successfully.
                    
                    Best Regards,
                    Spring Shop
                    """, event.getOrderNumber()));
        };

        log.info("Sending email via JavaMailSender...");
        javaMailSender.send(messagePreparator);
        log.info("JavaMailSender.send() completed");
    }
}