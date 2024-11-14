package com.example.service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.Message;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;

@Service
@Component
@ComponentScan(basePackages = "com.example.repository")
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository repository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.repository = messageRepository;
        this.accountRepository = accountRepository;
    }
    public Message createMessage(Message message) {
        if (message.getMessageText().length() > 255 || message.getMessageText().isEmpty() || !accountRepository.existsById(message.getPostedBy())) {
            return null;
        }
        return repository.save(message);
    }
    public List<Message> getAll() {
        return repository.findAll();
    }
    public Message getById(int id) {
        return repository.findById(id).get();
    }
    //used for delete
    public boolean existById(int id) {
        return repository.existsById(id);
    }
    public void deleteById(int id) {
        repository.deleteById(id);
    }
    public Message updateById(int id, String text) {
        if (text.length() > 255 || text.isEmpty()) {
            return null;
        }
        Message message = repository.findById(id).get();
        message.setMessageText(text);
        return repository.save(message);
    }
    public List<Message> getByAccount(int accountId) {
        return repository.findByFk(accountId);
    }
    
}
