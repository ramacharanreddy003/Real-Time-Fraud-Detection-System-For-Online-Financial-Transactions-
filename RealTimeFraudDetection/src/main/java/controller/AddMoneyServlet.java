package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.WalletService;

import java.io.IOException;

public class AddMoneyServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        double amount = Double.parseDouble(req.getParameter("amount"));

        WalletService walletService = new WalletService();
        walletService.addBalance(user.getUserId(), amount);

        res.sendRedirect("DashboardServlet");
    }
}
