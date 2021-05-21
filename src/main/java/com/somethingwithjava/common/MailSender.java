package com.somethingwithjava.common;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Slf4j
public class MailSender {
    private static final String USERNAME = "rickdo3258@gmail.com";
    private static final String PASSWORD = "61cW7XR0Tq7hVhr+l3dgLg==";

    private void sendMail(String receiver, Number pinCode) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", 465);
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("New Account Email Verification");

            String htmlMessage = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <title></title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                    "<script src=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js\"></script>\n" +
                    "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>\n" +
                    "<!------ Include the above in your HEAD tag ---------->\n" +
                    "\n" +
                    "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css\">\n" +
                    "<div class=\"form-gap\" style=\"padding-top: 70px;\"></div>\n" +
                    "<div class=\"container\">\n" +
                    "  <div class=\"row\">\n" +
                    "    <div class=\"col-md-4 col-md-offset-4\">\n" +
                    "      <div class=\"panel panel-default\">\n" +
                    "        <div class=\"panel-body\">\n" +
                    "          <div class=\"text-center\">\n" +
                    "            <h3><i class=\"fa fa-lock fa-4x\"></i></h3>\n" +
                    "            <h2 class=\"text-center\">Verification Code</h2>\n" +
                    "            <div class=\"panel-body\">\n" +
                    "              <h1><b>"+ pinCode +"</b></h1>\n" +
                    "            </div>\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlMessage, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
