package com.store.commen.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.store.commen.exception.BadRequestException;
import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(0,"已激活" ),
    FROZEN(1, "禁止使用"),
    ;
    @EnumValue
    int value;
    String desc;

    UserStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UserStatus of(int value) {
        if (value == 0) {
            return NORMAL;
        }
        if (value == 1) {
            return FROZEN;
        }
        throw new BadRequestException("账户状态错误");
    }
}