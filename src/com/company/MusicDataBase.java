package com.company;

import java.sql.*;

public class MusicDataBase {


    static final String driver = "com.mysql.jdbc.Driver";


    // connection variable for database
    static final String connection = "jdbc:mysql://localhost:3306/javafinal";


    static final String USER = "root";


    static final String PASSWORD = "airskyler&19790201";

    static Statement database = null;
    static Connection conn = null;
    static ResultSet rs = null;


    public final static String Sales_TABLE_NAME = "Sales";
    public final static String Consignor_COLUMN = "Name_Cosign";
    public final static String Phone_COLUMN = "Phone_Number";
    public final static String Title_COLUMN = "Album_Title";
    public final static String Artist_COLUMN = "Artist";
    public final static String Date_Received_COLUMN = "Album_Received";
    public final static String Selling_Price_COLUMN = "Album_Price";

    public final static String Sold_Date_COLUMN = "Sold Date";
    public final static String Sold_Price_COLUMN = "Sold Price";

    public static MusicTableModel musicTableModel;




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


            conn = DriverManager.getConnection(connection, USER, PASSWORD);
            database = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String Consignor = "CREATE TABLE if NOT EXISTS consignor (ConsignorID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Name_Cosign varchar(50), Phone_Number int, Money_owed double)" ;
            database.executeUpdate(Consignor);
            String Album = "CREATE TABLE if NOT EXISTS album (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, ConsignorID int, Album_Title varchar(55), Artist varchar(55), Album_Price double, Album_Received date) ";
            database.executeUpdate(Album);
            String salesTable = "CREATE TABLE if NOT EXISTS sales (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, ConsignorID int, AlbumID int, Date_Sold date, Sold_Price double, Money_Cosign double," +
                    "Money_Owner double, FOREIGN KEY (ConsignorID) REFERENCES consigner (ConsignorID)) ";
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
                // Closing the connection cam throw Exception also
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



    public static void addConsignor (String Name, String Number){
        try {
            String addData = "INSERT INTO consignor (Name_Cosign, Phone_Number) VALUE(?, ?)";
            PreparedStatement prep = conn.prepareStatement(addData);
            prep.setString(1, Name);
            prep.setString(2, Number);
            prep.executeUpdate();
        }
        catch (SQLException sqle){
            System.out.println(sqle);
        }
    }

    public static void loadAllMusic(){

        try{

            if (rs!=null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM consignor";
            rs = database.executeQuery(getAllData);

            if (musicTableModel == null) {
                //If no current movieDataModel, then make one
                musicTableModel = new MusicTableModel(rs);
            } else {
                //Or, if one already exists, update its ResultSet
                musicTableModel.updateResultSet(rs);
            }

        } catch (Exception e) {
            System.out.println("Error loading or reloading music");
            System.out.println(e);
        }

    }


    public static void loadAllAlbum(){

        try{

            if (rs!=null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM album";
            rs = database.executeQuery(getAllData);

            if (musicTableModel == null) {
                //If no current movieDataModel, then make one
                musicTableModel = new MusicTableModel(rs);
            } else {
                //Or, if one already exists, update its ResultSet
                musicTableModel.updateResultSet(rs);
            }

        } catch (Exception e) {
            System.out.println("Error loading or reloading music");
            System.out.println(e);
        }

    }

}


