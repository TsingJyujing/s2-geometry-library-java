package com.txj.yuanyifan.util.file.selector;

import javax.swing.*;

/**
 * Created by Administrator on 2017/6/19.
 */
public class CommonDialogs {
    public static String getFolder() throws Exception {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = chooser.showDialog(null, "选择文件夹");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getPath() + "\\";
        }else{
            throw new Exception("Didn't select folder normally");
        }
    }

    public static String getFolder(String defaultValue) {
        try{
            return getFolder();
        }catch(Exception ex){
            return  defaultValue;
        }
    }
}
