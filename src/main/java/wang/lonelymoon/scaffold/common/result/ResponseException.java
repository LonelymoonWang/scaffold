package wang.lonelymoon.scaffold.common.result;

import lombok.Data;

@Data
public class ResponseException extends RuntimeException{
    /**
     * 错误码
     */
    protected Integer errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

}
