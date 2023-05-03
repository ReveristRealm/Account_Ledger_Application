

import java.io.*;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Records {
    public static ArrayList<Transactions> records = new ArrayList<>();
    public static Scanner userInput = new Scanner(System.in);
    public static void main(String[] args) {
         boolean done = false;

        loadTransactions();
        while(true){
        while (!done) {
            System.out.println("---------------------------");
            System.out.println("Home Screen: ");
            System.out.println("D - Add Deposit");
            System.out.println("P - Make Payment(Debit)");
            System.out.println("L - Ledger");
            System.out.println("X - Exit");

            String answer = userInput.next();

            switch (answer) {
                case "D":
                    System.out.println("---------------------------");
                    addDeposit();
                    System.out.println("---------------------------");
                    /*for (int i = 0; i < records.size(); i++){
                        System.out.println(records.get(i));
                    }*/
                    break;
                case "P":
                    System.out.println("---------------------------");
                    makePayment();
                    break;
                case "L":
                    System.out.println("---------------------------");
                    System.out.println("A - Display all entries");
                    System.out.println("D - Display Deposits");
                    System.out.println("P - Display Payments");
                    System.out.println("R - Reports");
                    System.out.println("H - Home");
                    String answer1 = userInput.next();
                    switch(answer1){
                        case "A":
                        displayAllEntries();
                        continue;
                        case "D":
                        displayPosEntries();
                        continue;
                        case "P":
                        displayPayments();
                        continue;
                        case "R":
                        reports();
                        continue;
                        case "H":
                        break;
                        default:
                            System.out.println("Wrong input, Please try again");
                            break;
                    }
                case "X":
                    return;
                default:
                    System.out.println("Wrong input, please try again");
                    break;
            }

            }
        }
    }
    public static void loadTransactions(){
        double price;
        String description;
        String vendor;
        String info;

        try {
            Scanner reader = new Scanner(new File("file.txt"));
            while(reader.hasNext()) {
                info = reader.nextLine();
                String[] information = info.split(Pattern.quote("|"));
                String[] date = information[0].split("-");

                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                LocalDate TD = LocalDate.of(year,month,day);

                String[] time = information[1].split(":");

                int hrs = Integer.parseInt(time[0]);
                int minutes = Integer.parseInt(time[1]);
                int seconds;
                try {
                    seconds = Integer.parseInt(time[2]);
                }catch(ArrayIndexOutOfBoundsException e){
                    seconds = 00;
                }
                LocalTime thisTime = LocalTime.of(hrs, minutes, seconds);
                description = information[2];
                vendor =information[3];
                price = Double.parseDouble(information[4]);
                Transactions newTransaction = new Transactions(TD,thisTime,description,vendor,price);
                System.out.println(newTransaction);
                records.add(newTransaction);
            }
        }catch(FileNotFoundException e){
            System.out.println("hi");
        }
    }
    public static void addDeposit(){
        try {
            String filepath = "file.txt";
            FileWriter fileWriter = new FileWriter(filepath,true);
            BufferedWriter BW = new BufferedWriter(fileWriter);

            LocalDate date = LocalDate.now();
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = now.format(formatter);
            LocalTime time = LocalTime.parse(formattedTime, formatter);
            System.out.println("Description of this payment?");
            String description = userInput.next();
            System.out.println("Who payed you?");
            String vendor = userInput.next();
            System.out.println("How much were you payed?");
            double payment = userInput.nextDouble();


            Transactions newTransaction = new Transactions(date,time,description,vendor,payment);
            BW.write(newTransaction.getDate() + "|" + newTransaction.getTime() + "|" + newTransaction.getDescription() + "|" + newTransaction.getVendor() + "|" + newTransaction.getPrice());
            BW.newLine();
            records.add(newTransaction);
            System.out.println("This transaction was added.");
            BW.close();
        }catch(java.io.IOException e){
            System.out.println("Hi");
        }
    }
    public static void makePayment(){
        try {
            String filepath = "file.txt";
            FileWriter fileWriter = new FileWriter(filepath,true);
            BufferedWriter BW = new BufferedWriter(fileWriter);

            LocalDate date = LocalDate.now();
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = now.format(formatter);
            LocalTime time = LocalTime.parse(formattedTime, formatter);
            System.out.println("Description of this payment?");
            String description = userInput.next();
            System.out.println("Who was the payment made too?");
            String vendor = userInput.next();
            System.out.println("What was the amount of the payment?");
            double payment = userInput.nextDouble();


            Transactions newTransaction = new Transactions(date,time,description,vendor,payment);
            BW.write(newTransaction.getDate() + "|" + newTransaction.getTime() + "|" + newTransaction.getDescription() + "|" + newTransaction.getVendor() + "|" + newTransaction.getPrice());
            BW.newLine();
            records.add(newTransaction);
            System.out.println("This transaction was added.");
            BW.close();
        }catch(java.io.IOException e){
            System.out.println("Hi");
        }
    }
    public static void displayAllEntries(){
        for (Transactions value: records){
            System.out.println(value);
        }
    }
    public static void displayPosEntries(){
        for(Transactions value: records ){
            if (value.getPrice() > 0){
                System.out.println(value);
            }
        }
    }
    public static void displayPayments(){
        for (Transactions value : records){
            if(value.getPrice() < 0){
                System.out.println(value);
            }
        }
    }
    public static void reports(){
        LocalDate date = LocalDate.now();
        System.out.println("---------------------------");
        System.out.println("1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Go back to home page");

        int answer2 = userInput.nextInt();
        switch(answer2){
            case 1:
                for(Transactions value: records){
                    if(value.getDate().isBefore(date.plusDays(1)) && value.getDate().isAfter(date.minusMonths(1).minusDays(1))){
                        System.out.println(value);
                    }
                }
            break;
            case 2:
                for(Transactions value:records){
                    if (value.getDate().isAfter(date.minusMonths(1).minusDays(1)) && value.getDate().isBefore(date.minusMonths(1)) ){
                        System.out.println(value);
                    }
                }
            break;
            case 3:
                for(Transactions value:records){
                    if(value.getDate().isBefore(date.plusDays(1)) && value.getDate().isAfter(date.minusYears(1))){
                        System.out.println(value);
                    }
                }
            break;
            case 4:
                for(Transactions value:records){
                    if(value.getDate().isAfter(date.minusYears(1).minusMonths(date.getMonthValue())) && value.getDate().isBefore(date.minusMonths(date.getMonthValue()-1))){
                        System.out.println(value);
                    }
                }
            break;
            case 5:
                System.out.println("What is the name of the vendor?");
                String response = userInput.next();
                for (Transactions value:records){
                    if(value.getVendor().equals(response)){
                        System.out.println(value);
                    }
                }
            break;
            case 0:
            break;
    }
    }
}