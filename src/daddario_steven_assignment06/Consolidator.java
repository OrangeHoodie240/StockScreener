package daddario_steven_assignment06;

import java.io.FileNotFoundException; 
import java.io.File; 
import java.io.PrintWriter; 
import java.util.ArrayList;
import java.util.Scanner; 
import java.util.TreeMap; 
import java.util.HashMap; 
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

//Class most of the code is done in
public class Consolidator{
   
   //Using TreeMap because the description says for the keys to be ordered
   //but i may be missunderstanding something. 
   private TreeMap<String, Object[]> map = new TreeMap<>(); 
   private Scanner input; 
   private HashMap<String, HashMap> allProfiles;
   
   //Constructor will get symbols from Tickers.csv and load them in the map as
   //keys
   public Consolidator() throws FileNotFoundException{
       allProfiles = getAllProfiles(); 
       findTenDayAvg();
       loadData(); 
       
   }
   
   //Gets the symbols from Tickers.csv and puts in map
   private void loadData() throws FileNotFoundException{
       File tickerFile = new File("Tickers.csv"); 
       input = new Scanner(tickerFile); 
       input.nextLine(); 
       String line; 
       while(input.hasNext()){
           line = input.nextLine(); 
           line = line.split("\"")[1]; 
           map.put(line.trim(), getValueArray(line)); 
       }
   }
   
   //Method that retrieves and loads the math with the values for the symbol
   //Two overriden methods. One for when the filename differs from the 
   //symbol so that it can take two arguments. 
   public void getValuesFor(String symbolName)throws FileNotFoundException{
       File f;
       if(!symbolName.contains("csv")){
            String fileName = symbolName + ".csv"; 
            f = new File(fileName); 
       }
       else{
           f = new File(symbolName); 
       }
       
       loadPriceArray(input, symbolName, f);
   }
   
   //Alternative getValuesFor call
   public void getValuesFor(String symbolName, String fileName)throws FileNotFoundException{
       if(!fileName.contains(".csv")){
            fileName += ".csv";
       }
       File f = new File(fileName); 
       
      loadPriceArray(input, symbolName, f);
   }
   
   //Statements common to both of the above. Goes through the file and performs
   //The correct calls to add an array to the data structure with the data inside
   private void loadPriceArray(Scanner input, String symbolName, File f) throws FileNotFoundException{
        input = new Scanner(f); 
        int adjClsNum;
        String s = input.nextLine();
        if(s.split(",")[5].toUpperCase().contains("VOL")){
            adjClsNum = 6;
        }
        else{
            adjClsNum = 5; 
        }
       
       while(input.hasNext()){
           
           Object[] objArray = getArrayOfData(input.nextLine(), adjClsNum); 
          ((MyDataStructure) map.get(symbolName)[1]).addArray(objArray);
       }
   }
   
   //Makes loads and returns the array that is added to the map
   private Object[] getArrayOfData(String line, int adjClsNum){
      String[] splitLine = line.split(",");  
      String date = splitLine[0]; 
      Double high = Double.valueOf(splitLine[2]); 
      Double low = Double.valueOf(splitLine[3]); 
      Double adjClose = Double.valueOf(splitLine[adjClsNum]);
      //adding date twice temporarily to ensure the size of the array can 
     // is big enough to take the moving averages
      Object[] array = {date, high, low, adjClose, date};
      
      if(splitLine[7].contains("n/a")){
          array[4] = null;
      }
      else{
          Double avg = Double.valueOf(splitLine[7]);
          array[4] = avg;
      }
      
      return array; 
       
      
       
      
   }
  //Returns the Object array holding the objects that will hold the two values
   //(profile and price) The profile is retrieved but an empty MyDataStructure 
  //is created because this will be called when we make the Outer container
   //And because we will only have prices for the few symbols
   private Object[] getValueArray(String line){
       Object[] array = new Object[2];
       array[0] = allProfiles.get(line); 
       array[1] = new MyDataStructure(); 
       return array; 
   }
   
   //Returns a hahsmap with all the profiles
   protected  HashMap getAllProfiles() throws FileNotFoundException{
      File[] setOfFiles = {new File("Amex.csv"), new File("NYSE.csv"), new File("NASDAQ.csv") }; 
      ProfileConstructor pCon = new ProfileConstructor(setOfFiles); 
      return pCon.getMap(); 
  }
   
   //Prints All the data for the passed Symbol
   public void printAllFor(String symbolName){
      System.out.println("______________________________" + symbolName + "____________________________");
      printProfileFor(symbolName);
      System.out.println("\n--------------------------------Prices------------------------");
      if(!((LinkedHashSet)((MyDataStructure)map.get(symbolName)[1]).outerContainer).isEmpty()){
          printPriceValuesFor(symbolName);
      }
      else{
          System.out.println("No prices stored yet.");
      }
   }
   
   public void printProfileFor(String symbolName){
       System.out.print("Name: " + ((HashMap)map.get(symbolName)[0]).get("Name"));
       System.out.print("--MarketCap: " + ((HashMap)map.get(symbolName)[0]).get("MarketCap"));
       System.out.print("--IPOyear: " + ((HashMap)map.get(symbolName)[0]).get("IPOyear"));
       System.out.print("--Sector: " + ((HashMap)map.get(symbolName)[0]).get("Sector"));
       System.out.print("--Industry: " + ((HashMap)map.get(symbolName)[0]).get("Industry"));
       System.out.print("--Summary Quote: " + ((HashMap)map.get(symbolName)[0]).get("Summary Quote"));
   }
   
   //For printing the values attached to a symbol
   public void printPriceValuesFor(String symbolName){
       ((MyDataStructure)map.get(symbolName)[1]).printMyDataStructure(); 
   }
   
   
   
   //Takes the Symbol and the date (date as three arguments) and prints the 
   //prices for that day
   public void printPricesByDate(String symbolName, String year, String month, String day){
   
       String date = year + "-" + month + "-" + day; 
       
       Object[] results = getPriceByDate(symbolName, date); 
       
       if(results.length ==1){
           System.out.println(results[0]);
       }
       else{
           System.out.println("Symbol: " + symbolName + "---Date: " + results[0] + "---High: " + results[1]+ "---Low: " + results[2] + "---Adj Close: " + results[3] );
       }
       
   }
   
   //Helper method used in and for the above.
   protected Object[] getPriceByDate(String symbolName, String date){
       
       LinkedHashSet<Object[]> prices = ((LinkedHashSet)((MyDataStructure)map.get(symbolName)[1]).outerContainer); 
       for(Object[] priceArray: prices){
           if(priceArray[0].equals(date)){
               return priceArray;
           }
       }
       
       Object[] noDate = new Object[1];
       noDate[0] = "Sorry. No price for that date";
       return  noDate; 
   }
   
   //Prints a price/date via iterator. takes symbol name and index in which it would 
   //appear in the LinkedHashSet (which is converted to recieve an iterator.
   public void printInnerInnerKeys(String symbolName, int index){
       Iterator iter = ((MyDataStructure)(map.get(symbolName)[1])).innerIteratorFor(index); 
       
       while(iter.hasNext()){ 
           System.out.println(iter.next()); 
       }
   }
   
   //prints the symbols using the iterator for the outmost map's keys 
   public void printSymbols(){
       Iterator iter = getIteratorForOutmostMapKeys();
       while(iter.hasNext()){
           System.out.println(iter.next()); 
       }
   }
   
   //gets an iterator using the keys from the outmost map
   protected Iterator getIteratorForOutmostMapKeys(){
       return map.keySet().iterator(); 
   }
   
   
   protected ArrayList<String> getArrayListOfSymbols(){
       Iterator i = map.keySet().iterator(); 
       ArrayList<String> a = new ArrayList<>(); 
       while(i.hasNext()){
           a.add((String)(i.next()));
       }
       return a; 
   }
   
   protected ArrayList<Object[]> getAllPricesFor(String symbolName){
 
       Iterator iter = (Iterator)((MyDataStructure)(map.get(symbolName)[1])).outerIterator();
       ArrayList<Object[]> prices = new ArrayList<>(); 
       
       while(iter.hasNext()){
           prices.add((Object[])(iter.next()));
       }
       
       return prices; 
   }
   
   protected void findTenDayAvg()throws FileNotFoundException{
       File f = new File(".");
       File[] files = f.listFiles();
       String name = ""; 
       LinkedList<String> list; 
       String firstLine; 
       Scanner input; 
       HashSet<String> symbols = getSymbols(); 
     
       for(File f2: files){
           if(f2.getName().contains(".csv")){
                name= f2.getName();
                name = name.split(".csv")[0];
               if(symbols.contains(name)){
                   
                   input = new Scanner(f2);
                   firstLine = input.nextLine();
                   
                   if(!firstLine.contains("Ten Day Avg")){
                       list = csvToList(f2);
                       addAvg(list);
                       listToCSV(list, f2);
                   }
                  input.close(); 
               }
           }
        }
   }
   
   protected LinkedList<String> csvToList(File f)throws FileNotFoundException{
       LinkedList<String> list = new LinkedList<>(); 
       Scanner input = new Scanner(f);
       
       boolean headerLine = true; 
       while(input.hasNext()){
           if(headerLine){
               list.add(input.nextLine() + ",Ten Day Avg");
               headerLine = false;
           }
           else{
               list.add(input.nextLine());
           }
       }
       input.close();
       

       
       return list;
   }
   
   protected void listToCSV(LinkedList<String> list, File f)throws FileNotFoundException{
        File f2 = new File(f.getName());
        PrintWriter output = new PrintWriter(f2);
           
        for(int i = 0; i < list.size(); i++){
            
            
            
            if(i > 9 || i == 0){
                output.println(list.get(i));
            }
            else{
                output.println(list.get(i) + ", n/a");
            }
            
        }
           
        output.close(); 
       
   }
   
   protected void addAvg(LinkedList<String> list){
       
       if(list.size() > 10){
            Queue<Double> avgQueue = new LinkedList<>(); 
            double sum; 
            double avg; 
            int adjClsNum = 0; 
       
            if(list.get(0).split(",")[5].toUpperCase().contains("VOL")){
                adjClsNum = 6; 
            }
            else{
                adjClsNum = 5; 
            }
       
           for(int i = 1; i < list.size(); i++){
               Double val = Double.parseDouble(list.get(i).split(",")[adjClsNum]); 
               sum = 0.0;
               
               
               avgQueue.offer(val);
               if(avgQueue.size() > 10){
                   avgQueue.remove(); 
               }
               
               if(i > 9){
                   for(int i2 = 0; i2 < avgQueue.size(); i2++){
                       sum += ((LinkedList<Double>)(avgQueue)).get(i2);
                   }
            
                   avg = sum/10.0; 
                   list.set(i, list.get(i) +  "," + avg); 
               }
               
           }
       }
   }
   
   protected HashSet<String> getSymbols()throws FileNotFoundException{
       HashSet<String> symbols = new HashSet<>(); 
       File f = new File("tickers.csv");
       Scanner input = new Scanner(f); 
       input.nextLine();
       String line; 
       
       while(input.hasNext()){
           line = input.nextLine(); 
           line = line.split("\"")[1]; 
           symbols.add(line.trim());
       }
       return symbols; 
   }
   
   /*
   protected Double[] returnAvg(){
       
   }
*/
}

