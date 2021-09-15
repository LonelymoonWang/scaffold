package wang.lonelymoon.scaffold.common.config.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangpeng
 * @description
 * @date 2021/9/15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String name;
    private String uri;
    private String type;
    private long size;
}
