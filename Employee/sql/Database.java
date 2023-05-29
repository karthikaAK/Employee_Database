package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Database {
     //Declaration of the DBConnection
    static Connection connection = null;
    static String databaseName = "employeedatabase";
    static String url = "jdbc:mysql://localhost:3306/" + databaseName;

    static String username = "root";
    static String password = "Karthi@123";


    public static void main(String[] args) {
    	//JDBC Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); //newInstance - class and constructor class is used to create a new instance of the class
            connection = DriverManager.getConnection(url, username, password);
            //creates a new Scanner object for reading user input
            Scanner input = new Scanner(System.in);

            boolean exit = false;
            while (!exit) {
            	System.out.println("-----------------------------------");
            	System.out.println("_______*** Welcome ***________");
            	System.out.println("-----------------------------------");
            	System.out.println();
                System.out.println("Select an option:");
                System.out.println();
                System.out.println("1. Add an employee");
                System.out.println("2. Search an employee by name");
                System.out.println("3. Display all employees");
                System.out.println("4. Delete an employee by ID");
                System.out.println("5. Exit");
                System.out.println();

                int option = input.nextInt();

                switch (option) {
                    //Add employee
                    case 1:
                        System.out.println("Enter employee details:");
                        System.out.print("ID: ");
                        int id = input.nextInt();
                        System.out.print("Name: ");
                        String name = input.next();
                        System.out.print("Designation: ");
                        String designation = input.next();
                        System.out.print("Salary: ");
                        int salary = input.nextInt();

                        //PreparedStatement = It is execute the sql db
                        PreparedStatement psInsert = connection.prepareStatement(
                                "INSERT INTO `Employee`(`EmployeeId`,`EmployeeName`, `Designation`, `Salary`) VALUES (?, ?, ?, ?)");
                        psInsert.clearParameters();
                        psInsert.setInt(1, id);
                        psInsert.setString(2, name);
                        psInsert.setString(3, designation);
                        psInsert.setInt(4, salary);
                        int insertStatus = psInsert.executeUpdate();

                        if (insertStatus != 0) {
                            System.out.println("Record was inserted");
                        }
                      
                        break;

                    case 2://Search employee
                        System.out.print("Enter employee name to search: ");
                        String searchName = input.next();

                        PreparedStatement psSearch = connection.prepareStatement(
                                "SELECT * FROM `Employee` WHERE `EmployeeName` = ?");
                        psSearch.setString(1, searchName);
                        
                       
                        ResultSet rs = psSearch.executeQuery();

                        while (rs.next()) {
                            String empName = rs.getString("EmployeeName");
                            String empDesignation = rs.getString("Designation");
                            int empSalary = rs.getInt("Salary");
                            System.out.println(empName + ", " + empDesignation + ", " + empSalary);
                        }
                        break;

                    case 3:
                    	//Display all the employees
                        PreparedStatement psDisplay = connection.prepareStatement("SELECT * FROM `Employee`");
                        ResultSet displayRs = psDisplay.executeQuery();
                        System.out.println("Employee Details:");

                        while (displayRs.next()) {
                        	
                            int empId = displayRs.getInt("EmployeeId");
                            String empName = displayRs.getString("EmployeeName");
                            String empDesignation = displayRs.getString("Designation");
                            int empSalary = displayRs.getInt("Salary");
                            
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println(empId + "| " + empName + "| " + empDesignation + "| " + empSalary);
                            System.out.println("------------------------------------------------------------------");

                        }
                        break;

                    case 4:
                    	//Delete the employee
                        System.out.print("Enter employee ID to delete: ");
                        int deleteId = input.nextInt();

                        PreparedStatement psDelete = connection
                                .prepareStatement("DELETE FROM `Employee` WHERE `EmployeeID` = ?");
                        psDelete.setInt(1, deleteId);
                        int deleteStatus = psDelete.executeUpdate();

                        if (deleteStatus != 0) {
                            System.out.println("Deleted Successfully");
                        }
                        break;
                    case 5:
                    	System.out.println("Have a great Day!...");
                    	System.exit(0);
                    	//It may occur when we put wrong statements
                    	default:
                    	System.out.println("Enter the valid option");
                    	break;
                    	
                    	
                }
                
            }while(true);

        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			System.err.println("Error: " + e.getMessage());
        }
    }
}

            