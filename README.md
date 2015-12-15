# Final_Project2

// Hi Clare... So... I have a little problem with pushing my Final project to my Github account for one of the java file from my Final // project. I have total of 7 java file and 3 form file for GUI. The 6 of my java file and 3 of the form file is pushed successfully // to
// my GitHub account but, for some reason, it won't let me push the "SalesTableModel.java file to my GitHub account. (I have tried
// pushing it twice to my GitHub account, but it didn't push the "SalesTableModel.java file" to my GitHub account.)

// So... I decided to paste the code for the "SalesTableModel.java file" to this README.MD file, to see it my help a little bit for my
// final project review.  I also have created a "SalesTableModel.java file" to the Gist GitHub link of 
//     https://gist.github.com/airskyler/7d8153042c124370feff          (I think you can still fork or clone from this link)

//  Sorry for the inconvenience

// My short discription for the final project is towards the end of this README.MD file


// The code for the "SalesTableModel.java file" START HERE!!!

// This SalesTableModel class is basically a function on the result set of the database
// it can add and update the data in database for certain column of data like
// Sold Price column, Money Cosign column, Money owner column.
// for the use of a sales Table data grid in a Sales Table GUI
// Lots of the code in this class is a code example from the URL link of "https:github.com/minneapolis-edu/Movies"



package com.company;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jessy on 12/10/2015.
 */
public class SalesTableModel extends AbstractTableModel {

    ResultSet resultSet;
    int numberOfRows = 0;
    int numberOfColumns = 0;


    //Constructor - use a ResultSet to work out how many rows and columns we have.
    SalesTableModel(ResultSet rs) {
        this.resultSet = rs;
        setup();

    }

    private void setup() {

        countRows();

        try {
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

    public void updateResultSet(ResultSet newRS) {
        resultSet = newRS;
        setup();
    }



    //returns true if successful, false if error occurs
    // method for trying to insert Sales information in a database to Sold Price column, Money Cosign column, Money owner column.
    public boolean insertSales(double soldPrice, double payConsign, double payOwner) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();

            resultSet.updateDouble(MusicDataBase.Sold_Price_COLUMN, soldPrice);
            resultSet.updateDouble("Money_Cosign",payConsign);
            resultSet.updateDouble("Money_Owner", payOwner);


            resultSet.updateInt("ConsignorID", 23);
            resultSet.updateInt("AlbumID", 1);


            java.sql.Date currentSQL = new java.sql.Date(System.currentTimeMillis());
            resultSet.updateDate(MusicDataBase.Sold_Date_COLUMN, currentSQL);

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
    public String getColumnName(int col) {
        //Get from ResultSet metadata, which contains the database column names

        try {
            return resultSet.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }


    //Delete row, return true if successful, false otherwise
    public boolean deleteRow(int row) {
        try {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            //Tell table to redraw itself
            fireTableDataChanged();
            return true;
        } catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }


}


///////////////////////////////////  End of SalesTableModel class code !!!


-	A short description of my final project - 

Jessy Mullen

•	My user name for SQL is “root”
•	My password for a SQL is “airskyler&19790201”
•	I don’t see any bugs yet on my final project.



The thing that you should do for my final project is enter a data to the blank textbox and click the add information button to see,
if the data you enter in the textbox has displayed in a consignor table, album table or sales table. You can also delete the data,
if you click on the row inside the table and high light the row and click delete information button.  
I have created three JFrame with displaying three different table for each frame. 
Each table has blank textbox to enter a data and there is a add information button, delete button, clear button and exit button.


•	Just to let you know… if you click the Exit button on the JFrame with a consignor table, it will close the connection to the database. 

•	If you click on the exit button for the JFrame with album Table or sales Table… it will just close the JFrame and it won’t close the connection to the database.

•	If you click the clear button, it will just clear the textbox for the new data entry

•	For Received Date for album table, I used a current date  


I also have a combo button with a drop down list of Consignor ID in JFrame with an album table, 
which can be used to adding an album information about title, artist and selling price along with a Consignor ID of your choice 
to add the data in an album table. This same Consignor ID is also used in a consignor table which contains a consignor’s name, 
phone number and money owed to the consignor. By looking at the Consignor ID inside the tables of consignor table, 
album table and sales table, it will be easy for you to see how the data is related to each table. 






