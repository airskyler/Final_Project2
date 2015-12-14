
// this class of SalesGUI is basically creating a new frame, so that i can display a
// Sales Table data grid.  It has all the property name, method for each button with action Listener

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jessy on 12/9/2015.
 */

// SalesGUI's property name
public class SalesGUI extends JFrame{
    private JTextField textSoldPrice;
    private JTextField textPayConsignor;
    private JTextField textPayOwner;
    private JButton addInformationButton;
    private JButton deleteInformationButton;
    private JButton clearButton;
    private JButton exitButton;
    private JTable tableSales;
    private JPanel rootPanel;



    // making the SalesTableModel variable stm as constant in this SalesGUI class
    public SalesGUI(final SalesTableModel stm){


        super("Sales Information");  // Display for the title

        setContentPane(rootPanel);  //  setting the JPanel called "rootPanel" to JFrame


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting the default close operation on JFrame
        setVisible(true);// make the form visible
        setSize(new Dimension(600, 600));   // setting the form size


        tableSales.setModel(stm);
        //making the grid color to black
        tableSales.setGridColor(Color.BLACK);
        //Also make the columns wider
        tableSales.getColumnModel().getColumn(0).setWidth(400);



        // if you click a add information button in a sales Table GUI, it will add the user input to the textBox
        // for the sold price of the album, pay amount to consignor and pay amount of owner to the Sales Table data grid
        addInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // store the user input from the textBox for sold Price in a soldPriceData variable
                // if the price is less than zero, it will display a dialog message to enter a album's sold price
                double soldPriceData = Double.parseDouble(textSoldPrice.getText());

                if (soldPriceData < 0) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter Album's sold price ");
                    return;
                }


                // calculate the pay consignor amount by the sold price
                // and display the amount in the textBox
                double payConsignorData = (soldPriceData / 100) * 40;
                textPayConsignor.setText(Double.toString(payConsignorData));


                // calculate the pay owner amount by the sold price
                // and display the amount in the textBox
                double payOwnerData = (soldPriceData / 100) * 60;
                textPayOwner.setText(Double.toString(payOwnerData));

                // display a message for adding a data to sales Table
                System.out.println("Adding " + soldPriceData + " "
                        + payConsignorData + " " + payOwnerData);


                // if all the user input for the data is usable to insert a row in a salesTable variable stm,
                // it will make the boolean variable of " insertRow" to true and loadAllSales method is called in
                // a MusicDataBase class to fill the data or update the data to a Sales Table result set
                boolean insertedRow = stm.insertSales(soldPriceData, payConsignorData, payOwnerData);
                if (insertedRow) {

                    MusicDataBase.loadAllSales();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error adding a new information");
                }

            }

        });





        // if one of the sales table GUI data row is high lighted and click delete button
        // it will delete that row
        deleteInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = tableSales.getSelectedRow();
                boolean deleted = stm.deleteRow(currentRow);
                if (deleted) {
                    MusicDataBase.loadAllSales();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting information");
                }
            }
        });




        // if clear button is clicked on sales table,
        // it will clear the textBox on the sales table GUI
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textSoldPrice.setText("");
                textPayConsignor.setText("");
                textPayOwner.setText("");

            }
        });



        // if you click a exit button on sales table GUI
        // it will close just the sales Table GUI
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDataBase.loadAllAlbum();
                MusicDataBase.loadAllMusic();
                dispose();



            }
        });


    }
}

