package com.donation.csv.exporter.csvhandler.notification;

import com.donation.csv.exporter.csvhandler.model.EmailStatus;
import com.donation.csv.exporter.csvhandler.model.Mail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HtmlEmailNotification {

    private EmailNotification emailNotification;

    private final TemplateEngine templateEngine;

    public EmailStatus send(Mail mail, String templateName, Context context) {
        String body = templateEngine.process(templateName, context);
        return emailNotification.sendHtml(mail, body);
    }
}
