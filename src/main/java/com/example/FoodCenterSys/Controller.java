package com.example.FoodCenterSys;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {


    //Initializing the variables of GUI
    ImageView [] imgVwArr =new ImageView[10];
    //queue part
    @FXML
    private ImageView q1p1 = new ImageView();
    @FXML
    private ImageView q1p2 = new ImageView();
    @FXML
    private ImageView q2p1 = new ImageView();
    @FXML
    private ImageView q2p2 = new ImageView();
    @FXML
    private ImageView q2p3 = new ImageView();
    @FXML
    private ImageView q3p1 = new ImageView();
    @FXML
    private ImageView q3p2 = new ImageView();
    @FXML
    private ImageView q3p3 = new ImageView();
    @FXML
    private ImageView q3p4 = new ImageView();
    @FXML
    private ImageView q3p5 = new ImageView();
    @FXML
    private TextField customerName;

    @FXML
    private Button search;
    static String[][] arr;

    //customer detail page variables

    @FXML
    private Label labelName;
    @FXML
    private Label labelPlacement;
    @FXML
    private Label labelBurgers;
    @FXML
    private Label labelQueue;
    @FXML
    private Label labelPosition;

    static ArrayList<FoodQueue> FQ = new ArrayList<>();
    static ArrayList<FoodQueue> awaiting = new ArrayList<>();

    public void setQue() {
        int counter=0;
        imgVwArr[0]=q1p1;
        imgVwArr[1]=q1p2;
        imgVwArr[2]=q2p1;
        imgVwArr[3]=q2p2;
        imgVwArr[4]=q2p3;
        imgVwArr[5]=q3p1;
        imgVwArr[6]=q3p2;
        imgVwArr[7]=q3p3;
        imgVwArr[8]=q3p4;
        imgVwArr[9]=q3p5;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <= arr[i].length - 1; j++) {
                if (arr[i][j].equals("O")){
                    imgVwArr[counter].setImage(new Image(getClass().getResourceAsStream("footPrint.png")));

                }
                counter++;
            }
        }
    }

    public void peopleSearch() throws IOException {
        Boolean isPersonExist = false;
        String name = customerName.getText();
        for (int i = 0; i < FQ.size(); i++) {
            if (FQ.get(i).customer.getName().equals(name)) {

                isPersonExist = true;
            }
        }
        for (int i = 0; i < awaiting.size(); i++) {
            if (awaiting.get(i).customer.getName().equals(name)) {
                isPersonExist = true;
            }
        }

        if (isPersonExist) {

            //open new window
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("customerDetails.fxml"));
            fxmlLoader.setController(this); // Set the controller class
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage2 = new Stage();
            stage2.setTitle("Customer Details Quarry");
            stage2.setScene(scene);
            stage2.show();


            for (int i = 0; i < FQ.size(); i++) {
                if (FQ.get(i).customer.getName().equals(name)) {
                    labelName.setText(FQ.get(i).customer.getName());
                    labelBurgers.setText(Integer.toString(FQ.get(i).getNoOfBurger()));
                    labelQueue.setText(Integer.toString(FQ.get(i).getColumn()));
                    labelPosition.setText(Integer.toString(FQ.get(i).getPosition()+1));
                    labelPlacement.setText("In queue");
                }
            }

            for (int i = 0; i < awaiting.size(); i++) {
                if (awaiting.get(i).customer.getName().equals(name)) {
                    labelName.setText(awaiting.get(i).customer.getName());
                    labelBurgers.setText(Integer.toString(awaiting.get(i).getNoOfBurger()));
                    labelQueue.setText(Integer.toString(awaiting.get(i).getColumn()));
                    labelPlacement.setText("In waiting");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setHeaderText(null);
            alert.setTitle("Person existence");
            alert.setContentText("Person is not found");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.show();

        }
    }
}