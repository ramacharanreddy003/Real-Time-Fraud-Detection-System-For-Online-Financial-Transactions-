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
    <title>Secure Wallet</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="container dashboard">

    <!-- Header -->
    <div class="dashboard-header">
        <h2>Welcome, <%= user.getUsername() %></h2>
        <p class="subtitle">Your secure digital wallet</p>
    </div>

    <!-- Wallet Card -->
    <div class="wallet-card">
        <p class="wallet-label">Available Balance</p>
        <h1 class="wallet-amount">
    Rs <%= request.getAttribute("balance") %>
</h1>


        <div class="trust-row">
            Protected by Real-Time Fraud Detection
        </div>
    </div>

    <!-- Primary Action -->
    <div class="primary-action">
        <a href="transaction.jsp" class="btn primary">
            Make Secure Payment
        </a>
    </div>

    <!-- Secondary Actions (Aligned Row) -->
    <div class="secondary-actions">
        <a href="TransactionsServlet" class="btn secondary">
            Transaction History
        </a>

        <a href="addMoney.jsp" class="btn secondary">
            Add Money
        </a>
    </div>

    <!-- Footer -->
    <div class="dashboard-footer">
        <a href="LogoutServlet" class="logout-link">Logout</a>
    </div>

</div>

</body>
</html>
