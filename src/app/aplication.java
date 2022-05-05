package app;

import java.util.*;
import app.app.*;
import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;

public class aplication {
    public static void main(String[] args) {

        app app = new app();


        JFrame frame = new JFrame("app");
        frame.setContentPane( app.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);




    }



}
