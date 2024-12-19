package com.forfries.ideaiuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.forfries.wxlogin.callback.WeixinLoginCallback;
import com.forfries.wxlogin.websocket.WeixinWebSocketHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomWeixinLoginCallBack implements WeixinLoginCallback {

    private final UserService userService;

    private final WeixinWebSocketHandler weixinWebSocketHandler;

    @SneakyThrows
    @Override
    public String onLoginSuccess(String sceneId, String openid) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid", openid);
        long count = userService.count(userQueryWrapper);

        weixinWebSocketHandler.sendLoginMessage(sceneId,openid);

        if (count > 0) {
            return "登陆成功";
        }
        //TODO 这里设置user_role
        User newUser = User.builder()
                .openid(openid)
                .role("")
                .build();
        userService.save(newUser);
        return "注册并登陆成功";
    }
}
