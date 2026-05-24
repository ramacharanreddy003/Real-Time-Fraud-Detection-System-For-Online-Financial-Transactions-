<!DOCTYPE html>
<html>
<head>
    <title> Register</title>
    <link rel="stylesheet" href="style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<div class="container">
    <h2>
        <span class="material-icons">person_add</span>
        Create Account
    </h2>

    <form action="RegisterServlet" method="post">
        <label>Username</label>
        <input type="text" name="username" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Mobile</label>
        <input type="text" name="mobile" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">
            <span class="material-icons">how_to_reg</span> Register
        </button>
    </form>

    <p style="text-align:center;">
        Already registered? <a href="login.jsp">Login</a>
    </p>
</div>

</body>
</html>
