package com.nod.notification;

import com.nod.clients.notifications.NotificationRequest;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    public void send(NotificationRequest notificationRequest){
        notificationRepository.save(Notification
                .builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .sender("kushani").message(notificationRequest.message())
                .sentAt(LocalDateTime.now())
                .build());
    }
}
