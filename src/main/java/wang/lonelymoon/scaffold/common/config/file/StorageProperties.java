package wang.lonelymoon.scaffold.common.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangpeng
 * @description
 * @date 2021/9/15
 **/
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
