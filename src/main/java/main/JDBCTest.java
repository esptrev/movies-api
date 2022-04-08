package main;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new Driver());
        DriverManager.registerDriver(new Driver());
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + Config.DB_Host + ":3306/trevor?allowPublicKeyRetrieval=true&useSSL=false",
                Config.DB_User,
                Config.DB_PW
        );
        Statement st = connection.createStatement();
        ResultSet carRows = st.executeQuery("select vin, year, mileage from cars");
        /// iterates over rows and prints fields for each
        while (carRows.next()) {
            System.out.printf("%s ", carRows.getString("vin"));
            System.out.printf("%s ", carRows.getInt("year"));
            System.out.println(carRows.getInt("mileage"));
        }

        carRows = st.executeQuery("select count(id) from cars");
        carRows.next();
        System.out.println("row count via count function " + carRows.getInt("count(id)"));



        Statement carST = connection.createStatement();
        carST.executeUpdate("insert into cars(vin,year,mileage) values('aaaaaab',2022,10005)");

        String newVin = "asdfskd541";
        int newYear = 2023;
        int newMileage = 1025;

        ////PARAMATERIZED QUERY === PREPARED STATEMENTS////
        PreparedStatement ps = connection.prepareStatement("insert into cars (vin,year,mileage) values(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, newVin);
        ps.setInt(2,newYear);
        ps.setInt(3,newMileage);
        ps.executeUpdate();

        ResultSet newKeys = ps.getGeneratedKeys();
        newKeys.next();
        int newID = newKeys.getInt(1);
        System.out.println("new record id is " + newID);

        ps = connection.prepareStatement("update cars set year = ? where id = ?");
        ps.setInt(1,newYear);
        ps.setInt(2,newID);
        ps.executeUpdate();


        ps.close();
        newKeys.close();
        carRows.close();
        st.close();
        connection.close();
    }/////END OF MAIN
}/////END OF CLASS
