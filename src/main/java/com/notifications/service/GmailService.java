package com.notifications.service;

import com.notifications.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("gmailService")
public class GmailService implements IMailService {

    @Autowired
    private AccountsService accountsService;
    @Autowired
    JavaMailSender mailSender;
    @Value(value = "spring.mail.username")
    String fromAddress;

    private List<Account> sentMailsList = new ArrayList<>();

    @Override
    public boolean sendEmail(Account account) throws MessagingException {
        boolean flag;
        String subject;
        String messageBody;
        MimeMessage mimeMessage;
        MimeMessageHelper mimeMessageHelper;

        /*
        Hey, account.getName() your account have an overdraft of account.getBal()
        */
        flag = false;
        mimeMessage = mailSender.createMimeMessage();


        subject = "Hey " + account.getAccountHolderName() + " you have overdraft of " + account.getAccountBalance() + "$ please clear the amount by end of this month";

        messageBody = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <style>\n" +
                "    .rectangle {\n" +
                "      height: 250px;\n" +
                "      width: 500px;\n" +
                "      background-color: #DFFF00;\n" +
                "    }\n" +
                "    .text{\n" +
                "        color: white;\n" +
                "        font-weight: 900;\n" +
                "        font-family: Century Gothic,CenturyGothic,AppleGothic,sans-serif; \n" +
                "        font-size: 250%;\n" +
                "\n" +
                "    }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"rectangle\"><div class=\"text\">" +
                "Hello "+account.getAccountHolderName()+",<br><br>You are overdraft by " + account.getAccountBalance() + "$ please clear by " + getLastDayOfMonthWithMonth() +" to avoid your account block"+ "</div></div>\n" +
                "\n" +
                "<br><p>Your Account details:<br> Account Holder Name: "+account.getAccountHolderName()+"<br>Account Number: "+account.getAccountNumber()+" </p></body>\n" +
                "</html>\n";


        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(fromAddress, "UCMBank");
            mimeMessageHelper.setTo(account.getEmailId());
            mimeMessageHelper.setText(messageBody, true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
            flag = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        account.setMailSentStatus(flag);
        sentMailsList.add(account);
        return flag;
    }

    public String getLastDayOfMonthWithMonth() {
        String monthInWords;
        Month month;
        LocalDate currentDate;

        currentDate = LocalDate.now();
        month = currentDate.getMonth();
        monthInWords = month.name();


        LocalDate lastDayOfMonthDate = currentDate.withDayOfMonth(
                currentDate.getMonth().length(currentDate.isLeapYear()));

        //  System.out.println("Last date of the month: " + lastDayOfMonthDate);
        return monthInWords + " " + lastDayOfMonthDate.getDayOfMonth();
    }

    //59 23 28-31 * *
    //@Scheduled(cron = "0 10 23 L * ?")
    @Scheduled(cron = "0 25 17 24 * ?")
    public List<Account> sendOverDraftAlerts() throws IOException {
        List<Account> accountList;
        List<Account> accountsWithOverDraftBalance;

        accountList = accountsService.readXlsxFile();

        accountsWithOverDraftBalance = accountList.stream().filter(account -> {
            return account.getAccountBalance() < 0;
        }).collect(Collectors.toList());

        accountsWithOverDraftBalance.forEach(account -> {
            try {
                sendEmail(account);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        return sentMailsList;

    }
}
