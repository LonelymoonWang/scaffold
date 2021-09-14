package wang.lonelymoon.scaffold;

import com.github.hui.quick.plugin.md.Html2ImageWrapper;
import com.github.hui.quick.plugin.md.MarkDown2HtmlWrapper;
import com.github.hui.quick.plugin.md.entity.MarkdownEntity;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import wang.lonelymoon.scaffold.common.util.EmailUtils;
import wang.lonelymoon.scaffold.common.util.HttpUtils;
import wang.lonelymoon.scaffold.dao.mapper.UserMapper;
import wang.lonelymoon.scaffold.dao.repository.UserRepository;
import wang.lonelymoon.scaffold.entity.User;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private HttpUtils httpUtils;
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
        userRepository.save(new User(null, "zhangsan", 23));
    }

    @Test
    public void test004() {
        List<User> userList = userRepository.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(userList);
        log.info("json:{}", json);
    }

    @Test
    public void test005() {
        User user = userMapper.findById(1L);
        String json = gson.toJson(user);
        log.info("json:{}", json);
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
    public void test008() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("867007845@qq.com");
        message.setSubject("测试");
        message.setText("测试邮件");
        message.setFrom("867007845@qq.com");
        mailSender.send(message);
        log.info("【文本邮件】成功发送！to={}", "867007845@qq.com");
    }

    @Test
    public void test009() {
        //创建复杂的消息
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //参数   参数一是 mimeMessage   参数二是  是否上传文件  布尔值
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("今天开学");
            //Text兼容html片段    参数二 表示当前是否是html标签
            helper.setText("<b style='color:red'>今天开学啦！！</b>", true);
            //上传文件  参数文件名 参数二 文件位置 或一个流
            helper.addAttachment("bird.jpg", new File("/Users/wangpeng/work/PROJECTS/scaffold/test_out.jpg"));
            helper.setTo("867007845@qq.com");
            helper.setFrom("867007845@qq.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test010() throws MessagingException {
        String to = "867007845@qq.com";
        String subject = "Springboot 发送 HTML 附件邮件";
        String content = "<h2>Hi~</h2><p>第一封 Springboot HTML 附件邮件</p>";
        String filePath = "/Users/wangpeng/work/PROJECTS/scaffold/README.md";
        emailUtils.sendAttachmentMail(to, subject, content, filePath, filePath);
    }

    @Test
    public void test011() throws MessagingException {
        String to = "867007845@qq.com";
        String subject = "Springboot 发送 HTML 附件邮件";
        String content = "<h2>Hi~</h2><p>第一封 Springboot HTML 图片邮件</p><br/><img src=\"cid:img01\" /><img src=\"cid:img02\" />";
        String imgPath = "/Users/wangpeng/work/PROJECTS/scaffold/test_out.jpg";
        Map<String, String> imgMap = new HashMap<>();
        imgMap.put("img01", imgPath);
        imgMap.put("img02", imgPath);
        emailUtils.sendImgMail(to, subject, content, imgMap);
    }

    @Test
    public void test012() throws MessagingException {
        String to = "867007845@qq.com";
        String subject = "Springboot 发送 HTML 附件邮件";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", "lonelymoon");
        emailUtils.sendTemplateMail(to, subject, paramMap, "EmailTemplates");
    }
    @Test
    public void test013() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/wp867007845/issues/issues/1")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println("Server: " + response.header("Server"));
        System.out.println("Date: " + response.header("Date"));
        System.out.println("Vary: " + response.headers("Vary"));
//        log.info(gson.toJson(Objects.requireNonNull(response.body()).toString()));
        ResponseBody body = response.body();
        assert body != null;
        JsonObject json = gson.fromJson(body.charStream(), JsonObject.class);
        System.out.println(gson.toJson(json));
    }


    @Test
    public void test014() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/wp867007845/issues/issues/1/comments")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println("Server: " + response.header("Server"));
        System.out.println("Date: " + response.header("Date"));
        System.out.println("Vary: " + response.headers("Vary"));
//        log.info(gson.toJson(Objects.requireNonNull(response.body()).toString()));
        ResponseBody body = response.body();
        assert body != null;
        JsonArray json = gson.fromJson(body.charStream(), JsonArray.class);
        System.out.println(gson.toJson(json));
    }

    @Test
    public void test015() throws Exception {
        MarkdownEntity entity = MarkDown2HtmlWrapper.ofContent("some thing by img\n" +
                "\n" +
                "![image](https://user-images.githubusercontent.com/41058332/133209417-3eac8f26-3818-4a41-a492-30669fb8359c.png)\n" +
                "![image](https://user-images.githubusercontent.com/41058332/133209418-5b7b90c4-dbb7-458f-959c-bc44f58e9ea5.png)\n" +
                "\n" +
                "hehe\n");
        BufferedImage bf = Html2ImageWrapper.ofMd(entity)
                .setW(800)
                .setAutoW(false)
                .setAutoH(true)
                .setOutType("jpg")
                .build()
                .asImage();
        ImageIO.write(bf, "jpg", new File("test_out01.jpg"));
        System.out.println("---over---");
    }

    @Test
    public void test016() throws IOException {
        String url = "https://api.github.com/repos/wp867007845/issues/issues/1/comments";
        Multimap<String,String> headers = HashMultimap.create();
        headers.put("Accept", "application/vnd.github.v3+json");
        headers.put("Accept", "application/json; q=0.5");
        log.info(String.valueOf(headers));
        String body = httpUtils.get(url, headers,null);
        log.info(body);
    }


    @Test
    public void test017() throws IOException {

    }


    @Test
    void contextLoads() {
    }


}