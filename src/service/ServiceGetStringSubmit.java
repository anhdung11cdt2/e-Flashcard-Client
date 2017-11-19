/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thehap Rok
 */
public class ServiceGetStringSubmit {

    public static String getString(DataInputStream dis) {
        String reviece = "";
        byte[] messageByte = new byte[10000];
        int bytesRead;
        try {
            bytesRead = dis.read(messageByte);
            reviece += new String(messageByte, 0, bytesRead);

        } catch (IOException ex) {
            Logger.getLogger(ServiceGetStringSubmit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reviece;
    }
}
