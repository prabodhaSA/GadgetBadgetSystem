package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class Payment {
	// A common method to connect to the DB
		private Connection connect() {
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");

				// Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gb_paf2021", "root", "kosala");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
		
public String insertPayment(int orderid, float amount) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = " insert into payment(`payID`,`orderID`,`totalAmount`)"+ "values (?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setInt(2, orderid);
				preparedStmt.setFloat(3, amount);
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";
			} catch (Exception e) {
				output = "Error while inserting the payment.";
				System.err.println(e.getMessage());
			}
			return output;
		}

public String readPayments() {
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for reading.";
		}
		// Prepare the html table to be displayed
		output = "<table border='1'><tr><th>PaymentID</th><th>OrderID</th>" 
				+ "<th>Payment Date and Time</th>" + "<th>Total Amount</th></tr>";

		String query = "select * from payment";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		// iterate through the rows in the result set
		while (rs.next()) {
			String payID = Integer.toString(rs.getInt("payID"));
			String orderID = Integer.toString(rs.getInt("orderID"));
			Timestamp datets = rs.getTimestamp("payDateTime");
			String payDate = datets.toString();
			String amount = Float.toString(rs.getFloat("totalAmount"));
			


			// Add into the html table
			output += "<tr><td>" + payID + "</td>";
			output += "<td>" + orderID + "</td>";
			output += "<td>" + payDate + "</td>";
			output += "<td>" + amount + "</td>";

			// buttons
			/*output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
					+ "<td><form method='post' action='items.jsp'>"
					+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
					+ "<input name='itemID' type='hidden' value='" + payID + "'>" + "</form></td></tr>";*/
		}
		con.close();

		// Complete the html table
		output += "</table>";
	} catch (Exception e) {
		output = "Error while reading the payment.";
		System.err.println(e.getMessage());
	}
	return output;
}

public String updatePayment(String pID, String total, String oID) {
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for updating.";
		}
		// create a prepared statement
		String query = "UPDATE payment SET orderID=?,totalAmount=? WHERE payID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		
		preparedStmt.setInt(1, Integer.parseInt(oID));
		preparedStmt.setFloat(2,Float.parseFloat(total));
		preparedStmt.setInt(3, Integer.parseInt(pID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
	} catch (Exception e) {
		output = "Error while updating the payment.";
		System.err.println(e.getMessage());
		}
	return output;
	}

public String deletePayment(String payID) {
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for deleting.";
		}
		// create a prepared statement
		String query = "delete from payment where payID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(payID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Deleted successfully";
	} catch (Exception e) {
		output = "Error while deleting the payment.";
		System.err.println(e.getMessage());
	}
	return output;
}
}
