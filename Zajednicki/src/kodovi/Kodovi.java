/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kodovi;

/**
 *
 * @author ivan
 */
public class Kodovi {
    
  //port
    public static final int PORT = 11111;
    
  //server_response
    public static final int STATUS_ODGOVOR_SERVER_OK     =  999;
    public static final int STATUS_ODGOVOR_SERVER_NOT_OK = -999;
    
  //class1 operacije [1-4]
    public static final int SELECT_class1 = 11; 
    public static final int INSERT_class1 = 12;
    public static final int UPDATE_class1 = 13;
    public static final int DELETE_class1 = 14;
       
  //class2 operacije [1-4]
    public static final int SELECT_class2 = 21;
    public static final int INSERT_class2 = 22;
    public static final int UPDATE_class2 = 23;
    public static final int DELETE_class2 = 24;
  
  //class3 operacije [1-4]
    public static final int SELECT_class3 = 31;    
    public static final int INSERT_class3 = 32;    
    public static final int UPDATE_class3 = 33;        
    public static final int DELETE_class3 = 34;    
        
}
