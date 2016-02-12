package fr.polytech;

import static org.junit.Assert.*;

import java.sql.Date;

import javax.management.InvalidAttributeValueException;

import org.junit.Before;
import org.junit.Test;

public class AccederBDDTest {
	AccederBDD accederBDD=new AccederBDD();

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testAjouterCarte_cas1() {
		/* Tous les parametres sont correct */
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498746541", "M. DUPONT", Date.valueOf("2018-05-01"), 50.);
		
		try {
			accederBDD.supprimerCarte(carteBanquaire);
			assertEquals("Congratulations, " + carteBanquaire.getNom() + "! Enregistrement success!", accederBDD.ajouterCarte(carteBanquaire));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testAjouterCarte_cas2(){
		/*le solde est incorrect  */
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498746541", "M. DUPONT", Date.valueOf("2018-05-01"), -50.);
		
		try {
			accederBDD.supprimerCarte(carteBanquaire);
			assertEquals("Error input!", accederBDD.ajouterCarte(carteBanquaire));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testAjouterCarte_cas3(){
		/*le nombre de carte est incorrect  */
		CarteBanquaire carteBanquaire=new CarteBanquaire("49701234 98746541", "M. DUPONT", Date.valueOf("2018-05-01"), 50.);
		
		try {
			accederBDD.supprimerCarte(carteBanquaire);
			assertEquals("Error input!", accederBDD.ajouterCarte(carteBanquaire));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testAjouterCarte_cas4(){
		/*la carte existe  */
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498746541", "M. DUPONT", Date.valueOf("2018-05-01"), 50.);
		
		try {
			accederBDD.ajouterCarte(carteBanquaire);
			assertEquals("Card nomber existe!", accederBDD.ajouterCarte(carteBanquaire));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPayement_cas1() {
		/*tous les parametres sont correct*/
		/*CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498748888", "M. Piano", Date.valueOf("2018-05-01"), 88888.);
		try {
			accederBDD.supprimerCarte(carteBanquaire);
			accederBDD.ajouterCarte(carteBanquaire);
		} catch (InvalidAttributeValueException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		String nomber="4970123498746541";
		double amount=5.;
		try {
			assertEquals("Congratulations, you have payed " + amount + " euros!",accederBDD.payement(nomber, amount));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testPayement_cas2() {
		/*le nombre de la carte est incorrect*/
		String nombreCarte="49701234  98748888";
		double amount=50.;
		try {
			assertEquals("Error input!",accederBDD.payement(nombreCarte, amount));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testPayement_cas3() {
		/*le mountant est incorrect*/
		String nombreCarte="4970123498748888";
		double amount=-50.;
		try {
			assertEquals("Error input!",accederBDD.payement(nombreCarte, amount));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testPayement_cas4() {
		/*la carte expire*/
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498741123", "M. LOUIS", Date.valueOf("2015-05-01"), 50.);
		/*try {
			accederBDD.supprimerCarte(carteBanquaire);
			accederBDD.ajouterCarte(carteBanquaire);
		} catch (InvalidAttributeValueException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		double amount=50.;
		try {
			assertEquals(carteBanquaire.getNom() + ", Your card expired!",accederBDD.payement(carteBanquaire.getNombreCarte(), amount));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testPayement_cas5() {
		/*Account balance is insufficient*/
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498741123", "M. LOUIS", Date.valueOf("2018-05-01"), 50.);
		try {
			accederBDD.supprimerCarte(carteBanquaire);
			accederBDD.ajouterCarte(carteBanquaire);
		} catch (InvalidAttributeValueException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		double amount=250.;
		try {
			assertEquals("Account balance is insufficient!",accederBDD.payement(carteBanquaire.getNombreCarte(), amount));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testPayement_cas6() {
		/*la carte n'existe pas!*/
		String nombreCarte="4970123498748812";
		double amount=50.;
		try {
			assertEquals("Card Nomber is not existe!",accederBDD.payement(nombreCarte, amount));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
