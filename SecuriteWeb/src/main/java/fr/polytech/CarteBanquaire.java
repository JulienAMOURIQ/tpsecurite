package fr.polytech;

import java.sql.Date;

public class CarteBanquaire {
	private String nombreCarte;
	private String nom;
	private Date date;
	private double solde;
	
	public CarteBanquaire(String nombre, String nom, Date date, Double d){
		this.setDate(date);
		this.setNombreCarte(nombre);
		this.setNom(nom);
		this.setSolde(d);
	}

	public String getNombreCarte() {
		return nombreCarte;
	}

	public void setNombreCarte(String nombreCarte) {
		this.nombreCarte = nombreCarte;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}
		

}
