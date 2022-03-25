package com.notifications.controllers;


import com.notifications.model.Account;
import com.notifications.service.AccountsService;
import com.notifications.service.GmailService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private GmailService gmailService;

    @GetMapping("/getAccounts")
    public String getAccounts(Map<String, Object> map) {
        List<Account> accountList = null;

        try {
            accountList = accountsService.readXlsxFile();
            map.put("accountsList", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }


    @GetMapping(value = {"/send-emails"})
    public String sendEmailsToOverDraftAccounts(Map<String, Object> map) {
        List<Account> sentMailsList;
        try {
            sentMailsList = gmailService.sendOverDraftAlerts();
            map.put("mailsSentList", sentMailsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "mails-sent-list";
    }
}
