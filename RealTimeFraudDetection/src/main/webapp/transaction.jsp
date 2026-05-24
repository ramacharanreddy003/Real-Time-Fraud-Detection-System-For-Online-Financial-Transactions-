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
    <title>Send Payment</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="container">

    <!-- Header -->
    <h2>Send Money</h2>
    <p class="subtitle">
        Payments are protected by real-time fraud detection
    </p>

    <!-- Sender Info -->
    <div class="sender-box">
        <span class="sender-label">From</span>
        <span class="sender-name"><%= user.getUsername() %></span>
    </div>

    <!-- Send Money Form -->
    <form action="TransactionServlet" method="post">

        <!-- Receiver -->
        <label>Receiver (Mobile / Username)</label>
        <input
            type="text"
            name="receiver"
            placeholder="Enter mobile number or username"
            required
        >

        <!-- Amount -->
        <label>Amount</label>
        <input
            type="number"
            name="amount"
            placeholder="Rs Enter amount"
            min="1"
            required
        >

        <!-- Info -->
        <div class="info-box">
            <strong>Security Checks:</strong>
            <ul>
                <li>Transaction amount</li>
                <li>Device behaviour</li>
                <li>Location pattern</li>
            </ul>
        </div>
        
        <!-- 🔐 HIDDEN LOCATION FIELDS (INSIDE FORM) -->
        <input type="hidden" name="latitude" id="lat">
        <input type="hidden" name="longitude" id="lng">

        <!-- Info -->
       

        <button type="submit" class="btn primary">
            Send Secure Payment
        </button>

    </form>

    <!-- Back -->
    <a href="DashboardServlet" class="btn secondary">
        Back to Dashboard
    </a>
    
    

</div>

<script>
navigator.geolocation.getCurrentPosition(
    function(position) {
        document.getElementById("lat").value = position.coords.latitude;
        document.getElementById("lng").value = position.coords.longitude;
        document.getElementById("payBtn").disabled = false;
    },
    function() {
        alert("Location permission is required for secure payments.");
    }
);
</script>


</body>
</html>
