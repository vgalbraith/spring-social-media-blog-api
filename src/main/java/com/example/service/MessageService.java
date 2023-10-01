package com.example.service;

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
     * @returns The persisted message including it's newly assigned message_id.
     */
    public Message persistMessage(Message message) throws Exception {
    	if (message.getMessage_text().equals("")) {
            throw new BadRequestException("Message text cannot be blank.");
        }
    	
        if (message.getMessage_text().length() >= 255) {
            throw new BadRequestException("Message text must be less than 255 characters long.");
        }
    	
        if (accountRepository.findById(message.getPosted_by()).isEmpty()) {
            throw new BadRequestException("Posted by user not found.");
        }

        return messageRepository.save(message);
    }
}
