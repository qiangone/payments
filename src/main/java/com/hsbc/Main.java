package com.hsbc;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {


        System.out.println("***********************WELCOME***********************");

        System.out.println("do you want to load from file? please type the 'yes' or 'no'");

        Scanner scan = new Scanner(System.in);
        String yesOrNo = scan.next();


        while (!yesOrNo.toLowerCase().equals("no") && !yesOrNo.toLowerCase().equals("yes")) {
            System.out.println("do you want to load data from a file? by typing  'yes' or 'no'");
            yesOrNo = scan.next();
        }

        if (yesOrNo.toLowerCase().equals("yes")) {
            PaymentsRecordUtils.loadPaymentsFromFile("/payments.txt");
            PaymentsRecordUtils.loadRateFromFile("/rate.txt");
            System.out.println("load data from file finished!");
        }

        System.out.println("enter more lines into the console by typing a currency and amount(eg: USD 880) and pressing enter. and enter 'end' to exit input");
//        PaymentsRecordUtils.printPayments();

        String pattern = "^[a-zA-Z]{3}\\s\\-{0,1}\\d+$";
        Pattern r = Pattern.compile(pattern);
        while (true){
            String line = scan.nextLine();
            Matcher matcher = r.matcher(line);
            if(line.equals("")) continue;
            if(line.equals("end")) break;

            String[] arr = line.split(" ");

            if(!matcher.matches() || arr.length !=2) {
                System.out.println("your input format is not correct. typing a currency and amount(eg: USD 880) and pressing enter.and enter 'end' to exit input");
                continue;
            }
            String currency = line.split(" ")[0];
            int amount = Integer.parseInt(line.split(" ")[1]);
            PaymentsRecord pr = new PaymentsRecord(currency, amount);
            PaymentsRecordUtils.addPayment(pr);
        }



        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {
                System.out.println("output a list of all the currency and amounts to the console once per minute ");
                PaymentsRecordUtils.printPaymentsWithRate();
                System.out.println("Type 'quit' anytime if you want to quit the program!");
            }
        }, 0, 60*1000);

        String quit = "";
        while (!quit.equalsIgnoreCase("quit")){
            quit = scan.nextLine();
            if (quit.equalsIgnoreCase("quit")) {
                System.exit(0);
            }
        }

    }

}