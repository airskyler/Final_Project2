// This AlbumTableModel class is basically a function on the result set of the database
// it can add and update the data in database for certain column of data like
// Title column, artist column, selling price column and Consignor ID
// for the use of a album Table data grid in a Album Table GUI
// Lots of the code in this class is a code example from the URL link of "https:github.com/minneapolis-edu/Movies"



package com.company;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jessy on 12/7/2015.
 */
public class AlbumTableModel extends AbstractTableModel {
    ResultSet resultSet;
    int numberOfRows= 0;
    int numberOfColumns = 0;



    //Constructor - use a ResultSet to work out how many rows and columns we have.
    AlbumTableModel(ResultSet rs) {
        this.resultSet = rs;
        setup();

    }

    private void setup(){

        countRows();

        try{
            numberOfColumns = resultSet.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }

    }

    private void countRows() {
        numberOfRows = 0;
        try {
            //Move cursor to the start...
            resultSet.beforeFirst();
            // next() method moves the cursor forward one row and returns true if there is another row ahead
            while (resultSet.next()) {
                numberOfRows++;

            }
            resultSet.beforeFirst();

        } catch (SQLException se) {
            System.out.println("Error counting rows " + se);
        }

    }

    public void updateResultSet(ResultSet newRS){
        resultSet = newRS;
        setup();
    }


    //returns true if successful, false if error occurs
    // method for trying to insert Album information in a database to Title column, artist column, selling price column and Consignor ID
    public boolean insertAlbum(String title, String artist, double sellPrice, int id) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();

            resultSet.updateString(MusicDataBase.Title_COLUMN, title);
            resultSet.updateString(MusicDataBase.Artist_COLUMN, artist);
            resultSet.updateDouble(MusicDataBase.Selling_Price_COLUMN, sellPrice);
            resultSet.updateInt("ConsignorID", id);

            java.sql.Date currentSQL = new java.sql.Date(System.currentTimeMillis());

            // using current date to store the date data to database for Date received column
            resultSet.updateDate(MusicDataBase.Date_Received_COLUMN, currentSQL);

            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            //This change goes to DB but is *not* reflected in this result set
            //So need to close and re-open result set to see latest data
            //Return true to the calling method so we know that the ResultSet
            //was successfully updated, and it can request a new ResultSet for this tablemodel.
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }


    @Override
    public int getRowCount() {
        countRows();
        return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        return numberOfColumns;
    }


    @Override
    //Fetch value for the cell at (row, col).
    //The table will call toString on the object, so it's a good idea
    //to return a String or something that implements toString in a useful way
    public String getValueAt(int row, int col) {
        try {
            //Move to this row in the result set. Rows are numbered 1, 2, 3...
            resultSet.absolute(row + 1);
            //And get the column at this row. Columns numbered 1, 2, 3...
            Object o = resultSet.getObject(col + 1);
            return o.toString();
        } catch (SQLException sqle) {
            //Display the text of the error message in the cell
            return sqle.toString();
        }


    }

    @Override
    public String getColumnName(int col){
        //Get from ResultSet metadata, which contains the database column names

        try {
            return resultSet.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }


    //Delete row, return true if successful, false otherwise
    public boolean deleteRow(int row){
        try {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            //Tell table to redraw itself
            fireTableDataChanged();
            return true;
        }catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }



    //returns true if successful, false if error occurs
    /*
    public boolean insertRow(String consignor, int phone, String title, String artist,
                             double sellPrice) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();

            resultSet.updateString(MusicDataBase.Consignor_COLUMN, consignor);
            resultSet.updateInt(MusicDataBase.Phone_COLUMN, phone);
            resultSet.updateDouble("Money_owed", sellPrice);
            //resultSet.updateString(MusicDataBase.Title_COLUMN, title);
            //resultSet.updateString(MusicDataBase.Artist_COLUMN, artist);
            //resultSet.updateDouble(MusicDataBase.Selling_Price_COLUMN, sellPrice);

            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            //This change goes to DB but is *not* reflected in this result set
            //So need to close and re-open result set to see latest data
            //Return true to the calling method so we know that the ResultSet
            //was successfully updated, and it can request a new ResultSet for this tablemodel.
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }
*/




}

