package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.WalletService;

import java.io.IOException;

public class DashboardServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        WalletService walletService = new WalletService();
        double balance = walletService.getBalance(user.getUserId());

        req.setAttribute("balance", balance);
        req.getRequestDispatcher("dashboard.jsp").forward(req, res);
    }
}
