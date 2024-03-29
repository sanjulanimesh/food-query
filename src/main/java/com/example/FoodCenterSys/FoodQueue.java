package com.example.FoodCenterSys;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FoodQueue {
    Customer customer;
    private int noOfBurger;
    private final String items[][];
    private int column;
    private boolean full;
    private int position;

    //create constructor
    public FoodQueue(String items[][], int column) {
        this.items = items;
        this.column = column;
    }

    //insert to queue
    void addCustomer (String name, int noOfBurger, int colAwaiting, int posAwaiting) {

        int Index;
        if (name.equals("")) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the First Name :");
            String firstName = input.nextLine();
            System.out.print("Enter the Second Name :");
            String secondName = input.nextLine();
            System.out.print("How many No. burgers do you required :");
            this.noOfBurger = input.nextInt();

            //Combine two names as FULL NAME
            String name2 = firstName + " " + secondName;
            //creating customer class
            customer = new Customer(name2);
        } else {
            this.noOfBurger = noOfBurger;
            //creating customer class
            customer = new Customer(name);
        }

        //add awaiting customers to queue
        if (!(colAwaiting == -1)) {
            items[colAwaiting - 1][posAwaiting] = "O";
            this.column = colAwaiting;
            this.position = posAwaiting;
        }
        //is queue full?
        else if (items[column - 1][items[column - 1].length - 1].equals("O")) {
            System.out.println("\nQueue is full!!");
            this.full = true;
        } else if (Arrays.asList(items[column - 1]).contains("O")) {
            //if already a slot has occupied
            //find the index of lastly existed value of ""O
            Index = IntStream.range(0, items[column - 1].length).reduce((i, j) -> items[column - 1][j].equals("O") ? j : i).orElse(-1);
            items[column - 1][Index + 1] = "O";//mark as occupied
            this.position = Index;
            System.out.println("\nSuccessfully occupied the place in " + column + " queue");
        } else {//if the occupying slot is the first slot in queue
            Index = 0;
            this.position = Index;
            items[column - 1][Index] = "O";
            System.out.println("\nSuccessfully occupied the place in " + column + " queue");
        }


    }

    //remove from queue
    void deQueue() {

        if (Arrays.asList(items[column - 1]).contains("O")) {//If already occupied a slot in queue
            //find the index of firstly existed position of value "O"
            int Index = IntStream.range(0, items[column - 1].length).filter(i -> items[column - 1][i].equals("O")).findFirst().orElse(-1);
            items[column - 1][Index] = "X";//make the occupied slot as unoccupied
            System.out.println("\nSuccessfully made available the place in " + column + " queue");
        } else System.out.println("\nQueue's all slots are available!");//is queue empty?

    }
//------------
    String[][] getItems() {
        return this.items;
    }

    int getColumn() {
        return this.column;
    }

    int getPosition() {
        return this.position;
    }

    int getNoOfBurger() {
        return this.noOfBurger;
    }

    boolean getFull() {
        return this.full;
    }
}
