package com.txj.yuanyifan.util.file.text;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author Yuan Yifan
  Function: Read large text file line by line.
 Usage: 
 Firstly, new a object  TextFileLineWriter and initial it by your filename:
 	TextFileLineWriter FWL = new TextFileLineWriter(FileName);
 Secondly, the function in this object str_write to write a String.
  For example:
  FWL.str_write("This is a test!\r\n");
 	You have to use "\r\n" in Win to shift a new line.
 Last but not least:
 	You have to free file before terminating your program like this:
 	FWL.free();
 Have fun!
 * 
 */
public class TextFileLineWriter {
    
    private FileWriter fp = null;
    private BufferedWriter bw = null;
    private int Flag_Loaded = 0;
    public boolean silence = false;
    
    /**
     * initialization
     */
    public TextFileLineWriter(){}
    
    /**
     * initialization
     * @param FileName
     */
    public TextFileLineWriter(String FileName){
        load_file(FileName);
    }
    
    /**
     * Load file manuelly
     * @param FileName
     */
    public void load_file(String FileName){
        try{
            if (Flag_Loaded==1){
                free();
            }else{
                fp = new FileWriter(FileName);
                bw = new BufferedWriter(fp);
                Flag_Loaded = 1;
            }
        }catch(Exception e) {
           if (!silence){
                System.out.println("Can't load file.");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Write given str into file
     * @param StrWrite
     */
    public void str_write(String StrWrite){
        try{
            bw.write(StrWrite,0,StrWrite.length());
        }catch(Exception e) {
            if (!silence){
                System.out.println("Can't write file.");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Free file while finalize
     */
    public void free(){
        try {
            if (Flag_Loaded==1){
                bw.close();
                fp.close();
                Flag_Loaded = 0;
            }
        } catch (Exception e) {
            if (!silence){
                System.out.println("Can't free file.");
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
