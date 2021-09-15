package wang.lonelymoon.scaffold.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseException extends RuntimeException{
    /**
     * 错误信息
     */
    private String errorMsg;

}
