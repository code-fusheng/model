package xyz.fusheng.model.security.sms;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @FileName: SmsCodeGenerator
 * @Author: code-fusheng
 * @Date: 2020/10/30 17:31
 * @version: 1.0
 * Description: 验证码生成器
 */

@Component
public class SmsCodeGenerator {

    public static String getRandom(int num) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < num; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    public SmsCode generate() {
        String code = getRandom(6);
        SmsCode smsCode = new SmsCode();
        smsCode.setCode(code);
        return smsCode;
    }

}
