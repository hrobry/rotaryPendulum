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

public class app {
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
    private JTextArea dataTextArea;
    private JPanel dataPanel;
    private JTextField dataField;
    private int counter =0;
public String dane;
int Odczytane[];
   private byte[] newData;

    public boolean isStartWork() {
        return startWork;
    }

    public void setStartWork(boolean startWork) {
        this.startWork = startWork;
    }

    private boolean startWork = false;

    public void setNumberPort(int numberPort) {
        this.numberPort = numberPort;
    }

    public int getNumberPort() {
        return numberPort;
    }

    private int numberPort;

    public app(){

         SerialPort[] comPort = SerialPort.getCommPorts();
        MyComPortListiner listiner = new MyComPortListiner();
        SerialPort finalPort;
       // data data=new data();
        runnable runi = new runnable();


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
                     //comPort = SerialPort.getCommPorts()[comboBox.getSelectedIndex()];
                    comPort[comboBox.getSelectedIndex()].openPort();
                   // data.setChooseSerial(comboBox.getSelectedIndex());
                  //  runnable.portnumber=comboBox.getSelectedIndex();
                   // runi.setId(comboBox.getSelectedIndex());
                   // runi.start();
                    if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                        InputStream in =comPort[comboBox.getSelectedIndex()].getInputStream();
                        comPort[comboBox.getSelectedIndex()].addDataListener(new SerialPortDataListener() {

                            String l;
                            String f;

                            @Override
                            public int getListeningEvents() {
                                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                            }

                            @Override
                            public void serialEvent(SerialPortEvent event) {

                                try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }
                                String f="";

                               if(  SerialPort.LISTENING_EVENT_DATA_RECEIVED>8 ) {
                                   // byte[] array = {1, 2, 3, 4};

                                   byte[] newData = event.getReceivedData();
                                   //System.out.println("Received data of size: " + newData.length);
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
                try {
                    startWork =true;
                    numberPort = comboBox.getSelectedIndex();
                    comPort[comboBox.getSelectedIndex()].addDataListener(listiner);

                }catch(IndexOutOfBoundsException y)
                {

                }


            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startWork = false;

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // comPort = SerialPort.getCommPorts()[0];

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

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                    byte[] buff =  {0x00};

                    comPort[comboBox.getSelectedIndex()].writeBytes(buff,1);
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                    byte[] buff =  {0x01};

                    comPort[comboBox.getSelectedIndex()].writeBytes(buff,1);
                }
            }
        });
        rotaryLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                    byte[] buff =  {0x02};

                    comPort[comboBox.getSelectedIndex()].writeBytes(buff,1);
                }
            }
        });
        rotaryRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                    byte[] buff =  {0x03};

                    comPort[comboBox.getSelectedIndex()].writeBytes(buff,1);
                }
            }
        });
        manualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                    byte[] buff =  {0x04};

                    comPort[comboBox.getSelectedIndex()].writeBytes(buff,1);
                }
            }

        });
    }

    private void createUIComponents() {

        // TODO: place custom component creation code here
    }


}


