package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.request.SmsRequest;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsController {

    private final JavaMailSender mailSender;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pasha.solodyankin2006@gmail.com");
        message.setTo(request.to());
        message.setSubject("SMS");
        message.setText(request.message());
        mailSender.send(message);
        return ResponseEntity.ok("Sent");
    }
}
