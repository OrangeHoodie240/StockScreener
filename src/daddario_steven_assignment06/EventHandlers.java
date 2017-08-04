package daddario_steven_assignment06;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;



 //Event handler for the radio button for selecting to enter a ticker
 class RadioInputHandler implements EventHandler<ActionEvent>{
     //Make attributes
     private HBox topPane; 
     private TextField enterField; 
     private ListView<String> tickerView; 
     private Button profileButton; 
     private StockScreener view; 
     private StackPane fileNotFound;
     
     //Set the attributes via a constructor
     public RadioInputHandler(HBox topPane, TextField enterField, ListView<String> tickerView, Button profileButton, StockScreener view, StackPane fileNotFound){
         this.topPane = topPane; 
         this.enterField = enterField; 
         this.tickerView = tickerView;
         this.profileButton = profileButton;
         this.view = view; 
         this.fileNotFound = fileNotFound;
     }
     
     //The handler code
     public void handle(ActionEvent e){
        topPane.getChildren().add(enterField); 
        topPane.getChildren().remove(tickerView);
        if(topPane.getChildren().contains(profileButton)){
            topPane.getChildren().remove(profileButton);
            topPane.getChildren().add(profileButton);
        }
        
        if(tickerView.getSelectionModel().getSelectedItem() != null){
            enterField.setText(tickerView.getSelectionModel().getSelectedItem());
        }
        if(view.getChildren().contains(fileNotFound)){
            if(view.buttonBar.getChildren().contains(view.tenDayAvgBt)){
                view.buttonBar.getChildren().remove(view.tenDayAvgBt);
            }
        }
        
        BorderPane.setMargin(view.buttonBar, new Insets(100, 0, 0, 150));
    }
     
 }

//RadioButton for choosing to select the ticker from the ListView
class RadioLoadHandler implements EventHandler<ActionEvent>{
   //Attributes 
   private HBox topPane;
   private TextField enterField; 
   private ListView<String> tickerView;
   private Button profileButton;
   private StackPane fileNotFound; 
   private Label lblNotFound;
   private HashSet<String> setOfSymbolsWithPrices = new HashSet<>(); 
   private Consolidator c;
   private File[] files;
   private StockScreener view;
   
   //Set the attributes via a constructor 
    public RadioLoadHandler(HBox topPane, TextField enterField, ListView<String> tickerView, Button profileButton, StackPane fileNotFound, Label lblNotFound, StockScreener view, Consolidator c, File[] files){
        this.topPane = topPane; 
        this.enterField = enterField; 
        this.tickerView = tickerView;
        this.profileButton = profileButton;
        this.fileNotFound = fileNotFound; 
        this.lblNotFound = lblNotFound;
        this.c = c;
        this.files = files;
        this.view = view;
        
        String fileName;
        for(File f: files){
            
            for(String s: FXCollections.observableArrayList(c.getArrayListOfSymbols())){
                s = s + ".csv";
                if(s.equals(f.getName())){
                    setOfSymbolsWithPrices.add(s);
                   
                }
            }
        }
        
    }
    
    
    //handler code
    public void handle(ActionEvent e){
        topPane.getChildren().remove(enterField);
        topPane.getChildren().add(tickerView);
    
        //Adding and then removing the profile button at the start but after 
        //adding the tickerView to preserve the order.
        if(topPane.getChildren().contains(profileButton)){
            topPane.getChildren().remove(profileButton);
            topPane.getChildren().add(profileButton);
        }
        
       
        
        if(lblNotFound.getText().equals("Ticker not found"))lblNotFound.setText("No price info found");
        if(tickerView.getSelectionModel().getSelectedIndex() == -1){
         
           if(topPane.getChildren().contains(profileButton)) topPane.getChildren().remove(profileButton);
           
           
        }     
        else{
            if(view.getChildren().contains(fileNotFound)){
               
                if(setOfSymbolsWithPrices.contains(tickerView.getSelectionModel().getSelectedItem() + ".csv")){
                    view.getChildren().remove(fileNotFound);
                    
                }
            }
            if(!topPane.getChildren().contains(profileButton)) topPane.getChildren().add(profileButton);
          
              
        }
           
        
       
        
        if(enterField.getText() != ""){
            int index = 0;
            for(String symbol: tickerView.getItems()){
                    
                if(symbol.equals(enterField.getText())){       
                    tickerView.getSelectionModel().select(symbol);
                    tickerView.getFocusModel().focus(index);
                    tickerView.scrollTo(symbol);
                    break;
                }
            index++;
            }
       }
        
        BorderPane.setMargin(view.buttonBar, new Insets(50, 0, 0, 150));
    }
    
    
}

//Event handler for the TextField that will take the input from the user 
//and locate the Ticker. It loads the info into the ComboBox 
class EnterFieldHandler implements EventHandler<ActionEvent>{
    //Make attributes
    private CheckBox lowChk, highChk, closedChk; 
    private TextArea infoPanel; 
    private ComboBox datesCombo; 
    private Stage chartStage; 
    private StackPane fileNotFound;
    private StockScreener viewPane; 
    private StringBuilder symbolName;
    private File[] files;
    private TextField enterField;
    private Consolidator c;
    private HBox midPane; 
    private Button chartButton;
    private Button profileButton; 
    private ListView<String> tickerView;
    private HBox topPane; 
    private Label lblNotFound; 
    private Stage profileStage;
    private TextArea profileDisplay;
    private HBox buttonBar; 
    private Button tenDayAvgBt; 
                                
    //set the attributes via the constructor                                                                  
    public EnterFieldHandler(CheckBox lowChk, CheckBox highChk, CheckBox closedChk, TextArea infoPanel, ComboBox datesCombo, Stage chartStage, StockScreener viewPane, StackPane fileNotFound, StringBuilder symbolName, File[] files, TextField enterField, Consolidator c, HBox midPane, Button chartButton, Button profileButton, ListView<String> tickerView, HBox topPane, Label lblNotFound, Stage profileStage, TextArea profileDisplay, HBox buttonBar, Button tenDayAvgBt){
        this.lowChk = lowChk; 
        this.highChk = highChk; 
        this.closedChk = closedChk; 
        this.infoPanel = infoPanel; 
        this.datesCombo = datesCombo; 
        this.chartStage = chartStage; 
        this.symbolName = symbolName;
        this.files = files; 
        this.enterField = enterField;
        this.c = c;
        this.midPane = midPane; 
        this.chartButton = chartButton;
        this.viewPane = viewPane;
        this.fileNotFound = fileNotFound;
        this.profileButton = profileButton; 
        this.tickerView = tickerView;
        this.topPane = topPane;
        this.lblNotFound = lblNotFound;
        this.profileStage = profileStage; 
        this.profileDisplay = profileDisplay; 
        this.buttonBar = buttonBar; 
        this.tenDayAvgBt = tenDayAvgBt;

    }
    
    //the handler code
    public void handle(ActionEvent e){
        lowChk.setSelected(false);
        highChk.setSelected(false);
        closedChk.setSelected(false);
        infoPanel.setText("");
         
        datesCombo.getItems().clear();
        datesCombo.getSelectionModel().select("");
         
        profileStage.hide();
        chartStage.hide(); 
        viewPane.avgStage.hide();
         
        if(buttonBar.getChildren().contains(chartButton)) buttonBar.getChildren().remove(chartButton);  
        if(buttonBar.getChildren().contains(tenDayAvgBt)) buttonBar.getChildren().remove(tenDayAvgBt); 
        if(viewPane.getChildren().contains(fileNotFound)) buttonBar.getChildren().remove(fileNotFound);
        
       
            
        if(tickerView.getItems().contains(enterField.getText().trim())){
            if(!topPane.getChildren().contains(profileButton)) topPane.getChildren().add(profileButton);
        
            boolean fileFound = false; 
            for(File f: files){
                symbolName.delete(0, symbolName.length());
                symbolName.append(enterField.getText());
                if(f.getName().equals(symbolName + ".csv")){
                     fileFound = true; 
                    if(!viewPane.getChildren().contains(midPane)) viewPane.setCenter(midPane);
                    if(!buttonBar.getChildren().contains(chartButton)){
                        if(buttonBar.getChildren().contains(tenDayAvgBt)){
                            buttonBar.getChildren().remove(tenDayAvgBt);
                            buttonBar.getChildren().add(chartButton);
                            
                        }
                        else{
                            buttonBar.getChildren().add(chartButton);
                        }
                    } 
                    if(!buttonBar.getChildren().contains(tenDayAvgBt)) buttonBar.getChildren().add(tenDayAvgBt); 
                
                    try{
                        c.getValuesFor(symbolName.toString());
                    }
                    catch(FileNotFoundException ex){
                        
                    }
                    
                    for(Object[] array: c.getAllPricesFor(symbolName.toString())){
                        datesCombo.getItems().add((String)array[0]);
                        
                    }
                    
               } 
            }  
             
            if(!fileFound){
                lblNotFound.setText("No price info found");
                if(!viewPane.getChildren().contains(fileNotFound)) viewPane.setCenter(fileNotFound);
            }
        
        }
        else{
            
            if(topPane.getChildren().contains(profileButton)) topPane.getChildren().remove(profileButton);
            
            if(!viewPane.getChildren().contains(fileNotFound)) {
                lblNotFound.setText("Ticker not found");
                viewPane.setCenter(fileNotFound);
                
            }
        }
     
    }
}

//The ListView Listener  that will display the tickers and load information for 
//the selected ticker 
class TickerViewListener implements InvalidationListener{
    //Attributes
    private Button profileButton; 
    private HBox topPane;
    private CheckBox lowChk, highChk, closedChk; 
    private TextArea infoPanel; 
    private ComboBox datesCombo; 
    private Button chartButton; 
    private StockScreener viewPane; 
    private HBox midPane; 
    private Stage chartStage; 
    private StackPane fileNotFound;
    private File[] files;
    private StringBuilder symbolName;
    private ListView<String> tickerView;
    private Consolidator c; 
    private Label lblNotFound;
    private Stage profileStage; 
    private TextArea profileDisplay;
    private HBox buttonBar; 
    private Button tenDayAvgBt; 
    
    //set the attributes                                     
    public TickerViewListener(Button profileButton, HBox topPane, CheckBox lowChk, CheckBox highChk, CheckBox closedChk, ComboBox datesCombo,  TextArea infoPanel, Button chartButton, StockScreener viewPane, HBox midPane, Stage chartStage, StackPane fileNotFound, File[] files, StringBuilder symbolName, ListView<String> tickerView, Consolidator c, Label lblNotFound, Stage profileStage, TextArea profileDisplay, HBox buttonBar, Button tenDayAvgBt){
        this.profileButton = profileButton;
        this.topPane = topPane; 
        this.lowChk = lowChk; 
        this.highChk = highChk; 
        this.closedChk = closedChk; 
        this.infoPanel = infoPanel; 
        this.datesCombo = datesCombo; 
        this.chartButton = chartButton; 
        this.viewPane = viewPane; 
        this.midPane = midPane; 
        this.chartStage = chartStage; 
        this.fileNotFound = fileNotFound;
        this.files = files; 
        this.symbolName = symbolName; 
        this.tickerView = tickerView; 
        this.c = c; 
        this.lblNotFound = lblNotFound;
        this.profileStage = profileStage; 
        this.profileDisplay = profileDisplay; 
        this.buttonBar = buttonBar;
        this.tenDayAvgBt = tenDayAvgBt;
    }
    
    //The listener code
    public void invalidated(Observable ov){
        lowChk.setSelected(false);
        highChk.setSelected(false);
        closedChk.setSelected(false);
        infoPanel.setText("");
        datesCombo.getItems().clear();
        datesCombo.getSelectionModel().select("");
        if(buttonBar.getChildren().contains(chartButton)) buttonBar.getChildren().remove(chartButton); 
        if(viewPane.getChildren().contains(midPane)) viewPane.getChildren().remove(midPane);
        profileStage.hide();
        chartStage.hide(); 
        viewPane.avgStage.hide();
        if(viewPane.getChildren().contains(fileNotFound)) viewPane.getChildren().remove(fileNotFound);
        if(!topPane.getChildren().contains(profileButton)) topPane.getChildren().add(profileButton);
        
           
            
           
           
        boolean fileFound = false; 
        for(File f: files){
            symbolName.delete(0, symbolName.length());
            symbolName.append(tickerView.getSelectionModel().getSelectedItem());
            symbolName.trimToSize();
             
            if(f.getName().equals(symbolName + ".csv")){
                fileFound = true; 
                if(!viewPane.getChildren().contains(midPane)) viewPane.setCenter(midPane);
                if(!buttonBar.getChildren().contains(chartButton)){
                        if(buttonBar.getChildren().contains(tenDayAvgBt)){
                            buttonBar.getChildren().remove(tenDayAvgBt);
                            buttonBar.getChildren().add(chartButton);
                            buttonBar.getChildren().add(tenDayAvgBt);
                        }
                        else{
                            buttonBar.getChildren().add(chartButton);
                        }
                    } 
                    if(!buttonBar.getChildren().contains(tenDayAvgBt)) buttonBar.getChildren().add(tenDayAvgBt); 
                try{
                        c.getValuesFor(symbolName.toString());
                }
                catch(FileNotFoundException ex){
                        
                }
                    
                for(Object[] array: c.getAllPricesFor(symbolName.toString())){
                    datesCombo.getItems().add((String)array[0]);
                        
                }
                    
             }
             if(!fileFound){
         
                 if(!viewPane.getChildren().contains(fileNotFound)) viewPane.setCenter(fileNotFound);
         
             }
         }
    }
}

//Event handler for the combobox that will display the available dates 
//and then load the information for the selected date
class DatesComboHandler implements EventHandler<ActionEvent>{
    
    //Attributes
    private CheckBox lowChk, highChk, closedChk; 
    private TextArea infoPanel; 
    private Stage chartStage; 
    private HBox midPane; 
    private VBox checkBoxPane; 
    private StringBuilder symbolName; 
    private ComboBox datesCombo; 
    private Object[] prices; 
    private Consolidator c; 
    
    //set the attributes via the constructor
    public DatesComboHandler(CheckBox lowChk, CheckBox highChk, CheckBox closedChk, TextArea infoPanel, Stage chartStage, HBox midPane, VBox checkBoxPane, StringBuilder symbolName, ComboBox datesCombo, Object[] prices, Consolidator c){
        this.lowChk = lowChk; 
        this.highChk = highChk; 
        this.closedChk = closedChk; 
        this.infoPanel = infoPanel; 
        this.chartStage = chartStage; 
        this.midPane = midPane; 
        this.checkBoxPane = checkBoxPane; 
        this.symbolName = symbolName; 
        this.datesCombo = datesCombo;
        this.prices = prices; 
        this.c = c;
    }
    
    //The code of the handler
    public void handle(ActionEvent e){
         lowChk.setSelected(false);
         highChk.setSelected(false);
         closedChk.setSelected(false);
         infoPanel.setText("");
         chartStage.hide(); 
            
         
         if(!midPane.getChildren().contains(checkBoxPane)) midPane.getChildren().addAll(checkBoxPane, infoPanel); 
         
         
         
         for(Object[] oArray: c.getAllPricesFor(symbolName.toString())){
             if(oArray[0] == datesCombo.getValue()){
                 prices[0] = oArray[0];
                 prices[1] = oArray[1];
                 prices[2] = oArray[2];
                 prices[3] = oArray[3];
                 prices[4] = oArray[4];
                 break;
             }
         }
    }
}

//Handler for both all three CheckBoxes
class CheckBoxHandler implements EventHandler<ActionEvent>{
    //Make Attributes
    private CheckBox lowChk, highChk, closedChk; 
    private Object[] prices;
    private TextArea infoPanel; 
    
    //Set Attributes with the constructor
    public CheckBoxHandler(CheckBox lowChk, CheckBox highChk, CheckBox closedChk, Object[] prices, TextArea infoPanel){
        this.lowChk = lowChk; 
        this.highChk = highChk; 
        this.closedChk = closedChk; 
        this.prices = prices; 
        this.infoPanel = infoPanel;
    }
    
    //Event handler code
    public void handle(ActionEvent e){
        String text = "";
         
         if(lowChk.isSelected()){
             text += "Low Price: " + prices[2] + "\n";
         }
         
         else{
            text += "\n";
         }
         
         if(highChk.isSelected()){
             text += "High Price: " + prices[1] + "\n";
         }
         
         else{
             text += "\n";
         }
         
         if(closedChk.isSelected()){
             text += "Adj Closed  Price: " + prices[3]; 
         }
         
         infoPanel.setText(text);
    }
}

//Event handler for the Chart Button 
class ChartButtonHandler implements EventHandler<ActionEvent>{
   //Make attributes
   private LineChart<String, Number> chartNode;
   private XYChart.Series series; 
   private int chartHeight;
   private int chartWidth;
   private StringBuilder symbolName; 
   private Stage chartStage;
   private Consolidator c; 
   private Slider slider; 
   
   //Set attributes
   public ChartButtonHandler(LineChart<String, Number> chartNode, XYChart.Series series, int chartHeight, int chartWidth, StringBuilder symbolName, Stage chartStage, Consolidator c, Slider slider){
         this.chartNode = chartNode; 
         this.series = series; 
         this.chartHeight = chartHeight; 
         this.chartWidth = chartWidth; 
         this.symbolName = symbolName; 
         this.chartStage = chartStage; 
         this.c = c;
         this.slider = slider; 
    }
    
    //code for the event handler
    public void handle(ActionEvent e){
        chartNode.setMaxHeight(chartHeight);
        chartNode.setMaxWidth(chartWidth); 
        chartNode.setMinHeight(chartHeight); 
        chartNode.setMinWidth(chartWidth); 
        slider.setValue(0);
        
        series.getData().clear();
        
        //The following will make a chart with all the close prices 
        
        for(Object[] oArray: c.getAllPricesFor(symbolName.toString())){
            series.getData().add((new XYChart.Data((String)oArray[0], (Double)oArray[3])));
        }
       
        
        chartStage.show(); 
    }
}

//Listener for the Slider
class SliderListener implements InvalidationListener{
    //Make Attributes
    private LineChart<String, Number> chartNode; 
    private int chartHeight; 
    private int chartWidth; 
    private Slider slider; 
    private Stage chartStage; 
    
    //Set attributes via constructor
    public SliderListener(LineChart<String, Number> chartNode, int chartHeight, int chartWidth, Slider slider, Stage chartStage){
        this.chartNode = chartNode; 
        this.chartHeight = chartHeight; 
        this.chartWidth = chartWidth; 
        this.slider = slider; 
        this.chartStage = chartStage; 
    }
    
    //Code for the listener
    public void invalidated(Observable ov){
        chartNode.setMaxHeight(chartHeight + 4.5 * slider.getValue());
        chartNode.setMaxWidth(chartWidth + 4.5 * slider.getValue() + 4.5 * (4 / 5) * slider.getValue());
        chartNode.setMinHeight(chartHeight + 4.5 * slider.getValue());
        chartNode.setMinWidth(chartWidth + 4.5 * slider.getValue() + 4.5 * (4 / 5) * slider.getValue());

        chartStage.setMaxHeight(chartHeight + 50 + 4.5 * slider.getValue());
        chartStage.setMaxWidth(chartWidth + 50 + 4.5 * slider.getValue() + 4.5 * (4 / 5) * slider.getValue());
        chartStage.setMinHeight(chartHeight + 50 + 4.5 * slider.getValue());
        chartStage.setMinWidth(chartWidth + 50 + 4.5 * slider.getValue() + 4.5 * (4 / 5) * slider.getValue());
    }
}
//Handler Class for the profile button
class ProfileButtonHandler implements EventHandler<ActionEvent>{
    
    //Attributes
    private Stage profileStage; 
    private TextArea profileDisplay;
    private Consolidator c; 
    private HashMap<String, HashMap> profileMap;
    private String name = ""; 
    private String marketCap = "";
    private String iPOYear = "";
    private String sector = "";
    private String industry = "";
    private String summaryQuote = "";
    private StringBuilder symbolName; 
    
    //Setting the attributes in the constructor
    public ProfileButtonHandler(Stage profileStage, TextArea profileDisplay, StringBuilder symbolName, Consolidator c) throws FileNotFoundException{
        this.profileStage = profileStage; 
        this.profileDisplay = profileDisplay;
        this.c = c; 
        this.symbolName = symbolName;
        profileMap = c.getAllProfiles();
    }
    
    //Handle code
    public void handle(ActionEvent e){
        name = (String)(profileMap.get(symbolName.toString()).get("Name"));
        marketCap = (String)(profileMap.get(symbolName.toString()).get("MarketCap"));
        iPOYear = (String)(profileMap.get(symbolName.toString()).get("IPOYear"));
        sector = (String)(profileMap.get(symbolName.toString()).get("Sector"));
        industry = (String)(profileMap.get(symbolName.toString()).get("Industry"));
        summaryQuote = (String)(profileMap.get(symbolName.toString()).get("Summary Quote"));
        
        profileDisplay.setText("Name: " + name + "\n"+
                         "MarketCap: " + marketCap + "\n" +
                         "IPOYear: " + iPOYear + "\n" + 
                         "Sector: " + sector + "\n" +
                         "Industry: " + industry + "\n" +
                         "Summary Quote: " + summaryQuote + "\n");
        
        profileStage.show();
    }
}


class TenDayAvgHandler implements EventHandler<ActionEvent>{
    
    StockScreener view; 
    Consolidator c; 
    StringBuilder symbolName; 
    
    public TenDayAvgHandler(StockScreener view, Consolidator c, StringBuilder symbolName){
        this.view = view;
        this.c = c; 
        this.symbolName = symbolName;
    }
    
    public void handle(ActionEvent e){
        view.avgArea.clear(); 
        
        String avgs = "";
        for(Object[] oArray: c.getAllPricesFor(symbolName.toString())){
             if(oArray[4] == null) continue;
             if((Double)oArray[3] > (Double)oArray[4]){
                 
                 avgs += String.format("%-15s Adj Close: %-15.5f Ten Day Avg: %-15.5f\n", oArray[0], oArray[3], oArray[4]);
                 
             }
         }
        
        if(avgs == ""){
            avgs += "                   No Adj Closed Prices Greater Than The Ten Day Avg"; 
        }
        
        view.avgArea.setText(avgs);
        view.avgStage.setTitle(symbolName.toString() + " Adj Cls > Ten Day Avg");
        view.avgStage.show();
        
    }
}