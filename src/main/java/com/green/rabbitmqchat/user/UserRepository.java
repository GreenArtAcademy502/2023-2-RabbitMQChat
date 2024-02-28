package com.green.rabbitmqchat.user;


import com.green.rabbitmqchat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUid(String uid);
}
