package sib.api;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Program {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("YOUR API KEY");

        try {
            //AllRouteExamples obj = new AllRouteExamples();
            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("example@example.com");
            sender.setName("John Doe");
            List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail("example@example.com");
            to.setName("John Doe");
            toList.add(to);
            List<SendSmtpEmailCc> ccList = new ArrayList<SendSmtpEmailCc>();
            SendSmtpEmailCc cc = new SendSmtpEmailCc();
            cc.setEmail("example1@example.com");
            cc.setName("Janice Doe");
            ccList.add(cc);
            List<SendSmtpEmailBcc> bccList = new ArrayList<SendSmtpEmailBcc>();
            SendSmtpEmailBcc bcc = new SendSmtpEmailBcc();
            bcc.setEmail("example2@example.com");
            bcc.setName("John Doe");
            bccList.add(bcc);
            SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
            replyTo.setEmail("replyto@domain.com");
            replyTo.setName("John Doe");
            SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
            attachment.setName("test.jpg");
            byte[] encode = Files.readAllBytes(Paths.get("local_filepath\\test.jpg"));
            attachment.setContent(encode);
            List<SendSmtpEmailAttachment> attachmentList = new ArrayList<SendSmtpEmailAttachment>();
            attachmentList.add(attachment);
            Properties headers = new Properties();
            headers.setProperty("Some-Custom-Name", "unique-id-1234");
            Properties params = new Properties();
            params.setProperty("parameter", "My param value");
            params.setProperty("subject", "New Subject");
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);
            sendSmtpEmail.setCc(ccList);
            sendSmtpEmail.setBcc(bccList);
            sendSmtpEmail.setHtmlContent("<html><body><h1>This is my first transactional email {{params.parameter}}</h1></body></html>");
            sendSmtpEmail.setSubject("My {{params.subject}}");
            sendSmtpEmail.setReplyTo(replyTo);
            sendSmtpEmail.setAttachment(attachmentList);
            sendSmtpEmail.setHeaders(headers);
            sendSmtpEmail.setParams(params);
            List<SendSmtpEmailTo1> toList1 = new ArrayList<SendSmtpEmailTo1>();
            SendSmtpEmailTo1 to1 = new SendSmtpEmailTo1();
            to1.setEmail("example1@example.com");
            to1.setName("John Doe");
            toList1.add(to1);
            List<SendSmtpEmailMessageVersions> messageVersions = new ArrayList<>();
            SendSmtpEmailMessageVersions versions1 = new SendSmtpEmailMessageVersions();
            SendSmtpEmailMessageVersions versions2 = new SendSmtpEmailMessageVersions();
            versions1.to(toList1);
            versions2.to(toList1);
            messageVersions.add(versions1);
            messageVersions.add(versions2);
            sendSmtpEmail.setMessageVersions(messageVersions);
            sendSmtpEmail.setTemplateId(1L);
            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("Exception occurred:- " + e.getMessage());
        }
    }
}