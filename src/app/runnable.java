package app;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread;

import com.fazecast.jSerialComm.*;

public class runnable extends Thread {




    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public runnable() {

    }
        //  static int portnumber=1;

        @Override
        public void run () {
            while (true) {
                SerialPort[] comPort = SerialPort.getCommPorts();
                comPort[id].openPort();
                try {



                    if (comPort[id].isOpen() == true) {
                        while (comPort[id].bytesAvailable() == 0)
                            Thread.sleep(20);
                        InputStream in = comPort[id].getInputStream();

                        System.out.println((char) in.read());
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch(IOException f)
                {
                    f.printStackTrace();
                }
            }
        }
/*
    SerialPort[] comPort = SerialPort.getCommPorts();
    comPort[portnumber].openPort();
    // data.setChooseSerial(comboBox.getSelectedIndex());
    if (comPort[portnumber].isOpen() == true) {

        InputStream in =comPort[portnumber].getInputStream();
        try{
            System.out.println((char)in.read());
        }catch(IOException e){
System.out.println("....");
            e.getStackTrace();
        }


}*/
        // comPort[1].closePort();


    }




