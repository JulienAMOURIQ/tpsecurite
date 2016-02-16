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

	private String checkCarte(CarteBanquaire carte){
		String resultat = "";
		if (carte.getNombreCarte() == null) {
			resultat = "Error input!";
		}
		if (carte.getNom() == null) {
			resultat = "Error input!";
		}

		if (carte.getDate() == null) {
			resultat = "Error input!";
		}
		if (carte.getSolde() < 0) {
			resultat = "Error input!";
		}
		if (carte.getNombreCarte().indexOf(" ") != -1) {
			resultat = "Error input!";
		}
		return resultat;
	}
	public String ajouterCarte(CarteBanquaire carte) {
		String resultat = checkCarte(carte);
		String nombreCard = org.apache.commons.codec.digest.DigestUtils.sha256Hex(carte.getNombreCarte());
		
		if (resultat != "Error input!") {
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

			} catch (Exception e) {

				System.out.println("Impossible d'acceder ¨¤ la BD.");
				
			} finally {
				try {
					Deconnection();
				} catch (SQLException e) {

					System.out.println("Impossible de se d¨¦connecter de la BD.");
				}

			}
		}

		return resultat;

	}

	public void supprimerCarte(CarteBanquaire carte)  {
		String nombreCard = org.apache.commons.codec.digest.DigestUtils.sha256Hex(carte.getNombreCarte());
		try {
			Connection();
			// add application code here
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM CARDS WHERE ID_CARD='" + nombreCard + "'");

		} catch (Exception e) {

			System.out.println("Impossible d'acceder ¨¤ la BD.");
		} finally {
			try {
				Deconnection();
			} catch (SQLException e) {

				System.out.println("Impossible de se d¨¦connecter de la BD.");
			}

		}

	}

	

	public String payement(String nombreCarte, double amount) throws InvalidAttributeValueException {
		String resultat = "";
		String nomberCard = org.apache.commons.codec.digest.DigestUtils.sha256Hex(nombreCarte);
		String name = "";
		Date dateExpriration = null;
		Date date = new Date(System.currentTimeMillis());

		if (nombreCarte == null || amount < 0 || nombreCarte.indexOf(" ") != -1) {
			resultat = "Error input!";

		} else {
			try {
				Connection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM CARDS WHERE ID_CARD='" + nomberCard + "'");
				if (rs.next()) {
					name = rs.getString("NAME_USER");
					dateExpriration = rs.getDate("DATE_EXPRIRATION");

					if (date.before(dateExpriration)) {
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

			} catch (Exception e) {
				System.out.println("Impossible d'acceder ¨¤ la BD.");
			} finally {
				try {
					Deconnection();
				} catch (SQLException e) {

					System.out.println("Impossible de se d¨¦connecter de la BD.");
				}
			}
		}

		return resultat;

	}

	public void creerTable() {

		try {
			Connection();

			String sql = "CREATE TABLE CARDS(ID_CARD VARCHAR(255) PRIMARY KEY,NAME_USER VARCHAR(255),DATE_EXPRIRATION DATE,SOLDE DOUBLE)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();

		} catch (Exception e) {

			System.out.println("Impossible d'acceder ¨¤ la BD.");
		} finally{
			try {
				Deconnection();
			} catch (SQLException e) {

				System.out.println("Impossible de se d¨¦connecter de la BD.");
			}
		}

	}

	public void supprimerTable() {

		try {
			Connection();

			String sql = "drop table CARDS";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();

		} catch (Exception e) {

			System.out.println("Impossible d'acceder ¨¤ la BD.");
		}
	}

}
