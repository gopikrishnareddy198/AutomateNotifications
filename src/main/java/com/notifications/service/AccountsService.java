package com.notifications.service;

import com.notifications.model.Account;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AccountsService {

    private final static String PATH = "src/main/resources";
    private static final int MAX = 1000;
    private static final int MIN = -100;

    public static void main(String[] args) throws IOException {
        new AccountsService().readXlsxFile();
        //new AccountsService().writeXlsxFile();
    }

    public   List<Account> readXlsxFile() throws IOException {
        int i=1;
        List<Account> accountList;
        Iterator<Row> rowIterator;
        Account account;
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream("modified_accounts.xlsx");
        //creating workbook instance that refers to .xlsx file
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        FormulaEvaluator formulaEvaluator = xssfWorkbook.getCreationHelper().createFormulaEvaluator();
        rowIterator=sheet.iterator();
        accountList=new ArrayList<>();

        while (rowIterator.hasNext()){
            Row currentRow=rowIterator.next();
            if(currentRow.getRowNum()!=0) {
                account = new Account();
                account.setSerialNumber(i++);
                account.setAccountHolderName(currentRow.getCell(0).toString());
                account.setAddressLine1(currentRow.getCell(1).toString());
                account.setCity(currentRow.getCell(2).toString());
                account.setZipcode((int) Double.parseDouble(currentRow.getCell(3).toString()));
                account.setCounty(currentRow.getCell(4).toString());
                account.setAccountNumber(currentRow.getCell(5).toString());
                account.setAccountBalance((int)Double.parseDouble(currentRow.getCell(6).toString()));
                account.setEmailId(currentRow.getCell(7).toString());
                accountList.add(account);
            }
        }
           // System.out.println(accountList);
            return accountList;
    }

    public void writeXlsxFile() throws IOException {
        Random random;
        String absolutePath;
        int i = 10000000;
        Iterator<Row> rowIterator;
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream("accounts.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = xssfWorkbook.getSheetAt(0);

        rowIterator = mySheet.iterator();
        random = new Random();

        while (rowIterator.hasNext()) {
            Cell cell1, cell2, cell3;
            Row currentRow = rowIterator.next();
            cell1 = currentRow.createCell(currentRow.getLastCellNum());
            cell2 = currentRow.createCell(currentRow.getLastCellNum() + 1);
            cell3 = currentRow.createCell(currentRow.getLastCellNum());
            if (currentRow.getRowNum() == 0) {
                cell1.setCellValue("Acc Number");
                cell2.setCellValue("Balance");
                cell3.setCellValue("Email");
            } else {
                cell1.setCellValue(
                        currentRow.getCell(0).toString().substring(0, 2).toUpperCase() + "56012" + i++

                );
                cell2.setCellValue(random.nextInt(MAX - MIN) + MIN);
                cell3.setCellValue("gopikrishnareddy201@gmail.com");
            }
        }

        absolutePath = new File(PATH).getAbsolutePath();
        FileOutputStream os = new FileOutputStream(new File(absolutePath + "\\modified_accounts.xlsx"));
        xssfWorkbook.write(os);
        System.out.println("Writing on XLSX file Finished ...");
    }

}


