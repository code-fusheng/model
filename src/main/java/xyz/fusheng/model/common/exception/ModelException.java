package xyz.fusheng.model.common.exception;

import lombok.Getter;
import xyz.fusheng.model.common.enums.ResultEnums;

/**
 * @FileName: ModelExcetion
 * @Author: code-fusheng
 * @Date: 2020/10/30 15:22
 * @version: 1.0
 * Description: 自定义异常
 */

@Getter
public class ModelException extends RuntimeException {

    /**
     * RuntimeException 运行时异常：是那些可能在 Java 虚拟机正常运行期间抛出的异常的超类。
     * 可能在执行方法期间抛出但未被捕获的RuntimeException 的任何子类都无需在 throws 子句中进行声明。
     * 它是Exception的子类。
     */

    private Integer errorCode = ResultEnums.ERROR.getCode();

    public ModelException(ResultEnums resultEnum) {
        super(resultEnum.getMsg());
        this.errorCode = resultEnum.getCode();
    }

    public ModelException(ResultEnums resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.errorCode = resultEnum.getCode();
    }

    public ModelException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ModelException(String msg) {
        super(msg);
    }

    public ModelException(Throwable throwable) {
        super(throwable);
    }

    public ModelException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
