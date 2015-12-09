package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jessy on 12/9/2015.
 */
public class SalesGUI extends JFrame{
    private JTextField textSoldPrice;
    private JTextField textPayConsignor;
    private JTextField textPayOwner;
    private JButton addInformationButton;
    private JButton deleteInformationButton;
    private JButton clearButton;
    private JButton exitButton;
    private JTable tableSales;


    public SalesGUI(final MusicTableModel mtm){


        super("Sales Information");  // Display for the title

        setContentPane(rootPane);  //  setting the JPanel called "rootPanel" to JFrame


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting the default close operation on JFrame
        setVisible(true);// make the form visible
        setSize(new Dimension(600, 600));   // setting the form size


        tableSales.setModel(mtm);
        //Grid color default is white, change it so we can see it
        tableSales.setGridColor(Color.BLACK);
        //Also make the columns wider
        tableSales.getColumnModel().getColumn(0).setWidth(400);


        addInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double soldPriceData = Double.parseDouble(textSoldPrice.getText());

                if (soldPriceData < 0) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter Album's sold price ");
                    return;
                }


                double payConsignorData = Double.parseDouble(textPayConsignor.getText());

                if (payConsignorData < 0) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a amount for Consignor pay ");
                    return;
                }

                double payOwnerData = Double.parseDouble(textPayOwner.getText());

                try {
                    if (payOwnerData > 100000000) {
                        System.out.println("Please enter the correct amount for Owner pay ");
                    }
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the number between 0 thru 9 for selling Album Price ");
                }


                System.out.println("Adding " + soldPriceData + " "
                        + payConsignorData + " " + payOwnerData);

                boolean insertedRow = mtm.insertSales(soldPriceData, payConsignorData, payOwnerData);
                if (insertedRow) {

                    MusicDataBase.loadAllMusic();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error adding a new information");
                }

            }

        });

        deleteInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = tableSales.getSelectedRow();
                boolean deleted = mtm.deleteRow(currentRow);
                if (deleted) {
                    MusicDataBase.loadAllMusic();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting information");
                }
            }
        });




        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textSoldPrice.setText("");
                textPayConsignor.setText("");
                textPayOwner.setText("");

            }
        });



        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDataBase.shutdown();
                System.exit(0);


            }
        });


    }
}

