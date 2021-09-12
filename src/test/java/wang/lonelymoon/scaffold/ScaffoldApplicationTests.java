package wang.lonelymoon.scaffold;

import com.github.hui.quick.plugin.md.Html2ImageWrapper;
import com.github.hui.quick.plugin.md.MarkDown2HtmlWrapper;
import com.github.hui.quick.plugin.md.entity.MarkdownEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import wang.lonelymoon.scaffold.dao.mapper.UserMapper;
import wang.lonelymoon.scaffold.dao.repository.UserRepository;
import wang.lonelymoon.scaffold.entity.User;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class ScaffoldApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> template;

    @Autowired
    private HashOperations<String, String, Object> redisHash;// Redis Hash

    @Autowired
    private UserRepository userRepository;

    @Resource
    private UserMapper userMapper;

    @Resource
    private Gson gson;

    @Test
    public void test001() {
        ValueOperations<String, Object> redisString = template.opsForValue();
        // SET key value: 设置指定 key 的值
        redisString.set("strKey1", "hello spring boot redis");
        // GET key: 获取指定 key 的值
        String value = (String) redisString.get("strKey1");
        System.out.println(value);
        redisString.set("strKey2", new User(110086L, "theName", 11));
        User user = (User) redisString.get("strKey2");
        System.out.println(user);
    }



    @Test
    public void test002() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "10010");
        map.put("name", "redis_name");
        map.put("amount", 12.34D);
        map.put("age", 11);
        redisHash.putAll("hashKey", map);
        // HGET key field 获取存储在哈希表中指定字段的值
        String name = (String) redisHash.get("hashKey", "name");
        System.out.println(name);
        // HGET key field
        Double amount = (Double) redisHash.get("hashKey", "amount");
        System.out.println(amount);
        // HGETALL key 获取在哈希表中指定 key 的所有字段和值
        Map<String, Object> map2 = redisHash.entries("hashKey");
        System.out.println(map2);
        // HKEYS key 获取在哈希表中指定 key 的所有字段
        Set<String> keySet = redisHash.keys("hashKey");
        System.out.println(keySet);
        // HVALS key 获取在哈希表中指定 key 的所有值
        List<Object> valueList = redisHash.values("hashKey");
        System.out.println(valueList);
    }

    @Test
    public void test003() {
        userRepository.save(new User(null,"zhangsan",23));
    }

    @Test
    public void test004() {
        List<User> userList = userRepository.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(userList);
        log.info("json:{}",json);
    }

    @Test
    public void test005() {
        User user = userMapper.findById(1L);
        String json = gson.toJson(user);
        log.info("json:{}",json);
        User user1 = gson.fromJson(json, User.class);
        System.out.println(user1);
    }

    @Test
    public void test006() throws Exception {
        MarkdownEntity entity = MarkDown2HtmlWrapper.ofFile("D:\\我的项目\\scaffold\\HELP.md");
        BufferedImage bf = Html2ImageWrapper.ofMd(entity)
                .setW(800)
                .setAutoW(false)
                .setAutoH(true)
                .setOutType("jpg")
                .build()
                .asImage();
        ImageIO.write(bf, "jpg", new File("test_out.jpg"));
        System.out.println("---over---");
    }


    @Test
    public void test007() throws Exception {
        MarkdownEntity markdownEntity = MarkDown2HtmlWrapper.ofFile("D:\\我的项目\\scaffold\\HELP.md");
        System.out.println(markdownEntity.toString());
    }

    @Test
    void contextLoads() {
    }


}
