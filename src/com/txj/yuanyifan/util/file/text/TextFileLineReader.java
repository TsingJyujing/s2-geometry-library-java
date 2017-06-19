package com.txj.yuanyifan.util.file.text;

/**
 *
 * @author Yuan Yifan
 *  Function: Read large text file line by line.
 * Usage: 
 * Firstly, new a object  TxtFileLineRead and initial it by your filename:
 * 	TxtFileLineRead FRL = new TxtFileLineRead(FileName);
 * Secondly, the function in this object fReadln to return a line.
 *  For example:
 *  String Exper = FRL.fReadln();
 * 	If the text file has N lines you have to execute this function for N times to read it all
 * Last but not least:
 * 	You have to free file before terminating your program like this:
 * 	FRL.FreeFile();
 * Have fun!
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class TextFileLineReader {
    //public static void main(){
    //  You can write Usage in main or a new function.
    //  Such as：
    //  System.out.println("Firstly, new a object  TextFileLineReader and initial it by your filename:");
    //  And so on
    //}
    
    private FileReader fp = null;
    private BufferedReader br = null;
    private int Flag_Loaded = 0;
    public boolean silence = false;
    
    public TextFileLineReader(String FileName){
        load_file(FileName);
    }
    
    public TextFileLineReader(){
    	//Do nothing
    }
    public int load_file(String FileName){
    	//Using for debug
        if (!silence){
            System.out.println("Try to load " + FileName);
        }
        try{
            if (Flag_Loaded==1){
                free();
            }
            fp = new FileReader(FileName);
            br = new BufferedReader(fp);
            Flag_Loaded = 1;
            return(0);
        }catch (FileNotFoundException e){
            if (!silence){
                System.out.println("File Not Found.");
                e.printStackTrace();
            }
            return(-1);
        }
    }
    
    public String line_read(){
        try {
            String Str = br.readLine();
            return(Str);
            //return null while end of the line
        } catch (Exception e) {
            if (!silence){
                System.out.println("Error while reading the line");
                e.printStackTrace();
            }
            return null;
        }
    }

    public void free(){
        try {
            if (Flag_Loaded != 0){
                br.close();
                fp.close();
                Flag_Loaded = 0;
            }
        } catch (Exception e) {
            if (!silence){
                System.out.println("Error while free the line");
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void finalize() throws Throwable{
        free();
        super.finalize();
    }
}
