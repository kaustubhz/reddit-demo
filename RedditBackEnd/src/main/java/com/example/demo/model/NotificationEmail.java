package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationEmail {

	private String mailSubject;
	private String mailRecipient;
	private String mailBody;
}
