package com.automotive.repository;

import com.automotive.model.Notification;
import com.automotive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);

    List<Notification> findByUserAndLueFalse(User user);

    long countByUserAndLueFalse(User user);
}
