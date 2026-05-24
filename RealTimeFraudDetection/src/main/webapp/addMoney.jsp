<%@ page session="true" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Money</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="container">

    <h2>Add Money to Wallet</h2>

    <form action="AddMoneyServlet" method="post">

        <label>Amount</label>
        <input type="number" name="amount" placeholder="Enter amount (Rs)" required>

        <button type="submit" class="btn">
            Add Money
        </button>

    </form>

</div>

</body>
</html>
