<!DOCTYPE html>
<html>
<head>
    <title> Login</title>
    <link rel="stylesheet" href="style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<div class="container">
    <h2>
        <span class="material-icons">account_circle</span><br>
        Login
    </h2>

    <form action="LoginServlet" method="post">
        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">
            <span class="material-icons">lock</span> Login
        </button>
    </form>

    <p style="text-align:center;">
        New user? <a href="register.jsp">Create account</a>
    </p>
</div>

</body>
</html>
