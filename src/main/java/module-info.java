module com.example.FoodCenterSys {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.FoodCenterSys to javafx.fxml;
    exports com.example.FoodCenterSys;
}