package com.mno.business.user.otb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendmail(String toEmail,String subject,String body){

        SimpleMailMessage massage = new SimpleMailMessage();
        massage.setFrom("myonaingoo623gmail.com");
        massage.setTo(toEmail);
        massage.setText(body);
        massage.setSubject(subject);
        mailSender.send(massage);
    }


}
