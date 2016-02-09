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
		String nomberCard = org.apache.commons.codec.digest.DigestUtils.sha256Hex(nomber);
		Date date_expriration=null;
		Date date=new Date(System.currentTimeMillis());
		//int pwd=Integer.parseInt(password)
		String name = "error";
		String resultat = "Sorry, Server is busy!";
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			// add application code here
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CARDS WHERE ID_CARD='" + nomberCard + "'");
			if (rs.next()) {
				name = rs.getString("NAME_USER");
				date_expriration=rs.getDate("DATE_EXPRIRATION");
				
				if(date.before(date_expriration)){
					double tmpAmount = rs.getDouble("SOLDE");
					if(tmpAmount<=amount){
						stmt.executeUpdate(
								"UPDATE CARDS SET SOLDE=" + (tmpAmount - amount) + " WHERE ID_CARD='" + nomberCard + "'");
						resultat = "Congratulations, you have payed " + amount + " euros!";
					}else{
						resultat = name+", Account balance is insufficient!";
					}
					
				}else{
					resultat=name+", Your card expired!";
				}
				

			} else {
				resultat = "Card Nomber is not existe!";
			}
			//conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}
		//System.out.print(name);
	%>
	<jsp:forward page="Main.jsp">
		<jsp:param name="user" value="<%=name%>" />
		<jsp:param name="resultat" value="<%=resultat%>" />
	</jsp:forward>

</body>
</html>
