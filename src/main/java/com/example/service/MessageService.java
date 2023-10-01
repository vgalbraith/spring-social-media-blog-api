package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Used to persist a message to the repository.
     * @param message The message to be added.
     * @return The persisted message including it's newly assigned message_id.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Message persistMessage(Message message) {
    	if (message.getMessage_text().equals("")) {
            throw new BadRequestException("Message text cannot be blank.");
        }
    	
        if (message.getMessage_text().length() >= 255) {
            throw new BadRequestException("Message text must be less than 255 characters long.");
        }
    	
        if (accountRepository.existsById(message.getPosted_by())) {
            return messageRepository.save(message);
        } else {
            throw new BadRequestException("Posted by user not found.");
        }
    }

    /**
     * Used to retrieve all messages from the repository.
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Used to retrieve a message from the repository given it's message_id.
     * @param message_id
     * @return The associated message object, null if message_id not found.
     */
    public Message getMessageById(int message_id) {
        return messageRepository.findById(message_id).orElse(null);
    }

    /**
     * Used to delete a message from the repository given it's message_id.
     * @param message_id
     * @return The number of rows affected.
     */
    public int deleteMessage(int message_id) {
        if (messageRepository.existsById(message_id)) {
            messageRepository.deleteById(message_id);
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Used to update a message in the repository given it's message_id.
     * @param message_id
     * @param message_text to replace existing message_text.
     * @return The number of rows affected.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public int updateMessage(int message_id, String message_text) {
    	if (message_text.equals("")) {
            throw new BadRequestException("Message text cannot be blank.");
        }
    	
        if (message_text.length() >= 255) {
            throw new BadRequestException("Message text must be less than 255 characters long.");
        }

        if (messageRepository.existsById(message_id)) {
            Message updatedMessage = this.getMessageById(message_id);
            updatedMessage.setMessage_text(message_text);
            messageRepository.save(updatedMessage);
            return 1;
        } else {
            throw new BadRequestException("Message id is invalid.");
        }
    }

    /**
     * Endpoint for retrieving all messages posted by the given account_id.
     * @param account_id
     * @return A list of all applicable messages.
     */
    public List<Message> getAllMessagesByUserId(int account_id) {
        return messageRepository.findAllByAccountId(account_id);
    }
}
