package app;

import com.fazecast.jSerialComm.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;



public class app extends Thread {
    private JButton connectButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private JPanel mainPanel;
    private JTextField upField;
    private JCheckBox conectedCheckBox;
    private JButton startButton;
    private JButton stopButton;
    private JTextField downField;
    private JComboBox comboBox;
    private JButton searchButton;
    private JButton manualButton;
    private JButton disconnectButton;
    private JButton rotaryLeftButton;
    private JButton rotaryRightButton;
    private JTextField infoField;
    private JTextPane datatextPane;
    private JButton pidAvlues;


    pidRegulator pid = new pidRegulator();


    byte[] sendBufer = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};

    private int rotaryLeft;

    private int rotaryRight;

    private int manual;

    private int connected;

    private int start;

    private int stop;

    private boolean startWork = false;

    private int numberPort;


    public int getRotaryLeft() {
        return rotaryLeft;
    }

    public void setRotaryLeft(int rotaryLeft) {
        this.rotaryLeft = rotaryLeft;
    }

    public int getRotaryRight() {
        return rotaryRight;
    }

    public void setRotaryRight(int rotaryRight) {
        this.rotaryRight = rotaryRight;
    }

    public int getManual() {
        return manual;
    }

    public void setManual(int manual) {
        this.manual = manual;
    }


    public int getConnected() {
        return connected;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public boolean isStartWork() {
        return startWork;
    }

    public void setStartWork(boolean startWork) {
        this.startWork = startWork;
    }


    public void setNumberPort(int numberPort) {
        this.numberPort = numberPort;
    }

    public int getNumberPort() {
        return numberPort;
    }

    SerialPort[] comPort = SerialPort.getCommPorts();

    Thread thread = new Thread(){
        public void run() {

           // try {
                toByte(pid, start, stop, manual, rotaryLeft, rotaryRight, startWork);
                // SerialPort[] comPort = SerialPort.getCommPorts();
                // compute primes larger than minPrime
                System.out.println("ff");
                if (comboBox.getSelectedIndex()>0&&comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                    System.out.println("ddd");

                    comPort[comboBox.getSelectedIndex()].writeBytes(sendBufer, 8);
                }else{

                    System.out.println(sendBufer);
                }
               // thread.sleep(100);
          //  } catch (InterruptedException e) {
           //     e.printStackTrace();
           // }
        }
    };



    public app() {

        thread.start();

        MyComPortListiner listiner = new MyComPortListiner();
        connectButton.addActionListener(new ActionListener() {

            public String byteToHex(byte num)
            {
                char[] hexDigits = new char[2];
                hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
                hexDigits[1] = Character.forDigit((num & 0xF), 16);
                return new String(hexDigits);
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    comPort[comboBox.getSelectedIndex()].openPort();

                    if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                        InputStream in =comPort[comboBox.getSelectedIndex()].getInputStream();
                        comPort[comboBox.getSelectedIndex()].addDataListener(new SerialPortDataListener() {


                            @Override
                            public int getListeningEvents() {
                                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                            }

                            @Override
                            public void serialEvent(SerialPortEvent event) {

                                try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }
                                String f="";

                               if(  SerialPort.LISTENING_EVENT_DATA_RECEIVED>8 ) {


                                   byte[] newData = event.getReceivedData();

                                   for (int i = 0; i < newData.length; ++i) {

                                       String l=String.valueOf((char) newData[i]);
                                       f=f+l;

                                   }
                                   System.out.print(f);
                                   datatextPane.setText(f);
                               }
                            }

                        });
                        conectedCheckBox.setSelected(true);
                        infoField.setText("Połączono z portem COM");
                    }
                }catch(IndexOutOfBoundsException g)
                {
                    infoField.setText("Nie wybrany Port");
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    startWork =true;
                    start = 1;
                    stop = 0;



            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startWork = false;
                stop = 1;
                start = 0;

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    comboBox.addItem(SerialPort.getCommPorts()[0]);
                    comboBox.addItem(SerialPort.getCommPorts()[1]);
                    comboBox.addItem(SerialPort.getCommPorts()[2]);
                    comboBox.addItem(SerialPort.getCommPorts()[3]);
                    comboBox.addItem(SerialPort.getCommPorts()[4]);
                    comboBox.addItem(SerialPort.getCommPorts()[5]);

                }catch(IndexOutOfBoundsException g){


                }
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startWork = false;
              //  comPort = SerialPort.getCommPorts()[comboBox.getSelectedIndex()];
                comPort[comboBox.getSelectedIndex()].closePort();
                    comPort[comboBox.getSelectedIndex()].removeDataListener();
                if (comPort[comboBox.getSelectedIndex()].isOpen() == false) {

                    conectedCheckBox.setSelected(false);
                    infoField.setText("Rozłączono z portem COM");
                }
                }catch(IndexOutOfBoundsException g){


                }
            }
        });



        rotaryLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               rotaryLeft = 1;
               rotaryRight =0;
            }
        });
        rotaryRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotaryLeft = 0;
                rotaryRight =1;
            }
        });
        manualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manual = 1;
                startWork =false;
            }

        });
        pidAvlues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                pid .setVisible(true);
                pid .pack();
                pid .setLocationRelativeTo(null);
                pid .setBounds(420, 0, 220, 220);
                pid.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);




            }
        });
    }

    private void createUIComponents() {

        // TODO: place custom component creation code here
    }

    private void toByte(pidRegulator pid , int start , int stop, int manual ,int rotaryLeft, int rotaryRight ,boolean startWork ){

// status sendBuffer = {proportional , integral , derivative , start , stop, manual , rotaryLeft, rotaryRight, workState}
      //  byte[] sendBufer = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};

        sendBufer[0]=(byte)pid.getPidP();
        sendBufer[1]=(byte)pid.getPidI();
        sendBufer[2]=(byte)pid.getPidD();
        sendBufer[3]=(byte)start;
        sendBufer[4]=(byte)stop;
        sendBufer[5]=(byte)manual;
        sendBufer[6]=(byte)rotaryLeft;
        sendBufer[7]=(byte)rotaryRight;






        if (startWork == true){

            sendBufer[8]=0x01;
        }else{

            sendBufer[8]=0x00;
        }

    }

    public void run() {
        toByte( pid , start , stop,  manual , rotaryLeft,  rotaryRight ,startWork );
       // SerialPort[] comPort = SerialPort.getCommPorts();
        // compute primes larger than minPrime
        if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {


            comPort[comboBox.getSelectedIndex()].writeBytes( sendBufer,8);
        }


    }
}


