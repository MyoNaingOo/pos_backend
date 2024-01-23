package com.mno.business.user.dto;


import com.mno.business.user.entity.Role;
import com.mno.business.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {
    private Long id;
    private String name;
    private String user_img;
    private String password;
    private String gmail;
    private String address;
    private Role role;
    private Boolean nameNotAvailable;
    private Boolean gmailNotAvailable;



}
