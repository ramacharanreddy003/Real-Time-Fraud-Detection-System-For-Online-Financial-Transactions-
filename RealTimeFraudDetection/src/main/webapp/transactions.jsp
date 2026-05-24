<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Transaction" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Transaction> transactions =
        (List<Transaction>) request.getAttribute("transactions");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Transaction History</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="app">

    <!-- Header -->
    <h2>Transaction History</h2>
    <p class="subtitle">Recent wallet activity</p>

    <!-- Transaction List -->
    <div class="txn-list">

    <%
        if (transactions != null && !transactions.isEmpty()) {
            for (Transaction tx : transactions) {

                boolean sent = tx.getUserId() == user.getUserId();
                String location =
                    (tx.getLocation() != null && !tx.getLocation().isEmpty())
                        ? tx.getLocation()
                        : "Location unavailable";
    %>

        <div class="txn-card">

            <div class="txn-left">
                <span class="txn-type <%= sent ? "sent" : "received" %>">
                    <%= sent ? "Sent to" : "Received from" %>
                </span>

                <span class="txn-name">
                    <%= sent ? tx.getReceiverName() : tx.getSenderName() %>
                </span>

                <!-- 📍 Location -->
                <span class="txn-location">
                    <%= location %>
                </span>

                <span class="txn-date">
                    <%= tx.getTransactionTime() %>
                </span>
            </div>

            <div class="txn-right">
                <span class="txn-amount <%= sent ? "minus" : "plus" %>">
                    <%= sent ? "-" : "+" %>Rs<%= tx.getAmount() %>
                </span>

                <span class="txn-status <%= tx.getStatus() %>">
                    <%= tx.getStatus() %>
                </span>
            </div>

        </div>

    <%
            }
        } else {
    %>
        <p style="text-align:center; margin-top:40px;">
            No transactions yet
        </p>
    <%
        }
    %>

    </div>

</div>

</body>
</html>
