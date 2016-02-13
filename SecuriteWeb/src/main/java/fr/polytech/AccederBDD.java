package fr.polytech;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.InvalidAttributeValueException;

public class AccederBDD {
	Connection conn = null;
	String dbpath = null;
	
	public AccederBDD() {
		super();
		this.dbpath = "jdbc:h2:~/test2";
	}
	
	public AccederBDD(String dbpath) {
		super();
		this.dbpath = dbpath;
	}
	
	private void Connection() throws Exception{
		if(conn != null){
			throw new Exception("D¨¦j¨¤ connect¨¦");
		}
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection(dbpath, "sa", "");
	}
	
	private void Deconnection() throws SQLException{
		if(conn!=null){
			conn.close();
			conn=null;
		}	
	}

	public String ajouterCarte(CarteBanquaire carte) throws InvalidAttributeValueException {
		String resultat = "";
		String nombreCard = org.apache.commons.codec.digest.DigestUtils.sha256Hex(carte.getNombreCarte());
		if (carte.equals(null)) {
			resultat = "information error!";
			// throw new
			// InvalidAttributeValueException("InvalidAttributeValueException!");
		} else {
			if (carte.getNombreCarte() == null || carte.getNom() == null || carte.getDate() == null
					|| carte.getSolde() < 0 || carte.getNombreCarte().indexOf(" ") != -1) {
				resultat = "Error input!";
				// throw new
				// InvalidAttributeValueException("InvalidAttributeValueException!");

			} else {
				try {
					Connection();
					// add application code here
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM CARDS WHERE ID_CARD='" + nombreCard + "'");
					if (rs.next()) {
						resultat = "Card nomber existe!";
					} else {

						stmt.execute("INSERT INTO CARDS VALUES('" + nombreCard + "','" + carte.getNom() + "','"
								+ carte.getDate() + "'," + carte.getSolde() + ")");
						resultat = "Congratulations, " + carte.getNom() + "! Enregistrement success!";

					}

					// conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						Deconnection();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}

		return resultat;

	}

	public void supprimerCarte(CarteBanquaire carte) throws InvalidAttributeValueException {
		String nombreCard = org.apache.commons.codec.digest.DigestUtils.sha256Hex(carte.getNombreCarte());
		if (carte.equals(null)) {

			// throw new
			// InvalidAttributeValueException("InvalidAttributeValueException!");
		} else {

			try {
				Connection();
				// add application code here
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM CARDS WHERE ID_CARD='" + nombreCard + "'");

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Deconnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}

	public String payement(String nombreCarte, double amount) throws InvalidAttributeValueException {
		String resultat = "";
		String nomberCard = org.apache.commons.codec.digest.DigestUtils.sha256Hex(nombreCarte);
		String name = "";
		Date date_expriration = null;
		Date date = new Date(System.currentTimeMillis());

		if (nombreCarte == null || amount < 0 || nombreCarte.indexOf(" ") != -1) {
			resultat = "Error input!";
			// throw new
			// InvalidAttributeValueException("InvalidAttributeValueException!");
		} else {
			try {
				Connection();
				// add application code here
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM CARDS WHERE ID_CARD='" + nomberCard + "'");
				if (rs.next()) {
					name = rs.getString("NAME_USER");
					date_expriration = rs.getDate("DATE_EXPRIRATION");

					if (date.before(date_expriration)) {
						double tmpAmount = rs.getDouble("SOLDE");
						if (tmpAmount >= amount) {
							stmt.executeUpdate("UPDATE CARDS SET SOLDE=" + (tmpAmount - amount) + " WHERE ID_CARD='"
									+ nomberCard + "'");
							resultat = "Congratulations, you have payed " + amount + " euros!";
						} else {
							resultat = "Account balance is insufficient!";
						}

					} else {
						resultat = name + ", Your card expired!";
					}

				} else {
					resultat = "Card Nomber is not existe!";
				}
				// conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					Deconnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return resultat;

	}

	public void creerTable() {

		try {
			Connection();
			// add application code here
			// Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE CARDS(ID_CARD VARCHAR(255) PRIMARY KEY,NAME_USER VARCHAR(255),DATE_EXPRIRATION DATE,SOLDE DOUBLE)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				Deconnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void supprimerTable() {

		try {
			Connection();
			// add application code here
			// Statement stmt = conn.createStatement();
			String sql = "drop table CARDS";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
