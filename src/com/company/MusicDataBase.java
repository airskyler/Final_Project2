
// I got help from Andre from Learning Center at Minneapolis Community and Technical College for
// some of the code on this Final Project. I also used some of the code example from D2L power point slide
// from the class of Week 12 of Part 2 with a URL link of "https:github.com/minneapolis-edu/Movies"

// This MusicDataBase class function is basically making a connection to a database and making a 3 new Tables
// to store the data to the 3 Tables and by writing a query code to database, it can retrieve the data from the
// 3 Tables in a database. It also has a shout down method to disconnect the connection of the database too.


package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicDataBase {


    static final String driver = "com.mysql.jdbc.Driver";


    // connection variable for database
    static final String connection = "jdbc:mysql://localhost:3306/javafinal";


    static final String USER = "root";


    static final String PASSWORD = "airskyler&19790201";

    // setting the variable to null for now
    static Statement database = null;
    static Connection conn = null;
    static ResultSet rs = null;

    static Statement query = null;
    static ResultSet test = null;


    // declaring the column name for the future use on tables display
    public final static String Sales_TABLE_NAME = "Sales";
    public final static String Consignor_COLUMN = "Name_Cosign";
    public final static String Phone_COLUMN = "Phone_Number";
    public final static String Title_COLUMN = "Album_Title";
    public final static String Artist_COLUMN = "Artist";
    public final static String Date_Received_COLUMN = "Album_Received";
    public final static String Selling_Price_COLUMN = "Album_Price";

    public final static String Sold_Date_COLUMN = "Date_Sold";
    public final static String Sold_Price_COLUMN = "Sold_Price";

    // declaring the variables
    public static MusicTableModel musicTableModel;
    public static AlbumTableModel albumTableModel;
    public static ResultSet albumResult = null;

    public static SalesTableModel salesTableModel;
    public static ResultSet salesResult = null;

    public static ResultSet pastQuery = null;
    public static ResultSet pastYearQuery = null;





    public static void main(String[] args) {



        try {
            Class.forName(driver);


            // display a message, if the driver and classpath is not configured correctly
        } catch (ClassNotFoundException cnfe) {

            System.out.println("Driver class is not available ; " +
                    "Please check you have drivers and classpath configured correctly?");
            cnfe.printStackTrace();

            // Doesn't have a driver so.. quit the code
            System.exit(-1);


        }



        try {

            // Making a connection to database, with the information stored on variables for
            // connection, USER and PASSWORD
            conn = DriverManager.getConnection(connection, USER, PASSWORD);

            database = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            query = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);


            // Creating three new tables in a database. The tables name are consignor, album and sales. Each table has a primary key
            // and different data type of data that can be stored in a different column of the table
            String Consignor = "CREATE TABLE if NOT EXISTS consignor (ConsignorID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Name_Cosign varchar(50), Phone_Number int, Money_owed double)" ;
            database.executeUpdate(Consignor);
            String Album = "CREATE TABLE if NOT EXISTS album (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, ConsignorID int, Album_Title varchar(55), Artist varchar(55), Album_Price double, Album_Received date, FOREIGN KEY (ConsignorID) REFERENCES consignor (ConsignorID)) ";
            database.executeUpdate(Album);
            String salesTable = "CREATE TABLE if NOT EXISTS sales (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, ConsignorID int, AlbumID int, Date_Sold date, Sold_Price double, Money_Cosign double," +
                    "Money_Owner double, FOREIGN KEY (ConsignorID) REFERENCES consignor (ConsignorID)) ";
            database.executeUpdate(salesTable);



            loadAllMusic();
            AlbumInfo gui = new AlbumInfo(musicTableModel);
        }
        catch (SQLException se) {
            se.printStackTrace();

            // catching any other kind of exception
        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    // method for disconnecting the connection for database
    public static void shutdown(){

            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("Result set is closed");
                }

                // catching any SQL exception
            } catch (SQLException se){
                    se.printStackTrace();
            }

            try{
                if(database != null){
                    database.close();
                    System.out.println("Statement is closed");
                }
            }catch(SQLException se){
                // Closing the connection can throw Exception also
                se.printStackTrace();
            }


            try {
                if (conn != null) {

                    // closing connection for database
                    conn.close();


                    System.out.println("There is no connection to database");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            System.out.println("The End");

        }



    // using HashMap called " hash" to store the data for ConsignorID and name from the consignor table in database.
    public static HashMap getInfo (){
        HashMap hash = new HashMap();
        try {
            String addData = "Select ConsignorID, Name_Cosign  FROM consignor ";
            test = query.executeQuery(addData);

            // using while loop to go thru every ConsignorID and Name and store the data in " hash"
            while (test.next()){
                int id = test.getInt("ConsignorID");
                String name = test.getString("Name_Cosign");
                hash.put(id, name);
            }

        }
        catch (SQLException sqle){
            System.out.println(sqle);
        }
        return hash;
    }



    // Filling the the result set with a data in a consignor table,
    public static void loadAllMusic(){

        try{

            if (rs!=null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM consignor";
            rs = database.executeQuery(getAllData);

            if (musicTableModel == null) {
                //If no current musicTableModel exists, then make one
                musicTableModel = new MusicTableModel(rs);
            } else {
                //Or, if one already exists, update its ResultSet
                musicTableModel.updateResultSet(rs);
            }

        } catch (Exception e) {
            System.out.println("Error loading or reloading Consignor database Information");
            System.out.println(e);
        }

    }


    // Filling the album table with data
    public static void loadAllAlbum(){

        try{

            String getAllData = "SELECT * FROM album";
            albumResult = database.executeQuery(getAllData);

            if (albumTableModel == null) {
                //If no current albumTableModel, then make one
                albumTableModel = new AlbumTableModel(albumResult);
            } else {
                //Or, if one already exists, update its ResultSet
                albumTableModel.updateResultSet(albumResult);
            }

        } catch (Exception e) {
            System.out.println("Error loading or reloading Album database Information");
            System.out.println(e);
        }

    }


    // loading or filling sales table with data
    public static void loadAllSales(){

        try{


            String getAllData = "SELECT * FROM sales";
            salesResult = database.executeQuery(getAllData);

            if (salesTableModel == null) {
                //If no current salesTableModel, then make one
                salesTableModel = new SalesTableModel(salesResult);
            } else {
                //Or, if one already exists, update its ResultSet
                salesTableModel.updateResultSet(salesResult);
            }

        } catch (Exception e) {
            System.out.println("Error loading or reloading sales database Information");
            System.out.println(e);
        }

    }




    // checking if any of the album is past 30 days from the database
    // table of consignor and album, if any of the album is past 30 days
    // get the consignor's name and album title and display from a dialog message box and notify the owner
    // Also update the album price to a $1
    public static ArrayList<String> checkThirtyDays() {

        ArrayList<String> oldAlbum = new ArrayList<>();

        try {

            String thirtyDay = "Select Album_Title, Name_Cosign FROM consignor, album WHERE consignor.ConsignorID = album.ConsignorID AND Album_Received < CURDATE() - 30";
            pastQuery = query.executeQuery(thirtyDay);


            while (pastQuery.next()){
                String title = pastQuery.getString("Album_Title");
                String name = pastQuery.getString("Name_Cosign");
                String message = "Please inform " + name + " that the album " + title + " is past 30 days and should be picked up!";
                oldAlbum.add(message);

            }

        }
        catch (SQLException ex){
            System.out.println("There were issue with connecting to a SQL database or other SQL issue with retrieving the data" + ex);
            }
        return oldAlbum;
        }


    public static ArrayList<String> checkYear() {

        ArrayList<String> yearOldAlbum = new ArrayList<>();

        try {

            String Year = "Select Album_Title, Name_Cosign FROM consignor, album WHERE consignor.ConsignorID = album.ConsignorID AND Album_Received < CURDATE() - 365 ";
            pastYearQuery = query.executeQuery(Year);


            while (pastYearQuery.next()){
                String title = pastYearQuery.getString("Album_Title");
                String name = pastYearQuery.getString("Name_Cosign");
                String message = "The consignor name -  " + name + " ,with the album title -  " + title + " is past year old and should be bargain price for $1 !";
                yearOldAlbum.add(message);

            }

            //TODO... still need work
            String update = "UPDATE album SET Album_Price = 1 WHERE Album_Received < (CURDATE() - 365)";
            query.executeUpdate(update);

        }
        catch (SQLException ex){
            System.out.println("There were issue with connecting to a SQL database or other SQL issue with retrieving the data" + ex);
        }
        return yearOldAlbum;
    }


}


