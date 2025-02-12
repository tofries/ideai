package com.forfries.ideai.user.callback;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.forfries.ideai.user.model.User;
import com.forfries.ideai.user.service.UserService;
import com.tofries.ideai.common.constant.UserConstant;
import com.tofries.wxlogin.callback.WeixinLoginCallback;
import com.tofries.wxlogin.websocket.WeixinWebSocketHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

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

        User newUser = User.builder()
                .openid(openid)
                .role(UserConstant.DEFAULT_ROLE)
                .build();
        userService.save(newUser);
        return "注册并登陆成功";
    }
}
