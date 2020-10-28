package com.example.demo.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.exception.SpringRedditException;
import com.example.demo.model.NotificationEmail;

import javassist.bytecode.stackmap.BasicBlock.Catch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	private final MailContentBuilder mailContentBuilder;

	@Async
	public void sendMail(NotificationEmail notificationEmail) {

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getMailRecipient());
			messageHelper.setSubject(notificationEmail.getMailSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getMailBody()));
		};
		try {
			log.info("Inside try block");
			mailSender.send(messagePreparator);
			log.info("Activation email sent");
		} catch (MailException exception) {
			throw new SpringRedditException(
					"Exception occured while sending an email to " + notificationEmail.getMailRecipient());

		}
	}

}
