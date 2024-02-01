package com.flab.goodchoiceconsumer;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private static final String TOPIC = "point";

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Async
    @KafkaListener(topics = TOPIC, groupId = "foo")
    public void sendJobCancelMail(String jobName) {
        System.out.println(
                "----------------------" + String.format("Consumed message : %s", jobName) + "----------------------");

        Context context = new Context();
        context.setVariable("jobName", jobName);
        String subject = "[GPU-IS-MINE] Job 포인트 만료 예정 메일";
        String body = templateEngine.process("job-point.html", context);
        sendMail("simbomi06@gmail.com", subject, body);
    }

    private void sendMail(String to, String subject, String body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("noreply@noreply.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
        };
        mailSender.send(messagePreparator);
    }

}
