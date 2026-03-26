package com.automotive.service;

import com.automotive.dto.RdvNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WebSocketNotificationService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public void notifyNewRendezVous(RdvNotificationDTO notification) {
    if (notification.getTimestamp() == null) {
      notification.setTimestamp(
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
      );
    }
    messagingTemplate.convertAndSend("/topic/rendezvous/new", notification);
  }

  public void notifyRendezVousUpdate(RdvNotificationDTO notification) {
    if (notification.getTimestamp() == null) {
      notification.setTimestamp(
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
      );
    }
    messagingTemplate.convertAndSend("/topic/rendezvous/update", notification);
  }
}
