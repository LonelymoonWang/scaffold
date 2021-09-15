package wang.lonelymoon.scaffold.common.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.lonelymoon.scaffold.common.result.ResponseException;
import wang.lonelymoon.scaffold.common.result.ResponseResult;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ResponseException.class)
    @ResponseBody
    public ResponseResult<Object> bizExceptionHandler(HttpServletRequest req, ResponseException e) {
        log.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return new ResponseResult<>(false, e.getErrorMsg(), null);
    }

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResponseResult<Object> exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return new ResponseResult<>(false, "空指针", null);
    }

    /**
     * 处理其他异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult<Object> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:", e);
        return new ResponseResult<>(false, "系统异常", null);
    }
}
