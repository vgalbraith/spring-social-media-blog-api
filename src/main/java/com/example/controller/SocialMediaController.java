package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Endpoint for registering a new account.
     * @param account The account to be registered.
     * @return The persisted account including it's newly assigned account_id.
     * @throws BadRequestException Thrown if there is an issue with the client's request.
     * @throws ConflictException Thrown if the username is already associated with a registered account.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) throws Exception {
        Account addedAccount = accountService.persistAccount(account);
        return new ResponseEntity<Account>(addedAccount, HttpStatus.OK);
    }

    /**
     * Endpoint for verifying a user login.
     * @param account An account containing a username/password combination to be verified.
     * @return The verified account object.
     * @throws UnauthorizedException Thrown if the username/password combination is not accosiated with a registered account.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) throws Exception {
        Account verifiedAccount = accountService.verifyAccount(account);
        return new ResponseEntity<Account>(verifiedAccount, HttpStatus.OK);
    }

    /**
     * Endpoint for creating a new message.
     * @param message The message to be created.
     * @return The persisted message including it's newly assigned message_id.
     * @throws BadRequestException Thrown if there is an issue with the client's request.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) throws Exception {
        Message addedMessage = messageService.persistMessage(message);
        return new ResponseEntity<Message>(addedMessage, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving all messages.
     * @return A list of all messages.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving a message given it's message_id.
     * @param message_id 
     * @return The associated message object, empty body if not found.
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        Message message = messageService.getMessageById(message_id);
        if (message == null) return null;
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
