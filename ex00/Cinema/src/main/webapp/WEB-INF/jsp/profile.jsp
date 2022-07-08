<jsp:useBean id="user" scope="session" type="edu.school21.cinema.models.User"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<h2>Profile</h2>
<a href="${pageContext.request.contextPath}/">Home page</a>
<p>Name: ${user.firstName}</p>
<p>Last name: ${user.lastName}</p>
<p>Phone: ${user.phone}</p>
</body>
</html>
