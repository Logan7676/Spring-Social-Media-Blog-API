package com.example.controller;

import org.jboss.logging.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.*;

import javax.websocket.server.PathParam;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //@param: An account object of an account that needs to be registered
    //registers a new user if the account info does not already exist and the username and password
    //are the appropriate length
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if(accountService.findByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(409).build();
        }
        if (account.getUsername() == "" || account.getPassword().length()<4) {
            return ResponseEntity.status(400).build();
        }
        accountService.registerNewUser(account);
        return ResponseEntity.status(200)
            .body(accountService.findByUsername(account.getUsername()));

    }

    //@param: account object of the account that is trying to login
    //"logs in" the account if the username and password combination are valid
    @PostMapping("login")
    public ResponseEntity<Account> login (@RequestBody Account account) {
        if(accountService.login(account.getUsername(), account.getPassword()) == null) {
            return ResponseEntity.status(401).build();
        } else {
            accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.status(200)
                .body(accountService.login(account.getUsername(), account.getPassword()));
        }
    }

    //@param: a message object for the message being created
    //creates the message if the message is created by an existing user, and if the
    //message text is of appropriate length
    @PostMapping("messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        if(message.getMessageText() == "" || message.getMessageText().length() > 255 
        || accountService.findByAccountId(message.getPostedBy()) == null) {
            return ResponseEntity.status(400).build();
        } else {
            messageService.createNewMessage(message);
            return ResponseEntity.status(200).body(message);
        }
    }

    //returns all messages that currently exist in the database
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllAMessages());
    }

    //@param: path variable containing message id of the message being searched for
    //returns message object of the message Id if it exists
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return new ResponseEntity<>(messageService.findByMessageId(messageId), HttpStatus.OK);
    }

    //@param: path variable containing the message id of the message being deleted
    //returns message object of the deleted message if the message existed
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteByMessageId(@PathVariable int messageId) {
        if (messageService.findByMessageId(messageId) == null) {
            return ResponseEntity.status(200).build();
        } else {
            messageService.deleteByMessageId(messageId);
            return ResponseEntity.status(200).body(1);
        }
    }

    //@param: messageId: path variable containing the message id of the message being updated
    //@param: message: message object containg the new message text for the updated message
    //updates the message if the message exists, and the new message text id of valid length
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        if (message.getMessageText().length() < 2 || message.getMessageText().length() > 255 
        || messageService.findByMessageId(messageId) == null) {
            return ResponseEntity.status(400).build();
        } else {
            messageService.updateMessage(messageId, message.getMessageText());
            return ResponseEntity.status(200).body(1);
        }
    }

    //@param: path variable containing the accountId of the user
    //returns list of all messages posted by a specific user
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUser(accountId));
    }

}
