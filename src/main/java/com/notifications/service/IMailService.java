package com.notifications.service;

import com.notifications.model.Account;

import javax.mail.MessagingException;

public interface IMailService {

        public boolean sendEmail(Account account) throws MessagingException;
}
