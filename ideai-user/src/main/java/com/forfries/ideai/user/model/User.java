package com.forfries.ideai.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@TableName(value = "user")
public class User implements Serializable {
    private String id;
    private String openid;
    private String role;
}
