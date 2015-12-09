package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by Jessy on 12/9/2015.
 */
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

    public AlbumGUI(final MusicTableModel mtm){


        super("Album Information");  // Display for the title

        setContentPane(rootPane);  //  setting the JPanel called "rootPanel" to JFrame


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting the default close operation on JFrame
        setVisible(true);// make the form visible
        setSize(new Dimension(600, 600));   // setting the form size


        tableAlbum.setModel(mtm);
        //Grid color default is white, change it so we can see it
        tableAlbum.setGridColor(Color.BLACK);
        //Also make the columns wider
        tableAlbum.getColumnModel().getColumn(0).setWidth(400);


        addInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String titleData = textAlbumTitle.getText();

                if (titleData == null || titleData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter Album Title ");
                    return;
                }


                String artistData = textAlbumArtist.getText();

                if (artistData == null || artistData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a Album Artist ");
                    return;
                }

                double sellPriceData = Double.parseDouble(textSellingPrice.getText());

                try {
                    if (sellPriceData < 100000000) {
                        System.out.println("Please enter the correct selling Album price ");
                    }
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the number between 0 thru 9 for selling Album Price ");
                }


                System.out.println("Adding " + titleData + " "
                        + artistData + " " + sellPriceData);

                boolean insertedRow = mtm.insertAlbum(titleData, artistData, sellPriceData);
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
                int currentRow = tableAlbum.getSelectedRow();
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

                textAlbumTitle.setText("");
                textAlbumArtist.setText("");
                textSellingPrice.setText("");

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
