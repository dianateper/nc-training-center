package ua.com.nc.nctrainingproject.controllers;


import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.nc.nctrainingproject.services.PasswordRecoverService;

@Controller
public class PasswordRecoverController {


    @Autowired
    private PasswordRecoverService passwordRecoverService;

    @RequestMapping("/email")
    @ResponseBody
    public void emailSender(@RequestParam String email,@RequestParam String userName) {
        try {
            passwordRecoverService.makeEmail(email,userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/recover")
    @ResponseBody
    public boolean passwordRecover(@RequestParam String code,
                                @RequestParam String userName,
                                @RequestParam String newPassword) {
    return passwordRecoverService.passwordRecover(code,newPassword,userName);
    }


}
