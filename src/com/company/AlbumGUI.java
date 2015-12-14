
// This AlbumGUI class is basically making a new frame for user to input a
// information about the Album title, artist and selling price and displaying
// the album table data grid. It also has a property names and
// method for each button with a action Listener



package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Jessy on 12/9/2015.
 */

// AlbumGUI's property name
public class AlbumGUI extends JFrame{
    private JTextField textAlbumTitle;
    private JTextField textAlbumArtist;
    private JTextField textSellingPrice;
    private JButton deleteInformationButton;
    private JButton clearButton;
    private JButton exitButton;
    private JButton addInformationButton;
    private JPanel rootPane;
    private JTable tableAlbum;
    private JComboBox consignorComboBox;


    // making the AlbumTableModel variable atm as constant in this AlbumGUI class
    public AlbumGUI(final AlbumTableModel atm){


        super("Album Information");  // Display for the title

        setContentPane(rootPane);  //  setting the JPanel called "rootPane" to JFrame


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting the default close operation on JFrame
        setVisible(true);// make the form visible
        setSize(new Dimension(600, 600));   // setting the form size


        tableAlbum.setModel(atm);
        // setting the Grid color to black
        tableAlbum.setGridColor(Color.BLACK);
        //Also make the columns wider
        tableAlbum.getColumnModel().getColumn(0).setWidth(400);


        // calling a getinfo method in MusicDataBase class
        fillCombo(MusicDataBase.getInfo());



        // if you click a add information button in a Album Table GUI, it will add the user input to the textBox
        // for the information about Album title, artist and selling price to the album table data grid
        addInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // store the user input from the textBox for album title in a titleData variable
                // if the title data is empty, it will display a dialog message to enter a album's title
                String titleData = textAlbumTitle.getText();

                if (titleData == null || titleData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter Album Title ");
                    return;
                }


                // store the user input from the textBox for album's artist in a artistData variable
                // if the artist data is empty, it will display a dialog message to enter a album's artist
                String artistData = textAlbumArtist.getText();

                if (artistData == null || artistData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a Album Artist ");
                    return;
                }

                // store the user input from the textBox for album's selling price in a sellPriceData variable
                // if the sell price data is less than zero, it will display a dialog message to enter a album's selling price
                double sellPriceData = Double.parseDouble(textSellingPrice.getText());

                try {
                    if (sellPriceData < 0) {
                        System.out.println("Please enter the correct selling Album price ");
                    }


                    // Checking if the user input the number in the textBox
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the number between 0 thru 9 for selling Album Price ");
                }


                //TODO, why need this code?
                // creating a array variable called " comboData" to store the ConsignorID from the comboBox
                // and store the first data from the Array comboData to variable called "id"
                String[] comboData = consignorComboBox.getSelectedItem().toString().split(" ");
                int id = Integer.parseInt(comboData[0]);


                // display a message for adding the album information
                System.out.println("Adding " + titleData + " "
                        + artistData + " " + sellPriceData);


                // if all the user input for the data is usable to insert a row in a album Table variable atm,
                // it will make the boolean variable of " insertRow" to true and loadAllAlbum method is called in
                // a MusicDataBase class to fill the data or update the data to a album Table result set
                boolean insertedRow = atm.insertAlbum(titleData, artistData, sellPriceData, id);
                if (insertedRow) {

                    MusicDataBase.loadAllAlbum();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error adding a new information");
                }

            }

        });


        // if one of the album table GUI data row is high lighted and click delete button
        // it will delete that row
        deleteInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = tableAlbum.getSelectedRow();
                boolean deleted = atm.deleteRow(currentRow);
                if (deleted) {
                    MusicDataBase.loadAllAlbum();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting information");
                }
            }
        });




        // if clear button is clicked on album table,
        // it will clear the textBox on the album table GUI
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textAlbumTitle.setText("");
                textAlbumArtist.setText("");
                textSellingPrice.setText("");

            }
        });



        // if you click a exit button on album table GUI
        // it will close just the album Table GUI
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MusicDataBase.loadAllMusic();
                dispose();


            }
        });


    }

    public void fillCombo(HashMap hash){


        Set keySet = hash.keySet(); //Creates a set of the keys, and iterate over that
        for ( Object id : keySet) {
            //Use the key to get each value. Repeat for each key.
            consignorComboBox.addItem(id + " - " + hash.get(id));
        }



    }
}
