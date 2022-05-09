package app;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class pidRegulator extends JFrame {

    private JPanel pidPane;
    private JTextField P;
    private JTextField I;
    private JTextField D;
    private JTable table;

    public int getPidP() {
        return pidP;
    }

    public void setPidP(int pidP) {
        this.pidP = pidP;
    }

    public int getPidI() {
        return pidI;
    }

    public void setPidI(int pidI) {
        this.pidI = pidI;
    }

    public int getPidD() {
        return pidD;
    }

    public void setPidD(int pidD) {
        this.pidD = pidD;
    }

    private int pidP;

   private int pidI;

   private int pidD;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    pidRegulator frame = new pidRegulator();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public pidRegulator() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

                //();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 150, 150);
        pidPane = new JPanel();
        pidPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(pidPane);
        pidPane.setLayout(null);

        JLabel lblDishName = new JLabel("P");
        lblDishName.setFont(new Font("High Tower Text", Font.BOLD, 15));
        lblDishName.setBounds(15, 15, 50, 15);
        pidPane.add(lblDishName);

        JLabel lblD = new JLabel("I");
        lblD.setFont(new Font("High Tower Text", Font.BOLD, 15));
        lblD.setBounds(15, 35, 50, 15);
        pidPane.add(lblD);

        JLabel lblDishType = new JLabel("D");
        lblDishType.setFont(new Font("High Tower Text", Font.BOLD, 15));
        lblDishType.setBounds(15, 55, 50, 15);
        pidPane.add(lblDishType);

        P = new JTextField();
        P.setFont(new Font("High Tower Text", Font.BOLD, 15));
        P.setBounds(30, 15, 50, 15);
        pidPane.add(P);
        P.setColumns(10);

        I = new JTextField();
        I.setFont(new Font("High Tower Text", Font.BOLD, 15));
        I.setBounds(30, 35, 50, 15);
        pidPane.add(I);
        I.setColumns(10);

        D = new JTextField();
        D.setFont(new Font("High Tower Text", Font.BOLD, 15));
        D.setBounds(30, 55, 50, 15);
        pidPane.add(D);
        D.setColumns(10);





        JButton sendPID = new JButton("SEND");
        sendPID.setBounds(90, 15, 80, 80);
        pidPane.add(sendPID);


        sendPID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pidP= Integer.parseInt( P.getText() ) ;
                pidI= Integer.parseInt( I.getText() ) ;
                pidD= Integer.parseInt( D.getText() ) ;
            }
        });


    }
}