package com.somethingwithjava.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Slf4j
@Component
public class MailSender {
    private static final String USERNAME = "dotranghoang@gmail.com";
    private static final String PASSWORD = "sxmqoncoeegzjfpq";

    public static void sendMail(String receiver, Number pinCode, String accountRegister) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.EnableSSL.enable","true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("New Account Verification");

            String htmlMessage = "<div style=\"margin:0;padding:0\" bgcolor=\"#FFFFFF\";    text-align: -webkit-center><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"padding-bottom:20px;max-width:516px;min-width:220px\">\n" +
                    "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <td width=\"8\" style=\"width:8px\"></td>\n" +
                    "        <td>\n" +
                    "            <div style=\"border-style:solid;border-width:thin;border-color:#dadce0;border-radius:8px;padding:40px 20px\"\n" +
                    "                 align=\"center\" class=\"m_1602779843361119679mdv2rw\">\n" +
                    "                <div style=\"font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,sans-serif;border-bottom:thin solid #dadce0;color:rgba(0,0,0,0.87);line-height:32px;padding-bottom:24px;text-align:center;word-break:break-word\">\n" +
                    "                    <div style=\"font-size:24px\">Verification pin code for account</div>\n" +
                    "                    <table align=\"center\" style=\"margin-top:8px\">\n" +
                    "                        <tbody>\n" +
                    "                        <tr style=\"line-height:normal\">\n" +
                    "                            <td align=\"right\" style=\"padding-right:8px\">\n" +
                    "                            <td>\n" +
                    "                                <i style=\"font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,sans-serif;color:rgba(0,0,0,0.87);font-size:14px;line-height:20px\">"+"<b>" + accountRegister + "</b>" +"</i>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                        </tbody>\n" +
                    "                    </table>\n" +
                    "                </div>\n" +
                    "                <div style=\"font-family:Roboto-Regular,Helvetica,Arial,sans-serif;font-size:14px;color:rgba(0,0,0,0.87);line-height:20px;padding-top:20px;text-align:center\">\n" +
                    "                  <h1>" + pinCode + "</h1>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </td>\n" +
                    "        <td width=\"8\" style=\"width:8px\"></td>\n" +
                    "    </tr>\n" +
                    "    </tbody>\n" +
                    "</table></div>";
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlMessage, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            log.info("Sent email ok");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
