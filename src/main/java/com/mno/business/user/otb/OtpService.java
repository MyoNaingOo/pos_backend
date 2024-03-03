package com.mno.business.user.otb;

import com.mno.business.user.entity.User;
import com.mno.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepo otpRepo;
    private final GmailSender gmailSender;
    private final UserService userService;


    private Optional<Otp> findByOtp(int otp) {
        return otpRepo.findByOtp(otp);
    }

    public void sendOtp(String email, String token) {
        Random random = new Random();
        int otpCode = random.nextInt(100000, 999999);
        String mail_body = "Hi "+ email +
                "\n"+"your authentication code is :" + otpCode;
        gmailSender.sendmail(email,"Login Authentication otp",mail_body);

//        System.out.println(mail_body);
        Otp createOtp = Otp.builder()
                .gmail(email)
                .token(token)
                .otp(otpCode)
                .build();
        otpRepo.save(createOtp);

    }

    public OtpDtoResponse authenticateByotp(OtpDtoRequest otpDtoRequest) {
        Otp otp = findByOtp(otpDtoRequest.getOtp()).orElse(null);
        assert otp != null;
        otpRepo.delete(otp);
        otpRepo.deleteAllByGmail(otp.getGmail());
        if (otpDtoRequest.getGmail().equals(otp.getGmail())) {
            String gmail = otp.getGmail();
            User user = userService.userfindByGmail(gmail);

            OtpDtoResponse otpDtoResponse;
            if (user == null) {
                otpDtoResponse = OtpDtoResponse.builder()
                        .gmail(gmail)
                        .checkotp(true)
                        .logined(false)
                        .massage("User is not register")
                        .build();
            } else {
                otpDtoResponse = OtpDtoResponse.builder()
                        .gmail(gmail)
                        .user(user)
                        .token(otp.getToken())
                        .checkotp(true)
                        .logined(true)
                        .massage("Successful Login")
                        .build();

            }
            return otpDtoResponse;
        } else {
            return OtpDtoResponse.builder()
                    .gmail(otpDtoRequest.getGmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("Not same Otp Try again")
                    .build();
        }
    }

    public OtpDtoResponse registerByotp(OtpDtoRequest otpDtoRequest) {
        Otp otp = findByOtp(otpDtoRequest.getOtp()).orElse(null);
        User user = userService.userfindByGmail(otpDtoRequest.getGmail());
        OtpDtoResponse otpDtoResponse;

        if (otp != null) {
            otpRepo.delete(otp);
            otpRepo.deleteAllByGmail(otpDtoRequest.getGmail());
            if (otpDtoRequest.getGmail().equals(otp.getGmail())) {

                otpDtoResponse = OtpDtoResponse.builder()
                        .gmail(otpDtoRequest.getGmail())
                        .user(userService.responeUser(user))
                        .token(otp.getToken())
                        .checkotp(true)
                        .logined(true)
                        .massage("Successful Login")
                        .build();
                return otpDtoResponse;
            } else {
                otpRepo.delete(otp);
                userService.deleteUser(user.getId());

                return OtpDtoResponse.builder()
                        .gmail(otpDtoRequest.getGmail())
                        .checkotp(false)
                        .logined(false)
                        .massage("email or otp is fail")
                        .build();
            }
        } else {
            otpRepo.deleteAllByGmail(otpDtoRequest.getGmail());
            userService.deleteUser(user.getId());
            return OtpDtoResponse.builder()
                    .gmail(otpDtoRequest.getGmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("email or otp is fail")
                    .build();
        }




    }
}
