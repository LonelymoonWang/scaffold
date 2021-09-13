package wang.lonelymoon.scaffold.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author wangpeng
 * @description 发送邮件工具类
 * @date 2021/9/13
 **/
@Slf4j
@Component
public class EmailUtils {

    @Resource
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Resource
    private TemplateEngine templateEngine;

    /**
     * 发送普通文本邮件
     *
     * @param to      收件人邮箱
     * @param subject 主题
     * @param text    内容
     */
    public void sendOrdinaryMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(emailFrom);
        mailSender.send(message);
    }

    /**
     * 发送 HTML 邮件
     *
     * @param to      收件人邮箱
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom(emailFrom);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        // true 为 HTML 邮件
        messageHelper.setText(content, true);
        mailSender.send(message);
        log.info("【HTML 邮件】成功发送！to={}", to);
    }

    /**
     * 发送带附件的邮件
     *
     * @param to      收件人邮箱
     * @param subject 主题
     * @param content 内容
     * @param fileArr 附件
     */
    public void sendAttachmentMail(String to, String subject, String content, String... fileArr)
            throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(emailFrom);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);

        // 添加附件
        for (String filePath : fileArr) {
            FileSystemResource fileResource = new FileSystemResource(new File(filePath));
            if (fileResource.exists()) {
                String filename = fileResource.getFilename();
                assert filename != null;
                messageHelper.addAttachment(filename, fileResource);
            }
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送带图片的邮件
     *
     * @param to      收件人邮箱
     * @param subject 主题
     * @param content 内容
     * @param imgMap  图片
     */
    public void sendImgMail(String to, String subject, String content, Map<String, String> imgMap)
            throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(emailFrom);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        // 添加图片
        for (Map.Entry<String, String> entry : imgMap.entrySet()) {
            FileSystemResource fileResource = new FileSystemResource(new File(entry.getValue()));
            if (fileResource.exists()) {
                messageHelper.addInline(entry.getKey(), fileResource);
            }
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送模版邮件
     *
     * @param to       收件人邮箱
     * @param subject  主题
     * @param paramMap 传参
     * @param template 模板文件
     */
    public void sendTemplateMail(String to, String subject, Map<String, Object> paramMap, String template)
            throws MessagingException {
        Context context = new Context();
        // 设置变量的值
        context.setVariables(paramMap);
        String emailContent = templateEngine.process(template, context);
        sendHtmlMail(to, subject, emailContent);
    }

}
