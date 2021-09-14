package wang.lonelymoon.scaffold;

import cn.hutool.core.map.MapUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.lonelymoon.scaffold.common.result.ResponseResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableCaching
@MapperScan("wang.lonelymoon.scaffold.dao.mapper")
@EnableScheduling //开启定时任务
@RestController
public class ScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScaffoldApplication.class, args);
    }

    @RequestMapping("/oauth/redirect")
    public String GetAuthCode(String code) {
        return code;
    }

}


//>openssl rsa -in lonelymoonscaffold.2021-09-14.private-key.pem -pubout -outform DER | openssl sha256 -binary | openssl base64
