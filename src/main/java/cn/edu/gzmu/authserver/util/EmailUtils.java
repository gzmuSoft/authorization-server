package cn.edu.gzmu.authserver.util;

import cn.edu.gzmu.authserver.model.properties.EmailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Japoul
 * @version 1.0
 * @date 2019-05-21 14:45
 */
@Component
@Async
@Slf4j
public class EmailUtils {

    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;
    private final TemplateEngine templateEngine;

    public EmailUtils(JavaMailSender javaMailSender, EmailConfig emailConfig, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.emailConfig = emailConfig;
        this.templateEngine = templateEngine;
    }

    /**
     * 简单文字邮件发送
     *
     * @param toEmail 接收者邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getUsername());
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    /**
     * 发送带模板的邮件
     *
     * @param toEmail   接收者邮箱
     * @param type      邮件类型
     * @param subject   邮件主题
     * @param template  邮件模板名称(默认资源路径下)
     * @param variables 模板内变量集合
     */
    public Future<String> sendTemplateMail(String toEmail, String type, String subject,
                                           String template, Map<String, Object> variables) {
        MimeMessage message = javaMailSender.createMimeMessage();
        Context context = new Context();
        variables.forEach(context::setVariable);
        context.setVariable("type", type);
        String content = templateEngine.process(template, context);
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(emailConfig.getUsername());
            messageHelper.setTo(toEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            javaMailSender.send(message);
            log.debug("向 {} 发送 {} 邮件成功", toEmail, type);
            return new AsyncResult<>("邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.warn("向 {} 发送 {} 邮件失败", toEmail, type);
            return new AsyncResult<>("邮件发送失败");
        }
    }
}

