package com.mno.business.user.otb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("authenticate")
    private OtpDtoResponse authenticateByotp(@RequestBody OtpDtoRequest otpDtoRequest){
        return otpService.authenticateByotp(otpDtoRequest);
    }

    @PostMapping("register")
    private OtpDtoResponse checkOtp(@RequestBody OtpDtoRequest otpDtoRequest){
        return otpService.registerByotp(otpDtoRequest);
    }

}
