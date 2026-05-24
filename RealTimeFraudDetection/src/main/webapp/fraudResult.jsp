<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Status</title>

    <!-- Google Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- CSS -->
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="payment-page">

<%
    String status = (String) request.getAttribute("status");
    boolean approved = "APPROVED".equals(status);
%>

    <!-- Animated Icon -->
    <div class="status-icon <%= approved ? "success" : "failed" %>">
        <span class="material-icons">
            <%= approved ? "check_circle" : "cancel" %>
        </span>
    </div>

    <!-- Text -->
    <h2>
        <%= approved ? "Payment Successful" : "Payment Blocked" %>
    </h2>

    <p class="status-msg">
        <%= approved
            ? "Amount sent successfully."
            : "Transaction blocked due to suspicious activity." %>
    </p>

    <small class="risk-note">
        Fraud detection analyzed amount, device, and location.
    </small>

    <!-- Action -->
    <a href="DashboardServlet" class="btn primary">
        Back to Wallet
    </a>

</div>

</body>
</html>
