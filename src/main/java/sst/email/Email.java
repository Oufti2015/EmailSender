package sst.email;

import lombok.Getter;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Email {
    @Getter
    private final SendSmtpEmailSender sender = new SendSmtpEmailSender();
    @Getter
    private final List<SendSmtpEmailTo> toList = new ArrayList<>();
    @Getter
    private final List<SendSmtpEmailCc> ccList = new ArrayList<>();
    @Getter
    private final List<SendSmtpEmailBcc> bccList = new ArrayList<>();
    @Getter
    private final SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
    @Getter
    private final List<SendSmtpEmailAttachment> attachmentList = new ArrayList<>();
    @Getter
    private final Properties headers = new Properties();
    @Getter
    private final Properties params = new Properties();
    @Getter
    private String subject;
    @Getter
    private String htmlContent;

    public Email(String myApiKey) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(myApiKey);
    }

    public Email sender(String name, String email) {
        sender.setName(name);
        sender.setEmail(email);
        return this;
    }

    public Email to(String name, String email) {
        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setName(name);
        to.setEmail(email);
        toList.add(to);
        return this;
    }

    public Email cc(String name, String email) {
        SendSmtpEmailCc cc = new SendSmtpEmailCc();
        cc.setEmail(email);
        cc.setName(name);
        ccList.add(cc);
        return this;
    }

    public Email bcc(String name, String email) {
        SendSmtpEmailBcc bcc = new SendSmtpEmailBcc();
        bcc.setEmail(email);
        bcc.setName(name);
        bccList.add(bcc);
        return this;
    }

    public Email attachment(String name, String path) throws IOException {
        SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
        attachment.setName(name);
        byte[] encode = Files.readAllBytes(Paths.get(path));
        attachment.setContent(encode);
        attachmentList.add(attachment);

        return this;
    }

    public Email replyTo(String name, String email) {
        replyTo.setEmail(email);
        replyTo.setName(name);
        return this;
    }

    public Email subject(String subject) {
        this.subject = subject;
        return this;
    }

    public Email htmlContent(String content) {
        this.htmlContent = content;
        return this;
    }

    public Email headerProperty(String key, String value) {
        headers.setProperty(key, value);
        return this;
    }

    public Email parameter(String key, String value) {
        params.setProperty(key, value);
        return this;
    }

    public CreateSmtpEmail send() throws ApiException {
        SendSmtpEmail sendSmtpEmail = getSendSmtpEmail();

        TransactionalEmailsApi api = new TransactionalEmailsApi();
        CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
        return response;
    }

    SendSmtpEmail getSendSmtpEmail() {
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(toList);
        sendSmtpEmail.setCc(ccList);
        sendSmtpEmail.setBcc(bccList);
        sendSmtpEmail.setHtmlContent(htmlContent);
        sendSmtpEmail.setSubject(subject);
        sendSmtpEmail.setReplyTo(replyTo);
        sendSmtpEmail.setAttachment(attachmentList);
        sendSmtpEmail.setHeaders(headers);
        sendSmtpEmail.setParams(params);
        sendSmtpEmail.setTemplateId(1L);
        return sendSmtpEmail;
    }

    public static void main(String[] args) {
        try {
            new Email(args[0])
                    .headerProperty("Some-Custom-Name", "unique-id-1234")
                    .parameter("parameter", "My param value")
                    .parameter("subject", "New Subject")
                    .sender("Steph", "stephane.stiennon@gmail.com")
                    .to("Steph", "stephane.stiennon@gmail.com")
                    .cc("Steph", "stephane.stiennon@gmail.com")
                    .bcc("Steph", "stephane.stiennon@gmail.com")
                    .replyTo("Steph", "stephane.stiennon@gmail.com")
                    .subject("My {{params.subject}}")
                    .htmlContent("<html><body><h1>This is my first transactional email {{params.parameter}}</h1></body></html>")
                    .send();
        } catch (ApiException e) {
            throw new EmailException(e);
        }
    }
}
