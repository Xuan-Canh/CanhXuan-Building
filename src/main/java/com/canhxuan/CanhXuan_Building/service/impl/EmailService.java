package com.canhxuan.CanhXuan_Building.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendContractEmail(String toEmail, String customerName, Long contractId, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Hợp đồng thuê phòng - Canh Xuan Building");

            String content = String.format(
                    "Kính gửi %s,\n\n" +
                            "Đính kèm là hợp đồng thuê phòng của quý khách.\n\n" +
                            "Vui lòng kiểm tra và liên hệ với chúng tôi nếu có bất kỳ thắc mắc nào.\n\n" +
                            "Trân trọng,\n" +
                            "Canh Xuan Building",
                    customerName
            );

            helper.setText(content);
            helper.addAttachment("contract_" + contractId + ".pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML content

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
