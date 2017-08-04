package daddario_steven_assignment06;

import java.util.Arrays; 
import java.util.Iterator;
import java.util.LinkedHashSet; 
//Will use a LinkedHashSet as the outer container and witin this container, 
//
public class MyDataStructure {
    protected LinkedHashSet<Object[]> outerContainer = new LinkedHashSet<>();
    
    public MyDataStructure(){
        
    }
    
    //method for adding to the container
    public void addArray(Object[] array){
        outerContainer.add(array);
    }
    

    //Method for printing the data 
    public void printMyDataStructure(){
        for(Object[] objArray: outerContainer){
           System.out.println("Date: " + objArray[0] + "---High: " + objArray[1] + "---Low: " + objArray[2] + "---Adj Close: " + objArray[3] + "--- avg" + objArray[4]);
         
        }
    }
    
    
    protected Iterator outerIterator(){
        return outerContainer.iterator(); 
    }
    
    protected Iterator innerIteratorFor(int i1){
        Object[] tempArray = new Object[outerContainer.size()]; 
        
       Iterator iter = outerContainer.iterator();  
       int i2 = 0;  
       while(iter.hasNext()){
            tempArray[i2] = iter.next();
            i2++; 
        }
       
       return Arrays.asList((Object[])(tempArray[i1])).iterator();
    }
}
