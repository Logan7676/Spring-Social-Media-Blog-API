package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

import java.util.*;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    Message findByMessageId(int messageId);
    List<Message> findByPostedBy(int postedBy);
}
