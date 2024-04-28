package com.store.userserver.model.vo;

import lombok.Data;

@Data
public class UserLoginVO {
    private String token;
    private Long userId;
    private String username;
    private Integer balance;
}
