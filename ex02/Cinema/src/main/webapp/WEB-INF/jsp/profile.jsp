<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="edu.school21.cinema.models.ImageDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="user" scope="session" type="edu.school21.cinema.models.User"/>

<%
    List list_sign_in = (List) request.getAttribute("list_sign_in");
    List<ImageDTO> list_images = (List<ImageDTO>) request.getAttribute("list_images");

    Collections.reverse(list_sign_in);
    Collections.reverse(list_images);

    String ava = "";
    try {
        ImageDTO imageDTO = list_images.get(0);
        ava = String.valueOf(imageDTO.getId());
    } catch (Exception e) {
    }
%>

<html>

<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>

<body>

    <a href="${pageContext.request.contextPath}/">Home page</a>

    <div class="wrapper">

        <div id="profile" class="container">
            <p>First name: ${user.firstName}</p>
            <p>Last name: ${user.lastName}</p>
            <p>Phone: ${user.phone}</p>
            <br>
        </div>

        <div id="form" class="container">
            <img src="/images/<%=ava%>" alt="no image">
            <form method="post" action="images" enctype="multipart/form-data">
                <input type="file" id="file" name="file" accept="image/*">
                <button>Upload</button>
            </form>
        </div>

        <div id="signin_table" class="container table">
            <table>
                <thead>
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Time</th>
                    <th scope="col">IP</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="<%=list_sign_in%>">
                    <tr>
                        <td>${item.date}</td>
                        <td>${item.time}</td>
                        <td>${item.ip}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div id="image_table" class="container table">
            <table>
                <thead>
                <tr>
                    <th>File name</th>
                    <th>Size</th>
                    <th>MIME</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="<%=list_images%>">
                    <tr>
                        <td><a href="images/${item.id}" target="_blank">${item.name}</a></td>
                        <td>${item.size}</td>
                        <td>${item.mime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>

</body>

</html>
