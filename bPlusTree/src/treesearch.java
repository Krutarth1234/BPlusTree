import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.ads.bTree.bPlusTree;
import com.ads.bTree.tuple;

public class treesearch {
    public static void main(String [] args) {
    	


        String file_name = "input.txt";
        String output_file = "output_file.txt";
        String curr_line = null;
        if (args.length < 1) {
    		System.out.println("Input file not given");
    		//return;
    	}else {
    		file_name = args[0];
    	} 
        try {
        	FileReader fileReader = new FileReader(file_name);
        	BufferedReader bufferedReader = new BufferedReader(fileReader);
        	bPlusTree tree;
            FileWriter fileWriter = new FileWriter(output_file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String currLine = bufferedReader.readLine();
            tree = new bPlusTree(Integer.parseInt(currLine));
            currLine = bufferedReader.readLine();
            while(currLine != null) {
            	 String[] currSplit = currLine.split("[(,)]");
            	 String instruction = currSplit[0];
            	 if(instruction.equals("Insert")) {
            		 float key = Float.parseFloat(currSplit[1]);
                	 String value = currSplit[2];
            		 tree.insert(new tuple<Float, String>(key, value));
            	 }else if(instruction.equals("Search")) {
            		 if(currSplit.length==2) {
            			 float key = Float.parseFloat(currSplit[1]);
            			 ArrayList<tuple<Float,String>> result = tree.search(key);
            			 if (result.size()==0) {
            				 bufferedWriter.write("Null");
            				 bufferedWriter.newLine();
            			 }
            			 else {
                			 String toPrint = "";
                			 for (int i=0; i<result.size();i++) {
                				 toPrint += result.get(i).getY()+",";
                			 }
                			 bufferedWriter.write(toPrint.substring(0, toPrint.length()-1));
                			 bufferedWriter.newLine();
            			 }
            		 }else {
            			 float startKey= Float.parseFloat(currSplit[1]);
            			 float endKey=Float.parseFloat(currSplit[2]);;
            			 ArrayList<tuple<Float,String>> result = tree.searchRange(startKey, endKey);
            			 String toPrint = "";
            			 for (int i=0; i<result.size();i++) {
            				 toPrint += "("+result.get(i).getX()+","+result.get(i).getY()+"),";
            			 }
            			 bufferedWriter.write(toPrint.substring(0, toPrint.length()-1));
            			 bufferedWriter.newLine();
            		 }
            	 }
                 currLine = bufferedReader.readLine();
            }   
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + file_name + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error in reading/writing file'");                  
        }
    }
}
