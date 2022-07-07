<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>

<h1>Sign Up</h1>
<a href="${pageContext.request.contextPath}/">Home page</a>
<form method="post">
    <div>
        <p>
            <label for="first_name">First name</label>
            <input name="first_name" id="first_name" placeholder="first name" required>
        </p>
    </div>
    <div>
        <p>
            <label for="last_name">Last name</label>
            <input name="last_name" id="last_name" placeholder="last name" required>
        </p>
    </div>
    <div>
        <p>
            <label for="phone">Phone</label>
            <input name="phone" id="phone" placeholder="phone number" required>
        </p>
    </div>
    <div>
        <p>
            <label for="password">Password</label>
            <input name="password" id="password" placeholder="password" required>
        </p>
    </div>
    <div>
        <button>Sign Up</button>
    </div>
</form>

</body>
</html>
