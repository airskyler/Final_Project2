
// This AlbumInfo class is basically making a new frame for user to input a
// information about the Consignor's name , phone number and money owed to the consignor,
// and displaying the consignor table data grid. It also has a property names and
// method for each button with a action Listener

// it also has a button called addAlbum button ans AddSales button to display a
// album table and sales table, if the button is clicked. if you click the exit button from the consignor table GUI frame
// it will close the connection to the database



package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jessy on 12/2/2015.
 */


// Property name
public class AlbumInfo extends JFrame implements WindowListener {
    private JTextField textConsignorName;
    private JTextField textPhoneNumber;

    private JButton addInformationButton;
    private JButton clearButton;
    private JButton exitButton;
    private JRadioButton consignorAlbumRadioButton;
    private JRadioButton salesInformationRadioButton;
    private JTextField textMoneyOwed;
    private JPanel rootPanel;
    private JButton deleteInformationButton;
    private JTextField textSoldDate;
    private JTextField textSoldPrice;
    private JLabel labelSoldDate;
    private JLabel labelSoldPrice;
    private JLabel labCosignorName;
    private JLabel labPhoneNumber;

    private JLabel labMoneyOwed;
    private JTable consignorTable;
    private JButton addAlbumButton;
    private JButton addSalesButton;
    private JButton past30DaysButton;
    private JButton pastYearButton;


    // making the MusicTableModel variable mtm as constant in this AlbumInfo class
    public AlbumInfo(final MusicTableModel mtm){


        super("Album Business Information");  // Display for the title

        setContentPane(rootPanel);  //  setting the JPanel called "rootPanel" to JFrame


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting the default close operation on JFrame
        setVisible(true);// make the form visible
        setSize(new Dimension(600, 600));   // setting the form size


        consignorTable.setModel(mtm);

        // setting the grid color to black
        consignorTable.setGridColor(Color.BLACK);

        // making the column wider
        consignorTable.getColumnModel().getColumn(0).setWidth(400);






        String consignor = textConsignorName.getText();
        String phoneNumber = textPhoneNumber.getText();
        //MusicDataBase.addConsignor(consignor, phoneNumber);



        // if you click the exit button on the Consignor table gui frame, it will
        // close the connection to the database and close the frame
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDataBase.shutdown();
                System.exit(0);


            }
        });


        // if you click a add information button in a Consignor Table GUI, it will add the user input to the textBox
        // for the information about Consignor's name, phone number and amount of money owed to consignor to the
        // consignor table data grid
        addInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String consignorData = textConsignorName.getText();

                // store the user input data to the consignorData variable
                // if the variable is empty, the dialog message will display to tell you to enter a consignor's name
                if(consignorData == null || consignorData.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "Please enter consignor's name");
                    return;
                }



                // getting a user input for a consignor's phone number.
                // it also checks to see, if the user entered a number for a user input
                int phoneData;

                try{
                    phoneData = Integer.parseInt(textPhoneNumber.getText());
                    if(phoneData > 1000000000){
                        System.out.println("Please enter a 9 digit number ");

                    }

                }catch (NumberFormatException ne){
                    JOptionPane.showMessageDialog(rootPane, "Please enter the number between 0 thru 9 for a Phone Number ");
                    return;
                }




                // getting a user input for a amount of money owed to a consignor
                // it also checks to see, if the user entered a number for a user input
                double sellPriceData = Double.parseDouble(textMoneyOwed.getText());

                try{
                    if(sellPriceData > 100000000){
                        System.out.println("Please enter the correct selling Album price ");
                    }
                }catch (NumberFormatException me){
                    JOptionPane.showMessageDialog(rootPane,"Please enter the number between 0 thru 9 for selling Album Price ");
                }



                // display a message for adding a user input data
                System.out.println("Adding " + consignorData+ " "+ phoneData+ " "+ sellPriceData);



                // if all the user input for the data is usable to insert a row in a Music Table variable mtm,
                // it will make the boolean variable of " insertRow" to true and loadAllMusic method is called in
                // a MusicDataBase class to fill the data or update the data to a consignor Table result set
                boolean insertedRow = mtm.insertRow(consignorData, phoneData, sellPriceData);
                if(insertedRow){

                    MusicDataBase.loadAllMusic();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Error adding a new information");
                }

            }
        });



        // if one of the consignor table GUI data row is high lighted and click delete button
        // it will delete that row
        deleteInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = consignorTable.getSelectedRow();
                boolean deleted = mtm.deleteRow(currentRow);
                if(deleted){
                    MusicDataBase.loadAllMusic();
                }else{
                    JOptionPane.showMessageDialog(rootPane,"Error deleting information");
                }
            }
        });




        // if clear button is clicked on consignor table,
        // it will clear the textBox on the album table GUI
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textConsignorName.setText("");
                textPhoneNumber.setText("");
                textMoneyOwed.setText("");


            }
        });


        // if addAlbumButton is clicked, it will load all the data for the album table
        // and display the new frame for the album table GUI
        addAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDataBase.loadAllAlbum();
                AlbumGUI albumGUI = new AlbumGUI(MusicDataBase.albumTableModel);
            }
        });


        // if addSalesButton is clicked, it will load all the data for the sales table
        // and display the new frame for the sales table GUI
        addSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDataBase.loadAllSales();
                SalesGUI salesGUI = new SalesGUI(MusicDataBase.salesTableModel);
            }
        });



        // if past 30 days button is clicked, it will display the
        // dialog message box to notify the owner, which consignor name and
        // album title is past 30 days.
        past30DaysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> old = MusicDataBase.checkThirtyDays();
                String message = "";
                for (String info : old){

                    message += info + "\n";
                }
                JOptionPane.showMessageDialog(rootPane, message);

            }
        });


        // if past Year button is clicked, it will display the
        // dialog message box to notify the owner, which consignor name and
        // album title is past year.
        pastYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> yearOld = MusicDataBase.checkYear();
                String messageYear = "";
                for (String info : yearOld){

                    messageYear += info + "\n";
                }
                JOptionPane.showMessageDialog(rootPane,messageYear);


            }
        });
    }





    // windowListener methods, using only one of the method.
    // windowClosing method will call the shutdown method to close the Database connection, etc
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        MusicDataBase.shutdown();

    }


    @Override
    public void windowOpened(WindowEvent e) {

    }


    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
