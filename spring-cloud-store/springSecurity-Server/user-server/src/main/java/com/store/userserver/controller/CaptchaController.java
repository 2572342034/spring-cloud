package com.store.userserver.controller;

import com.google.code.kaptcha.Producer;

import com.store.commen.utils.RedisCache;
import com.store.commen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
@Api(tags = "登录相关接口")
@RestController
@Slf4j
@RequestMapping("user")
public class CaptchaController {

    @Autowired
    private Producer captchaProducer;
    @Autowired
    private RedisCache redisCache;
    @ApiOperation("获取验证码接口")
    @GetMapping("captcha")

    public void generateCaptcha(HttpServletResponse response,HttpSession httpSession) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成验证码文本
        String captchaText = captchaProducer.createText();
        // 生成并输出验证码图片
        BufferedImage image = captchaProducer.createImage(captchaText);
        // 将图片写入响应流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        byte[] captchaData = outputStream.toByteArray();
        httpSession.setAttribute("captchaText", captchaText);
//        将数据流存入redis缓存
        redisCache.setCacheObject("captchaText" ,captchaText);
        // 输出到HTTP响应
        response.getOutputStream().write(captchaData);
        response.getOutputStream().flush();
//        return new ResponseResult(200,"Success",captchaData);

    }
    @ApiOperation("验证验证码接口")
    @PostMapping("verify-captcha")
    public ResponseResult verifyCaptcha(@RequestParam(value = "userInputCaptcha") String userInputCaptcha, HttpSession httpSession) {
        String imgCaptcha = redisCache.getCacheObject("captchaText");

        if (imgCaptcha != null && imgCaptcha.equalsIgnoreCase(userInputCaptcha)) {
            // 验证成功，清除session中的验证码
            httpSession.removeAttribute("captchaText");
            redisCache.deleteObject("captchaText");
            return new ResponseResult(200, "Success");

            // 执行其他业务逻辑...
        } else {
            // 验证失败，提示错误信息
            redisCache.deleteObject("captchaText");
            return new ResponseResult(304, "验证码错误");
        }
    }
}