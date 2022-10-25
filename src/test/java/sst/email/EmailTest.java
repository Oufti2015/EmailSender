package sst.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailBcc;
import sibModel.SendSmtpEmailCc;
import sibModel.SendSmtpEmailTo;

public class EmailTest {

    public static final String EMAIL = "toto@toto.com";
    public static final String NAME = "Toto";
    public static final String EMAIL2 = "toto2@toto.com";
    public static final String NAME2 = "Toto2";
    public static final String API_KEY = "MY-API-KEY";

    @Test
    public void sender() {
        final Email email = new Email(API_KEY).sender(NAME, EMAIL);
        Assertions.assertEquals(EMAIL, email.getSender().getEmail());
        Assertions.assertEquals(NAME, email.getSender().getName());
    }

    @Test
    public void to() {
        Email email = new Email(API_KEY).to(NAME, EMAIL).to(NAME2, EMAIL2);
        Assertions.assertEquals(2, email.getToList().size());
        SendSmtpEmailTo s = email.getToList().get(0);
        Assertions.assertEquals(EMAIL, s.getEmail());
        Assertions.assertEquals(NAME, s.getName());
        s = email.getToList().get(1);
        Assertions.assertEquals(EMAIL2, s.getEmail());
        Assertions.assertEquals(NAME2, s.getName());
    }

    @Test
    public void cc() {
        final Email email = new Email(API_KEY).cc(NAME, EMAIL).cc(NAME2, EMAIL2);
        Assertions.assertEquals(2, email.getCcList().size());
        SendSmtpEmailCc s = email.getCcList().get(0);
        Assertions.assertEquals(EMAIL, s.getEmail());
        Assertions.assertEquals(NAME, s.getName());
        s = email.getCcList().get(1);
        Assertions.assertEquals(EMAIL2, s.getEmail());
        Assertions.assertEquals(NAME2, s.getName());
    }

    @Test
    public void bcc() {
        final Email email = new Email(API_KEY).bcc(NAME, EMAIL).bcc(NAME2, EMAIL2);
        Assertions.assertEquals(2, email.getBccList().size());
        SendSmtpEmailBcc s = email.getBccList().get(0);
        Assertions.assertEquals(EMAIL, s.getEmail());
        Assertions.assertEquals(NAME, s.getName());
        s = email.getBccList().get(1);
        Assertions.assertEquals(EMAIL2, s.getEmail());
        Assertions.assertEquals(NAME2, s.getName());
    }

    @Test
    public void replyTo() {
        final Email email = new Email(API_KEY).replyTo(NAME, EMAIL);
        Assertions.assertEquals(EMAIL, email.getReplyTo().getEmail());
        Assertions.assertEquals(NAME, email.getReplyTo().getName());
    }

    @Test
    public void smtpEmail() {
        final Email email = new Email(API_KEY)
                .sender(NAME, EMAIL)
                .to(NAME, EMAIL)
                .to(NAME2, EMAIL2)
                .cc(NAME, EMAIL)
                .cc(NAME2, EMAIL2)
                .bcc(NAME, EMAIL)
                .bcc(NAME2, EMAIL2)
                .replyTo(NAME, EMAIL);

        final SendSmtpEmail sendSmtpEmail = email.getSendSmtpEmail();
        Assertions.assertEquals(EMAIL, sendSmtpEmail.getSender().getEmail());
        Assertions.assertEquals(NAME, sendSmtpEmail.getSender().getName());
        Assertions.assertEquals(2, sendSmtpEmail.getTo().size());
        Assertions.assertEquals(2, sendSmtpEmail.getCc().size());
        Assertions.assertEquals(2, sendSmtpEmail.getBcc().size());
        Assertions.assertEquals(EMAIL, sendSmtpEmail.getReplyTo().getEmail());
        Assertions.assertEquals(NAME, sendSmtpEmail.getReplyTo().getName());
    }
}
