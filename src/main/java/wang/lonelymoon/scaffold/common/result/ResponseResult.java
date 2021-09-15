package wang.lonelymoon.scaffold.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private Boolean success;
    private String msg;
    private T data;
}
