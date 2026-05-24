package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.TransactionService;

import java.io.IOException;


public class TransactionsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public TransactionsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        TransactionService service = new TransactionService();
        req.setAttribute(
            "transactions",
            service.getTransactionsByUser(user.getUserId())
        );

        req.getRequestDispatcher("transactions.jsp").forward(req, res);
    }
}
