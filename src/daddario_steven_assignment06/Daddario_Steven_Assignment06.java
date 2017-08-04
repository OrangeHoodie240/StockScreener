/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daddario_steven_assignment06;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Iterator; 
import java.util.LinkedHashSet;
import java.util.TreeMap;
import javafx.scene.control.ListView;

/**
 *
 * @author steven
 */
public class Daddario_Steven_Assignment06 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws java.io.FileNotFoundException {

        
        
        
        //Make pane and StockScreener and add the StockScreenre to the Pane
       Pane pane = new Pane();
       StockScreener stockScreener = new StockScreener(); 
       pane.getChildren().add(stockScreener);
            
        
       
       //Use pane to make the Scene and start up
        Scene scene = new Scene(pane, 300, 300);
        
        primaryStage.setTitle("Stock Chart Analysis");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(350); 
        primaryStage.setMinWidth(450); 
        primaryStage.setResizable(false); 
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws FileNotFoundException {
        launch(args);

        
    }
    
}
