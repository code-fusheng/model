package xyz.fusheng.model.security.sms; /**
 * @author: code-fusheng
 * @Date: 2020/10/30 15:30
 */

import org.springframework.security.core.AuthenticationException;

/**
 * @FileName: ValidateCodeException
 * @Author: code-fusheng
 * @Date: 2020/10/30 15:30
 * @version: 1.0
 * Description:
 */

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 1674688107826790470L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
