//[ID] 20220730_W1953533
//[name] Sanjula_Nimesh
//package
package com.example.FoodCenterSys;

//java - import part
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//javafx - imports part
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    private static String[][] arr2;
    //array that use in javafx

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        //creating an arraylist to save details
        ArrayList FQ = new ArrayList<FoodQueue>();

        //creating the queues' array
        String[][] arr = {{"X", "X"}, {"X", "X", "X"}, {"X", "X", "X", "X", "X"}};
        Main.arr2 = arr;
        //copy the array to use in javafx

        //burger stock
        ArrayList burgStockArr = new ArrayList();
        burgStockArr.add(50);

        //burger stock income
        ArrayList<Integer> firstQIncome = new ArrayList<>();
        ArrayList<Integer> secondQIncome = new ArrayList<>();
        ArrayList<Integer> thirdQIncome = new ArrayList<>();

        //creating an awaiting customers list
        ArrayList<FoodQueue> awaiting = new ArrayList<>();

        //menu option array
        final String[] optionList = {
                "100", "101", "102", "103", "104", "105", "106", "107", "108", "109",
                "110", "112", "999", "GUI", "VFQ", "ACQ", "RCQ", "PCQ", "VCS", "SPD",
                "LPD", "STK", "AFS", "IFQ", "EXT"};

        //JavaFX part
        //copy arraylist of details on queue and waiting queue
        Controller.FQ = FQ;
        Controller.awaiting = awaiting;

        while (true) {
            try {
                System.out.println("""
                        **********************************************************************************************
                        ***************               Welcome to Foodies Fave Food Center              ***************
                        **********************************************************************************************
                                        
                        ---Menu Option---
                                          
                        100 or VFQ:  View all Queues.
                        101 or VEQ:  View all Empty Queues.
                        102 or ACQ:  Add customer to a Queue.
                        103 or RCQ:  Remove a customer from a Queue.
                        104 or PCQ:  Remove a served customer.
                        105 or VCS:  View Customers Sorted in alphabetical order.
                        106 or SPD:  Store Program Data into file.
                        107 or LPD:  Load Program Data from file.
                        108 or STK:  View Remaining burgers Stock.
                        109 or AFS:  Add burgers to Stock.
                        110 or IFQ:  Income of queues.
                        112 or GUI:  Open the GUI Application.
                        999 or EXT:  Exit the Program.
                        """);

                System.out.print("Please Enter the Option :");
                String option = input.nextLine();
                option = option.toUpperCase();
                System.out.println(" ");

                //check the option validity
                if (!(Arrays.asList(optionList).contains(option))) System.out.println("Invalid option selected!");

                switch (option) {
                    case "100", "VFQ"
                            -> viewAllQueue(arr);
                    case "101", "VEQ"
                            -> viewEmptyQueues(arr);
                    case "102", "ACQ"
                            -> addCustomer(arr, FQ, burgStockArr, awaiting);
                    case "103", "RCQ"
                            -> removeCustomer(arr, FQ);
                    case "104", "PCQ"
                            -> deQueue(arr, FQ, burgStockArr, firstQIncome, secondQIncome, thirdQIncome, awaiting);
                    case "105", "VCS"
                            -> sortByName(FQ);
                    case "106", "SPD"
                            -> createFile(arr, FQ);
                    case "107", "LPD"
                            -> loadFile(arr, FQ);
                    case "108", "STK"
                            -> viewRemainingBurgers((int) burgStockArr.get(burgStockArr.size() - 1));
                    case "109", "AFS"
                            -> addBurgersToStock(burgStockArr);
                    case "110", "IFQ"
                            -> incomePrice(firstQIncome, secondQIncome, thirdQIncome);
                    case "999", "EXT"
                            -> exit();
                    case "112", "GUI"
                            -> Application.launch(Main.class, args);
                }
            } catch (Throwable t) {
                System.out.println("Error Occurred, Try Again!!!");
            }
        }
    }
//--------------------------------------------------------------------------
    //javafx main
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cashierMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Foodies Fave Food Center System");
        stage.setScene(scene);
        stage.show();
        Controller controller = fxmlLoader.getController();
        controller.arr = arr2;
        controller.setQue();
    }
//--------------------------------------------------------------------------

    public static void viewAllQueue(String arr[][]) {

        //view all queues
        System.out.println("""
                                
                ******************
                *    Cashiers    *
                ******************
                """);

        //aligning the queues
        for (int i = 0; i < 6; i++) {
            String arr0Elements = "";
            String arr1Elements = "";
            String arr2Elements = "";
            for (int j = 0; j < 3; j++) {
                if (j == 0) arr0Elements = i < arr[j].length ? arr[j][i] + "    " : "     ";
                if (j == 1) arr1Elements = i < arr[j].length ? arr[j][i] + "    " : "     ";
                if (j == 2) arr2Elements = i < arr[j].length ? arr[j][i] + "    " : "     ";
            }
            System.out.println("   " + arr0Elements + arr1Elements + arr2Elements);
        }
    }

    public static void addCustomer(String arr[][], ArrayList FQ, ArrayList burSto, ArrayList<FoodQueue> awaiting) {

        if (burSto.get(burSto.size() - 1).equals(10)) {//display warning message
            System.out.println("  !!!Warning Message  !!!\nBurger stocks are low. Please restock.");
        }


        int column;
        //select column with minimum length
        if (!arr[0][1].equals("O")) {
            column = 1;
        } else if (!arr[1][2].equals("O")) {
            column = 2;
        } else {
            column = 3;
        }
        //create object on queue
        FoodQueue que = new FoodQueue(arr, column);
        que.addCustomer("", 0, -1, -1);

        //call back the updated list of queue
        String items[][] = que.getItems();
        //set the original queue array as updated
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = items[i][j];
            }
        }

        if (que.getFull()) {
            awaiting.add(que);
            System.out.println("Customer is kept at awaiting queue");
        } else {
            //add details to arraylist
            FQ.add(que);
            System.out.println("Customer and queue details are saved.");
        }
    }

    public static void deQueue(String[][] arr, ArrayList<FoodQueue> FQ, ArrayList burSto, ArrayList<Integer> firstQIncome, ArrayList<Integer> secondQIncome, ArrayList<Integer> thirdQIncome, ArrayList<FoodQueue> awaiting) {
        int col = 0, pos = 0;
        Scanner input = new Scanner(System.in);
        System.out.print("""
                                 
                ---------------------------------
                      Selecting the queue
                ---------------------------------
                                 
                1) 2 people slotted queue
                2) 3 people slotted queue
                3) 5 people slotted queue
                                 
                Select a Slot to remove :""");
        int column = input.nextInt();

        FoodQueue que = new FoodQueue(arr, column);
        que.deQueue();
        que.getItems();
        String items[][] = que.getItems();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = items[i][j];
            }
        }

        //remove details from arraylist
        for (int i = 0; i < FQ.size(); i++) {
            if (FQ.get(i).getColumn() == que.getColumn() && FQ.get(i).getPosition() == que.getPosition()) {
                System.out.println("Deleted the records of Customer " + FQ.get(i).customer.getName());

                //remove burgers from stock for person
                burSto.add((int) burSto.get(burSto.size() - 1) - FQ.get(i).getNoOfBurger());

                //updating the arraylist where store the amount of burgers
                //code snippet for calculating income
                if (FQ.get(i).getColumn() - 1 == 0) {
                    firstQIncome.add(FQ.get(i).getNoOfBurger() * 650);
                } else if (FQ.get(i).getColumn() - 1 == 1) {
                    secondQIncome.add(FQ.get(i).getNoOfBurger() * 650);
                } else thirdQIncome.add(FQ.get(i).getNoOfBurger() * 650);
                col = FQ.get(i).getColumn();
                pos = FQ.get(i).getPosition();
                FQ.remove(i);
                break;
            }
        }

        //add an awaiting customer to queue
        if (!awaiting.isEmpty()) {
            FoodQueue fq = awaiting.get(0);
            FoodQueue fq2 = new FoodQueue(arr, fq.getColumn());
            fq2.addCustomer(fq.customer.getName(), fq.getNoOfBurger(), col, pos);
            FQ.add(fq2);
            System.out.println("Successfully " + fq.customer.getName() + " added to the " + (fq2.getColumn() + 1) + "queue");
            awaiting.remove(0);
        }
    }


    //view all empty queues
    public static void viewEmptyQueues(String[][] arr) {

        System.out.println("\n-----------");
        System.out.println("EMPTY QUEUES \n-----------");

        for (int i = 0; i < 3; i++) {
            System.out.print(i + 1 + " Queue :");
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].equals("X")) {
                    System.out.print(j + 1 + ",");
                    ;
                }
            }
            System.out.println();
        }
    }
    //create a file that keep track of records
    public static void createFile(String[][] arr, ArrayList<FoodQueue> fq) {
        try {
            FileWriter fw = new FileWriter("program.txt");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    fw.write(arr[i][j]);
                }
                fw.write("\n");
            }
            fw.close();
            FileWriter fw2 = new FileWriter("program2.txt");
            for (FoodQueue foodQ : fq) {
                fw2.write(Integer.toString(foodQ.getPosition()));
                fw2.write("\n");
                fw2.write(Integer.toString(foodQ.getColumn()));
                fw2.write("\n");
                fw2.write(foodQ.customer.getName());
                fw2.write("\n");
                fw2.write(Integer.toString(foodQ.getNoOfBurger()));
                fw2.write("\n");
            }
            fw2.close();
            System.out.println("Successfully Data Saved to files!");
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
    //file load saved file to program
    public static void loadFile(String[][] arr, ArrayList<FoodQueue> arr2) {
        try {

            File file2 = new File("program2.txt");
            Scanner fileReader2 = new Scanner(file2);
            ArrayList<String> arr3 = new ArrayList();
            while (fileReader2.hasNextLine()) {

                String line = fileReader2.nextLine();
                arr3.add(line);

                if (arr3.size() == 4) {
                    FoodQueue fq = new FoodQueue(arr, Integer.parseInt(arr3.get(1)));
                    fq.addCustomer(arr3.get(2), Integer.parseInt(arr3.get(3)), -1, -1);
                    arr2.add(fq);
                    arr3.clear();
                }
            }
            System.out.println("Successfully loaded the data into ");
        } catch (FileNotFoundException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
    //sorting in alphabetical order part
    public static void sortByName(ArrayList<FoodQueue> arr) {

        System.out.println("SORTED IN ALPHABETICAL ORDER\n");

        //used bubble sort
        ArrayList<FoodQueue> list = new ArrayList<FoodQueue>();
        FoodQueue temp;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size() - 1; j++) {
                char first = arr.get(i).customer.getName().charAt(0);
                char sec = arr.get(j + 1).customer.getName().charAt(0);
                if (first > sec) {
                    temp = arr.get(j + 1);
                    arr.set((j + 1), arr.get(i));
                    arr.set(i, temp);
                }
            }
            list.add(arr.get(i));
        }
        //all elements print out
        for (FoodQueue fq : list) {
            System.out.println(fq.customer.getName());
        }
    }

    //view remaining burgers in stock
    public static void viewRemainingBurgers(int burSock) {
        System.out.println("Remaining Burger Stock :" + burSock);
    }

    //add burgers to stock
    public static void addBurgersToStock(ArrayList<Integer> arr) {
        Scanner input = new Scanner(System.in);
        System.out.print("How many burgers add to :");
        int amount = input.nextInt();
        int burgers = arr.get(arr.size() - 1) + amount;
        arr.add(burgers);
        System.out.println("Successfully!!! " + amount + " burgers added to the stock");
    }

    //remove customer from a specific location
    public static void removeCustomer(String arr[][], ArrayList<FoodQueue> FQ) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the queue of person in(1-3) :");
        int column = input.nextInt();
        System.out.print("Enter the queue of person in(1-" + arr[column - 1].length + ") :");
        int position = input.nextInt();

        arr[column - 1][position - 1] = "X";
        for (int i = 0; i < FQ.size(); i++) {
            if (FQ.get(i).getColumn() == column && FQ.get(i).getPosition() == position - 1) {
                System.out.println("Successfully removed " + FQ.get(i).customer.getName() + " in queue and records.");
                FQ.remove(i);
            }
        }
        System.out.println("Successfully removed the person from queue");
    }

    //calculate the price
    public static void incomePrice(ArrayList<Integer> firstQIncome, ArrayList<Integer> secondQIncome, ArrayList<Integer> thirdQIncome) {

        int sumQ2 = 0;
        int sumQ3 = 0;
        int sumQ1 = 0;
        int sumtotal = 0;

        for (int x : firstQIncome) sumQ1 += x;
        for (int x : secondQIncome) sumQ2 += x;
        for (int x : thirdQIncome) sumQ3 += x;
        System.out.println("INCOME OF QUESES\n");
        System.out.println("First queue Income  : Rs." + sumQ1);
        System.out.println("Second queue Income : Rs." + sumQ2);
        System.out.println("Third queue Income  : Rs." + sumQ3);
        sumtotal = sumQ1 +sumQ2 +sumQ3;
        System.out.println(" ");
        System.out.println("Total Income Price : Rs." + sumtotal);
    }
    //exit part
    public static void exit() {
        System.out.println("""
                
                                Thank you for Coming, Come Again!
                            ------------------------------------------
                """);
        System.exit(0);
    }
}

