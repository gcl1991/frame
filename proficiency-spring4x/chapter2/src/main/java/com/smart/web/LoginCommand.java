package com.smart.web;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginCommand {
    private String userName;
    private String password;
}
