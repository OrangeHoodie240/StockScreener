package daddario_steven_assignment06;

import java.io.File; 
import java.io.PrintWriter; 
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.HashMap; 
import java.util.Iterator;
import java.util.LinkedHashMap; 
public class ProfileConstructor{
    
    //make attributes
    private File[] files; 
    private Scanner input; 
    private String fileName = "Tickers.csv"; 
    protected HashMap<String, HashMap> map = new HashMap<>(); 
    
    //Constructor will take File Array first consolidate into one file 
    //via the consolidateInto() call and then make fill the map by invoking
    //makeMap()
    public ProfileConstructor(File[] files) throws FileNotFoundException{
        this.files = files; 
        consolidateInto(fileName); 
        makeMap(); 
    }
    
    //Takes the file name Attribute which is holding "Tickers.csv" for this
    //program and then creates and outputs all the information from the other
    //files to a file of this name
    private void consolidateInto(String fileName) throws FileNotFoundException{
        PrintWriter output = new PrintWriter(fileName); 
        for(int i = 0; i < files.length; i++){
            input = new Scanner(files[i]); 
            
            if(i > 0)input.nextLine(); 
            
            while(input.hasNext()){
                output.println(input.nextLine());
            }
            input.close(); 
        }
        output.close(); 
    }
    
    //Returns the map attribitu filled in in the constructur from the makeMap() call
    public HashMap getMap(){
        return map; 
    }
    
    //Takes the information in Tickers.csv and puts it in a 
    //the HashMap<String, HashMap> map attribute 
    private void makeMap() throws FileNotFoundException{ 
        String[] line; 
        File file = new File(fileName); 
        input = new Scanner(file); 
        input.nextLine(); 
        while(input.hasNext()){
            
            
            line = input.nextLine().split("\"");
            line[1] = line[1].trim();
            map.put(line[1], new HashMap<String, String>());
            map.get(line[1]).put("Name", line[3]); 
            map.get(line[1]).put("MarketCap", line[7]); 
            map.get(line[1]).put("IPOyear", line[9]);
            map.get(line[1]).put("Sector",line[11]);
            map.get(line[1]).put("Industry", line[13]);
            map.get(line[1]).put("Summary Quote", line[15]); 
            
        }
        
    }
    

    
    

}