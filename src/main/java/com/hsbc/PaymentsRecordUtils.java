package com.hsbc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PaymentsRecordUtils {

    private static Map<String, Integer> results = new HashMap<>();

    private static Map<String, Double> rateResults = new HashMap<>();


    /**
     * print payments data
     */
    public static void printPayments() {
        for (Map.Entry entry : results.entrySet()) {
            Object value = entry.getValue();
            if (value != null && Integer.parseInt(value.toString()) != 0) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }

    }

    /**
     * print payments data withe rate
     */
    public static void printPaymentsWithRate() {
        for (Map.Entry entry : results.entrySet()) {
            Object value = entry.getValue();
            if (value != null && Integer.parseInt(value.toString()) != 0) {
               Double rate =  rateResults.get(entry.getKey());
               if(rate != null){
                   BigDecimal mul1 = new BigDecimal(String.valueOf(value));
                   BigDecimal mul2 = new BigDecimal(rate);
                   BigDecimal rets = mul1.multiply(mul2);
                   System.out.println(entry.getKey() + " " + entry.getValue() + " (USD "+rets.setScale(4, BigDecimal.ROUND_UP)+" )");
               }else{
                   System.out.println(entry.getKey() + " " + entry.getValue());
               }

            }
        }

    }


    /**
     * load rate base USD from file
     */
    public static void loadRateFromFile(String path){
        Scanner fileScan = null;
        InputStream input = null;
        try {
            input = Class.forName(PaymentsRecordUtils.class.getName()).getResourceAsStream(path);
            fileScan = new Scanner(input);
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                String currency = line.split(" ")[0];
                Double amount = Double.valueOf(line.split(" ")[1]);
                rateResults.put(currency, amount);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileScan != null) {
                fileScan.close();
            }

        }
    }

    /**
     * load payments data from file
     */
    public static void loadPaymentsFromFile(String path) {
        Scanner fileScan = null;
        InputStream input = null;
        try {
            input = Class.forName(PaymentsRecordUtils.class.getName()).getResourceAsStream(path);
            fileScan = new Scanner(input);
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                String currency = line.split(" ")[0];
                int amount = Integer.parseInt(line.split(" ")[1]);

                PaymentsRecord pr = new PaymentsRecord(currency, amount);

                addPayment(pr);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileScan != null) {
                fileScan.close();
            }
//            if(input != null){
//                try{
//                    input.close();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
        }


    }


    public static void addPayment(PaymentsRecord pr) {

        Object amountObj = results.get(pr.getCurrency());
        if (amountObj != null) {
            Integer amount = (Integer) amountObj;
            results.put(pr.getCurrency(), amount + pr.getAmount());

        } else {

            results.put(pr.getCurrency(), pr.getAmount());
        }


    }

    public static int getAmountByCurrency(String currency){
        Integer val = results.get(currency);
        return val;
    }


}