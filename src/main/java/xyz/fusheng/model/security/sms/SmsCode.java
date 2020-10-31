package xyz.fusheng.model.security.sms;

import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: SmsCode
 * @Author: code-fusheng
 * @Date: 2020/10/30 12:44
 * @version: 1.0
 * Description: 短信验证码
 */

@Data
public class SmsCode implements Serializable {

    private String code;

}
