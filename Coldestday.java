
/**
 * Décrivez votre classe Coldestday ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Coldestday {
   // first question finding the coldest Hour in a file
   public CSVRecord coldestHourInFile(CSVParser parser){
       CSVRecord lowestSoFar = null;
       for(CSVRecord currentRow : parser){
           if(lowestSoFar == null){
               lowestSoFar = currentRow ;
           }
           else{
               double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));   
               double lowestTemp = Double.parseDouble(lowestSoFar.get("TemperatureF"));
               if(lowestTemp > currentTemp && currentTemp != -9999){
                  lowestSoFar = currentRow;
               }
           }
       }
       return lowestSoFar;
   }
   void testColdestHourInFile(){
       FileResource fr = new FileResource();
       CSVRecord lowest = coldestHourInFile(fr.getCSVParser());
       System.out.println("coldest Temperature was: "+ lowest.get("TemperatureF") + " at "+ lowest.get("TimeEDT") );
   }
   // Second question to find the name of the file with  the coldest temperature hour
   public String fileWithColdestTemperature(){
       //select the files where to find the temperature
       DirectoryResource dr = new DirectoryResource();
       CSVRecord lowestFile = null;
       String fileColdest = null;
       double minTemp = 100;
       
       for(File f : dr.selectedFiles()){
          FileResource fr = new FileResource(f);
          File coldestF = null;
          CSVParser fileParser = fr.getCSVParser();
          lowestFile = coldestHourInFile(fileParser);
          double currTemp = Double.parseDouble(lowestFile.get("TemperatureF"));
          if(currTemp < minTemp){
            minTemp = currTemp;
            fileColdest = f.getName(); 
            coldestF = f;
          }
       }   
       System.out.println("\n\n Coldest temperature was: " + minTemp);
       return fileColdest ;
   }
   public void testFileWithColdestTemperature(){
       String nameOfFile = null;
       nameOfFile = fileWithColdestTemperature();
       System.out.println(" The coldest day was in the  file: " + nameOfFile);
   } 
   
   public CSVRecord lowestHumidityInFile(CSVParser parser){
       CSVRecord lowestHumSoFar = null;
       CSVRecord trrrrrr = null;
       for(CSVRecord currentRow : parser){
           if(lowestHumSoFar == null){
               lowestHumSoFar = currentRow ;
           }
           else{
                 String currenthumidity = currentRow.get("Humidity");
                 try{
                         double currenthum = Double.parseDouble(currenthumidity);
                         double lowestHumidity  = Double.parseDouble(lowestHumSoFar.get("Humidity"));
                         if(currenthum < lowestHumidity ){
                                 lowestHumSoFar = currentRow;
                         }
                  } catch(NumberFormatException ex){
                         return null;
                 }
   
               //if(currenthumidity != "N/A"){
                  
               //}
           }
       }
       return lowestHumSoFar;
   }
   public void testLowestHumidityInFile(){
       System.out.println("\n\n");
       System.out.println("****************************************************");
       System.out.println("Part three: ");
       FileResource fr = new FileResource();
       CSVParser parser = fr.getCSVParser();
       CSVRecord lowestHumidity = lowestHumidityInFile(parser);
       System.out.println(" Lowest Humidity was: " + lowestHumidity.get("Humidity") + " at "+ lowestHumidity.get("DateUTC") );
   } 
   
   public CSVRecord lowestHumidityInManyFiles(){
       double minHum = 100;
       double currHum = 0.0;
       CSVRecord fileLowestHum = null;
       CSVRecord lowestHumInFr = null;
       DirectoryResource dr = new DirectoryResource();
       for(File f: dr.selectedFiles()){
          FileResource fr = new FileResource(f);
          CSVParser fileParser = fr.getCSVParser();
          lowestHumInFr = lowestHumidityInFile(fileParser);
          try{ 
               currHum = Double.parseDouble(lowestHumInFr.get("Humidity"));
               if(currHum < minHum){
                  minHum = currHum;
                  fileLowestHum = lowestHumInFr;
               }
          } catch(NumberFormatException ex){
                         return null;
          }
          
       }
       return fileLowestHum;
   }
   public void testLowestHumidityInManyFiles(){
       System.out.println("\n\n");
       System.out.println("------------------------------------------------");
       System.out.println("Part four : ");
       CSVRecord ourFile = lowestHumidityInManyFiles();
       System.out.println("Lowest Humidity was "+ ourFile.get("Humidity") + " at " + ourFile.get("DateUTC") );   
   }
   
   public double averageTemperatureInFile(CSVParser parser){
      int compt = 0;
      double sumTemp = 0.0;
      double currTemp = 0.0;
      double moyTemp = 0.0;
      for(CSVRecord currentRow: parser){
        currTemp = Double.parseDouble(currentRow.get("TemperatureF"));
        sumTemp = sumTemp + currTemp;
        compt = compt +1 ;
      }
      moyTemp = sumTemp/compt;
      return moyTemp;
   }
   public void testAverageTemperatureInFile(){
       FileResource fr = new FileResource();
       CSVParser parser = fr.getCSVParser();
       double moyenneTemp = averageTemperatureInFile(parser);
       System.out.println("Average Temperature in file is : " + moyenneTemp ); 
    
   }
   
   public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
       double sumTemp = 0.0;
       int compt = 0;
       double currHum = 0.0;
       double moyTemp = 0.0;
       for(CSVRecord currentRow : parser){
          currHum = Double.parseDouble(currentRow.get("Humidity"));
          if(currHum >= value){
             double currTemp = 0.0;
             currTemp = Double.parseDouble(currentRow.get("TemperatureF"));
             sumTemp = sumTemp + currTemp;
             compt = compt +1 ;
          }
       }
       moyTemp = sumTemp/compt;
       return moyTemp;
   }
   public void testAverageTemperatureWithHighHumidity(){
       FileResource fr = new FileResource();
       CSVParser parser = fr.getCSVParser();
       double moyenneTemp = averageTemperatureWithHighHumidityInFile(parser, 80 );
       if(moyenneTemp != 0.0){
            System.out.println("Average Temperature in file is : " + moyenneTemp ); 
       }
       else{
            System.out.println("No temperature with that Humidity ");
       }
   }
   
   
   
}
