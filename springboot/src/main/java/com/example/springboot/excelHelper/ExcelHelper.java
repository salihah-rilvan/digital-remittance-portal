package com.example.springboot.excelHelper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.springboot.transaction.Transaction;
import com.example.springboot.transaction.helperFunctions.MissingFieldsException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // Check if the file submitted is of Excel
    public static boolean hasExcelFormat(MultipartFile file) {

        if (TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }

        return false;
    }

    // Parse excel file into list of Transactions
    public static List<Transaction> excelToTransaction(InputStream is, long senderId, Map<Long, String> ssots,  HashMap<Long,String> fields) {        
        // if sender hasn't mapped fields to ssots, throw errors  
        if (fields.isEmpty()){
            throw new IllegalArgumentException("You haven't mapped your fields to SSOTs of the selected country");
        
        }
        
        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            XSSFSheet sheet = workbook.getSheetAt(0);  
            int rowNum = sheet.getLastRowNum();

            // Since getPhysicalNumberOfRows only returns number of rows that are used (not include blank row)
            // check the number of rows in the Excel file
            if (rowNum > 0 || sheet.getPhysicalNumberOfRows() > 0){
                rowNum += 1;
            }

            if (rowNum == 0){
                throw new IllegalArgumentException("You have submitted an empty file");
            }

            // Excel with only one row (which assumed to be header)
            if (rowNum == 1){
                throw new IllegalArgumentException("There is no data in your file");
            }

            Row headerRow = sheet.getRow(0);

            if (headerRow == null){
                throw new IllegalArgumentException("You have submitted an empty file");
            }

            // Get last cell with value in the row 
            int cellNum = headerRow.getLastCellNum();
            List<String> headers = new ArrayList<String>();

            // Check the header in the excel file to see if they match the field mappings submitted by user
            // if there are extra headers not in field mappings return that header column's ID so that it could be ignore later
            List<Integer> ignoreCols = checkTransactionHeaders(fields, ssots, headerRow, headers);                             
            
            // Map the excel header with the ssotName according to field mapping
            List<String> ssotHeaders = mapSSOTtoHeaders(ssots, fields, headers); 
            
            // Reformat the Date (Short) format in Excel
            DataFormatter dataFormatter = new DataFormatter();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            dataFormatter.addFormat("m/d/yy", new java.text.SimpleDateFormat("dd/MM/yyyy"));

            List<Transaction> transactions = new ArrayList<Transaction>();
            // Go through each row  in the excel file
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++){
                Row r = sheet.getRow(i);
                List<String> record = new ArrayList<>();
                int countBlank = 0;
                // Check if the row is null
                if (r != null){
                    for (int j = 0; j < cellNum; j++){
                        Cell c = r.getCell(j);
                        // Check if the cell is null, if yes add an empty string
                        if (c == null || c.getCellType() == CellType.BLANK){
                            record.add("");
                            countBlank++;
                        }else{
                            // convert the value of cell to string and add to the list of record
                            record.add(getStringCellOnTransaction(c, dataFormatter, evaluator).trim());
                        }
                    }
                }
                // if countBlank >= cell Num -> the row is empty -> ignore this record
                // otherwise, convert the record into a HashMap with the key is the ssot name and the value is the value of cell
                // the ignore columns will be ignored in the process.
                if (countBlank < cellNum){
                    Map<String, String> result = IntStream.range(0,headers.size())
                                                          .filter(q -> !ignoreCols.contains(q))
                                                          .boxed()
                                                          .collect(Collectors.toMap(q -> ssotHeaders.get(q), q -> record.get(q)));

                    System.out.println(result);

                    // convert the hashmap into a json string
                    String json = new ObjectMapper().writeValueAsString(result);
                    long receiverId = 0;

                    // Create a Transaction object with default status and receiver Id (no receiver with id 0)
                    // the json string is an attribute in Transaction that store all values in the record
                    Transaction transaction = new Transaction(senderId, receiverId, LocalDate.now(), "pending", json);
                    transactions.add(transaction);
                }

            }

            
            if (transactions.size() == 0){
                throw new RuntimeException("Fail to parse Excel file!");
            }

            return transactions;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }

    // Parse excel file into list of field names
    public static List<String> excelToHeader(InputStream is, Map<Long, String> ssots) {        
        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            XSSFSheet sheet = workbook.getSheetAt(0);  
            int rowNum = sheet.getLastRowNum();
            DataFormatter dataFormatter = new DataFormatter();

            if (rowNum > 0 || sheet.getPhysicalNumberOfRows() > 0){
                rowNum += 1;
            }

            // Check if there is one row in the Excel
            if (rowNum == 0){
                throw new IllegalArgumentException("You have submitted an empty file");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null){
                throw new IllegalArgumentException("You have submitted an empty file");
            }

            // Go through each cell to get the value
            int cellNum = headerRow.getLastCellNum();
            List<String> headers = new ArrayList<String>();

            for (int i = 0; i < cellNum; i++){
                Cell c = headerRow.getCell(i);
                // only get cell with value
                if (c != null && c.getCellType() != CellType.BLANK){
                    String val = getStringCellOnHeader(c, dataFormatter).trim();

                    // throw error if there are duplicate field
                    if (headers.contains(val)){
                        throw new IllegalArgumentException("There are duplicate headers in your file");
                    }
                    headers.add(val);
                }
            }

            // Check if the number of field names is smaller than number of ssots
            if (ssots.size() > headers.size()){
                throw new IllegalArgumentException("The number of fields is smaller than the number of  SSOTs for the country");
            }
            
            return headers;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }


    private static ArrayList<String> mapSSOTtoHeaders(Map<Long, String> ssots,  HashMap<Long,String> fields, List<String> headers){      
        Map<String, Long> swapped = fields.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        ArrayList<String> ssotHeaders = new ArrayList<>();

        // Go through each field name and get the ssotName corresponding with the field name. If the field name cannot 
        // be found in the HashMap, insert a null String
        for (var header: headers){
            Long ssotId = swapped.get(header);
            String ssotHeader = "";
            if (ssotId != null){
                ssotHeader = ssots.get(ssotId);
            }

            ssotHeaders.add(ssotHeader);
        }
        return ssotHeaders;
    }


    private static ArrayList<Integer> checkTransactionHeaders(HashMap<Long,String> fields, Map<Long, String> ssots, Row headerRow, List<String> headers){      
        ArrayList<Integer> ignoreCols = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        
        // Check the header for empty cell
        if (headerRow.getLastCellNum() > headerRow.getPhysicalNumberOfCells()){
            throw new MissingFieldsException("There are cells with empty values in the header");
        }

        // Go through each cell in header row 
        for (int i = 0; i < headerRow.getLastCellNum(); i++){
            Cell c = headerRow.getCell(i);
            // if the cell is null or empty insert empty string
            if (c == null || c.getCellType() == CellType.BLANK){
                headers.add("");
            }else{
                // Convert the cell value to string and add to list of headers
                String val = getStringCellOnHeader(c, dataFormatter).trim();
                // throw error if there are duplicate headers
                if (headers.contains(val)){
                    throw new IllegalArgumentException("There are duplicate headers in your file");
                }
                headers.add(val);
            }
        }

        // if number of headers is smaller than the number of field mappings -> throwing error
        if (fields.size() > headers.size()){
            throw new MissingFieldsException("There are missing fields in the file as compared to the submitted fields");
        }

        // (Double check) if number of headers is smaller than the number of SSOTs -> throwing error
        if (ssots.size() > headers.size()){
            throw new MissingFieldsException("There are missing fields in the file as compared to the submitted fields");
        }

        // Check against the field mapping to see which column to be ignored when converting the transaction record
        // to json string
        for (int i = 0; i < headers.size(); i++){
            String header = headers.get(i);
            if (!fields.containsValue(header)){
                ignoreCols.add(i);
            }
        }

        // Check the number of actual header (that appears in field mappings) against number of field mappings
        if (headers.size() - ignoreCols.size() < fields.size()){
            throw new MissingFieldsException("There are missing fields in the file as compared to the submitted fields");
        }
        
        return ignoreCols;
    }

    private static String getStringCellOnTransaction(Cell c, DataFormatter dataFormatter, FormulaEvaluator evaluator){      
        // Convert cell value to string
        String s = dataFormatter.formatCellValue(c, evaluator);
        return s;
    }

    private static String getStringCellOnHeader(Cell c, DataFormatter dataFormatter){    
        // Convert cell value to string
        String s = dataFormatter.formatCellValue(c);
        return s;
    }

}