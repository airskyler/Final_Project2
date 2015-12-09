package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

/**
 * Created by Jessy on 12/2/2015.
 */
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

    private ButtonGroup decision;




    public AlbumInfo(final MusicTableModel mtm){


        super("Album Business Information");  // Display for the title

        setContentPane(rootPanel);  //  setting the JPanel called "rootPanel" to JFrame


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting the default close operation on JFrame
        setVisible(true);// make the form visible
        setSize(new Dimension(600, 600));   // setting the form size


        consignorTable.setModel(mtm);
        //Grid color default is white, change it so we can see it
        consignorTable.setGridColor(Color.BLACK);
        //Also make the columns wider
        consignorTable.getColumnModel().getColumn(0).setWidth(400);



        // Making the radio button for having a choice to input sales information and consignor/Album ButtonGroup,
        // so that only one radio button can be selected
        decision = new ButtonGroup();
        decision.add(consignorAlbumRadioButton);
        decision.add(salesInformationRadioButton);




        // ActionListener is listening to any change on consignor/Album radio button
        consignorAlbumRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // if centralAC radio button is selected, set the visibility for the property
                    textConsignorName.setVisible(true);
                    textPhoneNumber.setVisible(true);

                    textMoneyOwed.setVisible(true);
                    labCosignorName.setVisible(true);
                    labPhoneNumber.setVisible(true);

                    labMoneyOwed.setVisible(true);

                    textSoldDate.setVisible(false);
                    labelSoldDate.setVisible(false);
                    textSoldPrice.setVisible(false);
                    labelSoldPrice.setVisible(false);


            }
        });





        String consignor = textConsignorName.getText();
        String phoneNumber = textPhoneNumber.getText();
        //MusicDataBase.addConsignor(consignor, phoneNumber);


        salesInformationRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textConsignorName.setVisible(false);
                textPhoneNumber.setVisible(false);

                textMoneyOwed.setVisible(false);
                labCosignorName.setVisible(false);
                labPhoneNumber.setVisible(false);

                labMoneyOwed.setVisible(false);

                textSoldDate.setVisible(true);
                labelSoldDate.setVisible(true);
                textSoldPrice.setVisible(true);
                labelSoldPrice.setVisible(true);



            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDataBase.shutdown();
                System.exit(0);


            }
        });


        addInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String consignorData = textConsignorName.getText();

                if(consignorData == null || consignorData.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "Please enter consignor's name");
                    return;
                }



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






                double sellPriceData = Double.parseDouble(textMoneyOwed.getText());

                try{
                    if(sellPriceData > 100000000){
                        System.out.println("Please enter the correct selling Album price ");
                    }
                }catch (NumberFormatException me){
                    JOptionPane.showMessageDialog(rootPane,"Please enter the number between 0 thru 9 for selling Album Price ");
                }



                System.out.println("Adding " + consignorData+ " "+ phoneData+ " "+ sellPriceData);

                boolean insertedRow = mtm.insertRow(consignorData, phoneData, sellPriceData);
                if(insertedRow){

                    MusicDataBase.loadAllMusic();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Error adding a new information");
                }

            }
        });



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




        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textConsignorName.setText("");
                textPhoneNumber.setText("");

                textMoneyOwed.setText("");
                textSoldDate.setText("");
                textSoldPrice.setText("");

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
