package fr.polytech;

import java.sql.Date;

import javax.management.InvalidAttributeValueException;

import org.junit.Test;

import junit.framework.TestCase;

public class AccederBDDTest extends TestCase {
	AccederBDD accederBDD=new AccederBDD();

	protected void setUp() throws Exception {
		super.setUp();
		
	}
	@Test
	public void ajouterCarteTest_cas1(){
		
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498746541", "M. DUPONT", Date.valueOf("2018-05-01"), 50.);
		try {
			accederBDD.ajouterCarte(carteBanquaire);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void ajouterCarteTest_cas2(){
		
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123498746541", "M. DUPONT", Date.valueOf("2018-05-01"), -50.);
		try {
			accederBDD.ajouterCarte(carteBanquaire);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void ajouterCarteTest_cas3(){
		
		CarteBanquaire carteBanquaire=new CarteBanquaire("4970123 498746541", "M. DUPONT", Date.valueOf("2018-05-01"), -50.);
		try {
			accederBDD.ajouterCarte(carteBanquaire);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void payementTesst_cas1(){
		String nombreCarte="4974384998761238";
		double amount=50.;
		try {
			accederBDD.payement(nombreCarte, amount);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void payementTesst_cas2(){
		String nombreCarte="4974384998761111";
		double amount=50.;
		try {
			accederBDD.payement(nombreCarte, amount);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void payementTesst_cas3(){
		String nombreCarte="4974384998761238";
		double amount=-50.;
		try {
			accederBDD.payement(nombreCarte, amount);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
