<%@page import="fr.polytech.AccederBDD"%>
<%@page import="fr.polytech.CarteBanquaire"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.*,java.sql.Date"%>
<%@ page%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>check the information</title>
</head>
<body>
	<%
		double amount = Double.parseDouble(request.getParameter("amount"));
		String nomber = request.getParameter("nomberCard");

		
		String name = request.getParameter("name");
		Date date = Date.valueOf(request.getParameter("date"));

		String resultat = "Sorry, Server is busy!";
	
		
		CarteBanquaire carte=new CarteBanquaire(nomber,name,date,amount);
		AccederBDD acceder=new AccederBDD();
		resultat=acceder.ajouterCarte(carte);
		

		//System.out.print(name);
	%>
	<jsp:forward page="Main.jsp">
		<jsp:param name="user" value="<%=name%>" />
		<jsp:param name="resultat" value="<%=resultat%>" />
	</jsp:forward>

</body>
</html>
