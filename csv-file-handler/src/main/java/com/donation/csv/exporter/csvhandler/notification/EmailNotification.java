package com.donation.csv.exporter.csvhandler.notification;

import com.donation.csv.exporter.csvhandler.exception.MailException;
import com.donation.csv.exporter.csvhandler.model.EmailStatus;
import com.donation.csv.exporter.csvhandler.model.ImageMapper;
import com.donation.csv.exporter.csvhandler.model.Mail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Slf4j
@Component
public class EmailNotification {

    private final JavaMailSender javaMailSender;
    private final String senderMailAddress;

    @Autowired
    public EmailNotification(JavaMailSender javaMailSender, @Value("${senderMailAddress}") String senderMailAddress) {
        this.javaMailSender = javaMailSender;
        this.senderMailAddress = senderMailAddress;
    }

    public EmailStatus sendPlainText(Mail mail, String text) {
        return sendM(mail, text, false);
    }

    public EmailStatus sendHtml(Mail mail, String htmlBody) {
        return sendM(mail, htmlBody, true);
    }

    private EmailStatus sendM(Mail mail, String text, Boolean isHtml) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(mail.getRecipient());
            helper.setSubject(mail.getSubject());
            helper.setFrom(senderMailAddress);
            helper.setText(text, isHtml);
            List<ImageMapper> imageMappers = defaultIfNull(mail.getImageMappers(), emptyList());
            imageMappers.forEach(imageMapper -> addInLineImage(helper, imageMapper));
            javaMailSender.send(message);
            log.info("Sent email '{}' to: {}", mail.getSubject(), mail.getRecipient());
            return new EmailStatus(mail.getRecipient(), mail.getSubject(), text).success();
        } catch (Exception e) {
            log.error(String.format("Problem with sending email to: %s, error message: %s", mail.getRecipient(), e.getMessage()));
            return new EmailStatus(mail.getRecipient(), mail.getSubject(), text).error(e.getMessage());
        }
    }

    private void addInLineImage(MimeMessageHelper helper, ImageMapper imageMapper) {
        try {
            InputStreamSource byteArrayResource = new ByteArrayResource(IOUtils.toByteArray(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("static/images/" + imageMapper.getImageFileName()))));
            helper.addInline(imageMapper.getImageFileName(), byteArrayResource, imageMapper.getContentType());
        } catch (IOException | MessagingException e) {
            throw new MailException(e);
        }
    }
}
