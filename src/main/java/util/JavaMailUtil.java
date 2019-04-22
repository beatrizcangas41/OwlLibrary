package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class JavaMailUtil {

    private Session mailSession;
    private static String subject;
    private static String token;

    private static String pwrdGenerator() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


    public static String getToken() {
        token = pwrdGenerator();
        return token;
    }

    public static void sendMail(String recipient) throws MessagingException {
        Properties properties = new java.util.Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");

        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");

        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");

        //Set smtp (TLS) port
        properties.put("mail.smtp.port", "587");

        //Set smtp (SSL) port
        //properties.put( "mail.smtp.port", "465");

        properties.put("SMTP TLS/SSL Required", "yes");

        //Your gmail address
        String myAccountEmail = "OwlLibraryBookstore@gmail.com";

        //Your password using App Passwords
        String password = "ogonvexwmvazcwqc";

        System.out.println("Preparing to send email");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myAccountEmail, password);
                    }
                });

        Message message = prepareMessage(session, myAccountEmail, recipient);

        System.out.println("Message sent successfully");
    }


    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) throws MessagingException {

        Message message = new MimeMessage(session);

        subject = "Password Reset Token";

        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject(subject);

        //language=HTML
        String htmlCode1 = "<h3> This email contains a key for a password reset. </h3> " +
                "<br/>" +
                "<p> Here is your token: </p>" + token;
        message.setContent(htmlCode1, "text/html");

        Transport.send(message);

        return message;
    }
}