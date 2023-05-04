

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
                            continue;
                    }
                case "X":
                    return;
                default:
                    System.out.println("Wrong input, please try again");
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
            Scanner reader = new Scanner(new File("transactions.csv"));
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
            String filepath = "transactions.csv";
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
            BW.write(newTransaction.getDate() + "|" + newTransaction.getTime() + "|" + newTransaction.getDescription() + "|" + newTransaction.getVendor() + "|" + newTransaction.getAmount());
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
            String filepath = "transactions.csv";
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
            BW.write(newTransaction.getDate() + "|" + newTransaction.getTime() + "|" + newTransaction.getDescription() + "|" + newTransaction.getVendor() + "|" + newTransaction.getAmount());
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
            if (value.getAmount() > 0){
                System.out.println(value);
            }
        }
    }
    public static void displayPayments(){
        for (Transactions value : records){
            if(value.getAmount() < 0){
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
        System.out.println("6) Custom Search");
        System.out.println("0) Go back to home page");

        int answer2 = userInput.nextInt();
        switch(answer2){
            case 1:
                for(Transactions value: records){
                    if(value.getDate().getMonth() == date.getMonth() && date.getYear() == value.getDate().getYear()){
                        System.out.println(value);
                    }
                }
            break;
            case 2:
                for(Transactions value:records){
                    if ((value.getDate().getMonthValue() == date.getMonthValue()-1) && date.getYear() == value.getDate().getYear()) {
                        System.out.println(value);
                    }
                }
            break;
            case 3:
                for(Transactions value:records){
                    if(value.getDate().getYear() == date.getYear()) {
                        System.out.println(value);
                    }
                }
            break;
            case 4:
                for(Transactions value:records){
                    if(value.getDate().getYear() == (date.getYear()-1)){
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
            case 6:
                ArrayList<Transactions> newRecords = new ArrayList<>();
                LocalDate SD1=null;
                LocalDate SD2=null;
                Double amount = null;
                String des;
                String answer3;

                System.out.println("Do you have a Start and End date? Y or N");
                String answer = userInput.next();
                if(answer.equalsIgnoreCase("y")){
                    System.out.println("What is the start date(yyyy-mm-dd)?");
                    String SD = userInput.next();
                    SD1 = LocalDate.parse(SD);
                    System.out.println("What is the end date(yyyy-mm-dd)?");
                    String ED = userInput.next();
                    SD2 = LocalDate.parse(ED);
                }

                System.out.println("Do you have a description? Y or N");
                String response1 = userInput.next();
                if(response1.equalsIgnoreCase("y")){
                    System.out.println("What is the description?");
                     des = userInput.next();
                }else{
                     des = "";
                }

                System.out.println("Do you have a vendor?");
                String response2 = userInput.next();
                if (response2.equalsIgnoreCase("Y")){
                    System.out.println("What is the Vendor?");
                    answer3 = userInput.next();

                }else{
                    answer3 = "";
                }

                System.out.println("Do you have an amount to enter? Y or N");
                String response3 = userInput.next();
                if (response3.equalsIgnoreCase("y")){
                    System.out.println("What was the amount?");
                    amount = userInput.nextDouble();
                }

                for(Transactions value : records){
                    boolean addthis = true;

                    if(!((SD1 == null)&&(SD2 ==null)) && !(value.getDate().isAfter(SD1) && value.getDate().isBefore(SD2))){
                        addthis=false;
                    }
                    if(!des.isEmpty() && !(value.getDescription().equalsIgnoreCase(des))){
                        addthis=false;
                    }
                    if(!answer3.isEmpty() && !(value.getVendor().equalsIgnoreCase(answer3))){
                        addthis=false;
                    }
                    if(!(amount == null) && !(value.getAmount() == amount)){
                        addthis=false;
                    }
                    if(addthis){
                        newRecords.add(value);
                    }
                }

                for (Transactions value: newRecords){
                    System.out.println(value);
                }

            case 0:
            break;
    }
    }
}
