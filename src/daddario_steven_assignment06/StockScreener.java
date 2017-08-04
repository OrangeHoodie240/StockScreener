
package daddario_steven_assignment06;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.HashSet;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


//StockScreener will inherrit Border Pane and all the nodes we make will be added to this pane
public class StockScreener extends BorderPane{
    
    //Attributes
    private StringBuilder symbolName = new StringBuilder("");
    private  Object[] prices = new Object[5]; 
    private File file = new File("."); 
    private File[] files = file.listFiles(); 
    private Consolidator c = new Consolidator(); 
    private Font stockFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12);
    private HashSet<String> setOfSymbolsWithPrices; 
    protected Button tenDayAvgBt = new Button("See Adj Cls > Ten Day Avg");
    protected HBox buttonBar = new HBox(10);
    protected Stage avgStage = new Stage();  
    protected StackPane avgPane = new StackPane();
    protected Scene avgScene = new Scene(avgPane, 370, 350);
    protected TextArea avgArea = new TextArea(); 
    
    
    //Constructor will make the panes, nodes, and register the EventHandlers defined in the EventHandler file
    public StockScreener()throws java.io.FileNotFoundException{
        
       
        
       
        
        
        //Make controls to select the Ticker. Ticker selection and stat display
        //will be in the top two panes
        HBox topPane = new HBox(10);
        HBox midPane = new HBox(10);  
        setMargin(midPane, new Insets(10)); 
        
        
         
        //The radio buttons
        ToggleGroup tg = new ToggleGroup(); 
        RadioButton radioInput = new RadioButton("Enter Ticker");
        RadioButton radioLoad = new RadioButton("Load all Tickers"); 
        radioInput.setToggleGroup(tg);
        radioLoad.setToggleGroup(tg); 
        
        topPane.getChildren().addAll(radioInput, radioLoad);
        setTop(topPane);
        
        
        //Make the TextField for entering a ticker and the ListView for selecting
        //A ticker and add them as well
        TextField enterField = new TextField();
        enterField.setPrefColumnCount(7);
        
        
        ObservableList<String> list = FXCollections.observableArrayList(c.getArrayListOfSymbols()); 
        ListView<String> tickerView = new ListView(list); 
        tickerView.setMaxWidth(100);
        tickerView.setMaxHeight(80);
        
    
       
        
     //Make controls to display the Ticker information. ComboBox for selecting
     //Date. And a CheckBoxes, which will get their own pane, for selecting 
     //information as well as a TextArea for displaying it. 
     //These will be added to the midPane
     ComboBox<String> datesCombo = new ComboBox<>(); 
     Label dateLabel = new Label("Date",datesCombo); 
     dateLabel.setContentDisplay(ContentDisplay.RIGHT); 
     midPane.getChildren().add(dateLabel); 
     
     TextArea infoPanel = new TextArea();
     infoPanel.setPrefRowCount(5);
     infoPanel.setPrefColumnCount(17); 
     infoPanel.setEditable(false); 
     
     VBox checkBoxPane = new VBox(10); 
     CheckBox lowChk = new CheckBox("Low"); 
     CheckBox highChk = new CheckBox("High"); 
     CheckBox closedChk = new CheckBox("Adj-Closed"); 
     checkBoxPane.getChildren().addAll(lowChk, highChk, closedChk);
     
     
     //Make a button to for showing the profile 
     Button profileButton = new Button("See Profile"); 
     
     
     //Make the pane and stage to show the profile
     StackPane profilePane = new StackPane(); 
     TextArea profileDisplay = new TextArea(); 
     profileDisplay.setEditable(false);
     profilePane.getChildren().add(profileDisplay);
     Stage profileStage = new Stage(); 
     profileStage.setScene(new Scene(profilePane, 600, 150));
     profileStage.setResizable(false);
     
     
     //Make Chart Button. Will update the chart Series as well as display
    //the chart which will be in a seperate Stage
     Button chartButton = new Button("Chart"); 
     setMargin(chartButton, new Insets(30, 30, 30, 180));
     Image chartPicSrc = new Image("chartPic.png");
     ImageView chartPic = new ImageView(chartPicSrc);
     chartPic.setFitHeight(40);
     chartPic.setFitWidth(50);
     chartButton.setGraphic(chartPic); 
     chartButton.setContentDisplay(ContentDisplay.TOP);
     
     setBottom(buttonBar);
     
     //Make Chart and Slider objects and set them in a pane
     //and put that pane in its own stage. The Slider will control the 
     //Size of the chart while retaining its proportion
     final int DEFAULT_CHART_HEIGHT= 400; 
     final int DEFAULT_CHART_WIDTH = 500; 
     
     CategoryAxis xAxis = new CategoryAxis();
     NumberAxis yAxis = new NumberAxis(); 
     
     XYChart.Series series = new XYChart.Series();
     series.setName("Adj Closed Price");
     Stage chartStage = new Stage(); 
     LineChart<String, Number> chartNode = new LineChart<>(xAxis, yAxis); 
     chartNode.getData().add(series);
     
     
     Slider slider = new Slider();
     
      
     BorderPane chartPane = new BorderPane(); 
     chartPane.setCenter(chartNode);  
     chartPane.setBottom(slider); 
     
     Scene chartScene = new Scene(chartPane);
     chartStage.setScene(chartScene);  
     chartStage.setResizable(false); 
     
     avgArea.setEditable(false);
     avgPane.getChildren().add(avgArea);
     avgStage.setScene(avgScene);
     avgStage.setResizable(false);
     
     
     //Make a file not found node
     Rectangle rec1 = new Rectangle(200, 50); 
     rec1.setFill(Color.GRAY);
     
     //Using a Rectangle, Lable, and StackPane to make a sign that will 
     //let the user know there is no price information available for the Ticker
     //They selected or entered
     Label lblNotFound = new Label("No Price information Found");
     lblNotFound.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
     lblNotFound.setStyle("-fx-color: blue");
     
     StackPane fileNotFound = new StackPane(); 
     fileNotFound.getChildren().addAll(rec1, lblNotFound);
     setMargin(fileNotFound, new Insets(100, 100, 100, 110));
     
     
     //Setting margins for buttonBar and tenDayAvgBt which were added 6/2/17
     BorderPane.setMargin(buttonBar, new Insets(100, 0, 0, 150));
     HBox.setMargin(tenDayAvgBt, new Insets(45, 0, 0, 0));
     
     //Set Fonts
     radioInput.setFont(stockFont);
     radioLoad.setFont(stockFont); 
     dateLabel.setFont(stockFont); 
     chartButton.setFont(stockFont); 
     lowChk.setFont(stockFont); 
     highChk.setFont(stockFont); 
     closedChk.setFont(stockFont); 
     tenDayAvgBt.setFont(stockFont);
    
     
     //Set styles
     radioInput.setStyle("-fx-text-fill: blue;"); 
     radioLoad.setStyle("-fx-text-fill: blue;"); 
     dateLabel.setStyle("-fx-text-fill: blue;"); 
     chartButton.setStyle("-fx-text-fill: blue;"); 
     lowChk.setStyle("-fx-text-fill: blue;"); 
     highChk.setStyle("-fx-text-fill: blue;");  
     closedChk.setStyle("-fx-text-fill: blue;"); 
     tenDayAvgBt.setStyle("-fx-text-fill: blue;");
     
     
     
     
    //Register the handlers/listeners
    radioInput.setOnAction(new RadioInputHandler(topPane, enterField, tickerView, profileButton, this, fileNotFound));
    
    radioLoad.setOnAction(new RadioLoadHandler(topPane, enterField, tickerView, profileButton, fileNotFound, lblNotFound, this, c, files)); 
     
    enterField.setOnAction(new EnterFieldHandler(lowChk, highChk, closedChk, infoPanel, datesCombo, chartStage, this, fileNotFound, symbolName, files, enterField, c, midPane, chartButton, profileButton, tickerView, topPane, lblNotFound, profileStage, profileDisplay, buttonBar, tenDayAvgBt));
    
    tickerView.getSelectionModel().selectedItemProperty().addListener(new TickerViewListener(profileButton, topPane, lowChk, highChk, closedChk, datesCombo, infoPanel, chartButton, this, midPane, chartStage, fileNotFound, files, symbolName, tickerView, c, lblNotFound, profileStage, profileDisplay, buttonBar, tenDayAvgBt)); 
    
    datesCombo.setOnAction(new DatesComboHandler(lowChk, highChk, closedChk, infoPanel, chartStage, midPane, checkBoxPane, symbolName, datesCombo, prices, c));
   
    lowChk.setOnAction(new CheckBoxHandler(lowChk, highChk, closedChk, prices, infoPanel)); 
    
    highChk.setOnAction(new CheckBoxHandler(lowChk, highChk, closedChk, prices, infoPanel)); 
    
    closedChk.setOnAction(new CheckBoxHandler(lowChk, highChk, closedChk, prices, infoPanel)); 
    
    chartButton.setOnAction(new ChartButtonHandler(chartNode, series, DEFAULT_CHART_HEIGHT, DEFAULT_CHART_WIDTH, symbolName, chartStage, c, slider));
   
    slider.valueProperty().addListener(new SliderListener(chartNode, DEFAULT_CHART_HEIGHT, DEFAULT_CHART_WIDTH, slider, chartStage));
    
    profileButton.setOnAction(new ProfileButtonHandler(profileStage, profileDisplay, symbolName, c));
    
    tenDayAvgBt.setOnAction(new TenDayAvgHandler(this, c, symbolName));
       
    }
    
        

}


