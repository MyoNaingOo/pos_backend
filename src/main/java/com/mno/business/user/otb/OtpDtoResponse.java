package com.mno.business.user.otb;


import com.mno.business.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpDtoResponse {

    public String gmail;
    public boolean logined;
    public boolean checkotp;
    public String massage;
    public String token;
    private User user;

}
