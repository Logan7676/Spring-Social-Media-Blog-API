package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.MessageRepository;
import java.util.*;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //creates a new message
    public void createNewMessage(Message message) {
        messageRepository.save(message);
    }

    //@return: a list of all current existing messages
    public List<Message> getAllAMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    //@param: the id of a message being searched for
    //@return: a message object containing the message id
    public Message findByMessageId(int messageId) throws ResourceNotFoundException{
        Message message = messageRepository.findByMessageId(messageId);
        return message;
    }

    //deletes a message by a specific message id
    public void deleteByMessageId(int messageId) {
        messageRepository.deleteById(messageId);
    }

    //@param: messageId: the id of the message being updated
    //@param: messageText: the contents of the new text for the updated message
    public void updateMessage(int messageId, String messageText) {
        Message message = findByMessageId(messageId);
        message.setMessageText(messageText);
        messageRepository.save(message);
    }

    //@param: accountId: the account id of the user whose messages are being searched
    //returns a list of all messages by a specific user
    public List<Message> getAllMessagesByUser(int accountId) {
        List<Message> messageList = messageRepository.findByPostedBy(accountId);
        return messageList;
    }
}
