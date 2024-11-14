package com.example.controller;

import com.example.service.*;
import com.example.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@ComponentScan(basePackages = "com.example.service")
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account account) {
        Account registered = this.accountService.register(account);
        if (registered==null) {
            if (this.accountService.checkUsername(account)) {
                return ResponseEntity.status(400).body(null);
            }
            return ResponseEntity.status(409).body(null);
        } else {
            return ResponseEntity.status(200).body(registered);
        }
    }
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) {
        Account login = this.accountService.login(account);
        if (login==null) {
            return ResponseEntity.status(401).body(null);
        } else {
            return ResponseEntity.status(200).body(login);
        }
    }
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message created = this.messageService.createMessage(message);
        if (created==null) {
            return ResponseEntity.status(400).body(null);
        } else {
            return ResponseEntity.status(200).body(created);
        }
    }
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> allMessages() {
        return ResponseEntity.status(200).body(this.messageService.getAll());
    }
    @GetMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Message> getSpecificMessage(@PathVariable int id) {
        if (this.messageService.existById(id)) {
            return ResponseEntity.status(200).body(this.messageService.getById(id));
        }
        return ResponseEntity.status(200).body(null);
    }
    @DeleteMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Integer> deleteSpecificMessage(@PathVariable int id) {
        if (this.messageService.existById(id)) {
            this.messageService.deleteById(id);
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(200).body(null);
        }
    }
    @PatchMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Integer> updateSpecificMessage(@PathVariable int id, @RequestBody String text) {
        text = text.substring(17, (text.length() - 2));//json formatting fix
        if (this.messageService.existById(id)) {
            Message message = this.messageService.updateById(id, text);
            if (message == null) {
                return ResponseEntity.status(400).body(null);
            }
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body(null);
    }
    @GetMapping("accounts/{id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> userMessages(@PathVariable int id) {
        return ResponseEntity.status(200).body(this.messageService.getByAccount(id));
    }
}
