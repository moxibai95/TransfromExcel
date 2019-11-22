package sample.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SendMailUtil {

    private static JavaMailSender mailSender;

    private SendMailUtil() {
    }

    public static boolean sendTextMail(String subject, String content, String[] to, File file, String loginName, String parssword) {
        return mailSend(subject, content, to, (String[])null, (String[])null, (Map)null, file, loginName, parssword);
    }

    public static boolean mailSend(String subject, String content, String[] to, String[] cc, String[] bcc, Map<String, Object> attachs, File file, String loginName, String parssword) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        mailSender = javaMailSender;
        javaMailSender.setHost("smtp.263.net");
        javaMailSender.setPort(25);
        javaMailSender.setUsername(loginName + "@100tal.com");
        javaMailSender.setPassword(parssword);
        if (to != null && to.length != 0) {
            MimeMessage mime = mailSender.createMimeMessage();

            try {
                MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
                helper.setFrom(loginName + "@100tal.com");
                helper.setTo(to);
                if (cc != null) {
                    helper.setCc(cc);
                }

                if (bcc != null) {
                    helper.setBcc(bcc);
                }

                helper.setSubject(subject);
                helper.setText(content, true);
                helper.addAttachment(file.getName(), file);
                if (attachs != null && attachs.size() >= 0) {
                    Set<String> nameSet = attachs.keySet();
                    Iterator var9 = nameSet.iterator();

                    while(var9.hasNext()) {
                        String name = (String)var9.next();
                        Object obj = attachs.get(name);
                        FileSystemResource attachment;
                        if (obj instanceof String) {
                            attachment = new FileSystemResource((String)obj);
                            helper.addAttachment(name, attachment);
                        } else if (obj instanceof File) {
                            attachment = new FileSystemResource((File)obj);
                            helper.addInline(name, attachment);
                        }
                    }
                }

                mailSender.send(mime);
                return true;
            } catch (Exception var13) {
                return false;
            }
        } else {
            return false;
        }
    }

}
