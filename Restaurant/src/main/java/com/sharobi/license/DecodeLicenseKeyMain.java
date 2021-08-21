package com.sharobi.license;

import javax.swing.JOptionPane;

import com.sharobi.license.struts.key.KeyBean;
import com.sharobi.license.struts.key.KeyDetails;

public class DecodeLicenseKeyMain {
                
                public static void main(String[] args){
                                //TGFhZ05GemRtMDFEZ2Fk
                    			String name = JOptionPane.showInputDialog(null, "Enter The Licese Key?");
                    
                                KeyDetails keyDetails = new KeyDetails();
                                KeyBean bean = keyDetails.getKeyDetails(name);
                                
                                JOptionPane.showMessageDialog(null, "Key for Store id: " + bean.getStore_id());
                                
                }

}
