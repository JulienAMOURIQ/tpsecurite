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
		String name = request.getParameter("name");
		Date date = Date.valueOf(request.getParameter("date"));
		System.out.println(request.getParameter("date") + " " + date);
		String resultat = "Sorry, Server is busy!";
		Connection conn = null;
		if ( nomber==null||name==null||date==null || amount < 0 || nomber.indexOf(" ") != -1) {
			resultat = "Error input!";
		} else {
			try {
				Class.forName("org.h2.Driver");
				conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
				// add application code here
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM CARDS WHERE ID_CARD='" + nomberCard + "'");
				if (rs.next()) {
					resultat = "Card nomber existe!";
				} else {

					stmt.execute("INSERT INTO CARDS VALUES('" + nomberCard + "','" + name + "','" + date + "',"
							+ amount + ")");
					resultat = "Congratulations, " + name + "! Enregistrement success!";

				}

				//conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				conn.close();

			}
		}

		//System.out.print(name);
	%>
	<jsp:forward page="Main.jsp">
		<jsp:param name="user" value="<%=name%>" />
		<jsp:param name="resultat" value="<%=resultat%>" />
	</jsp:forward>

</body>
</html>
