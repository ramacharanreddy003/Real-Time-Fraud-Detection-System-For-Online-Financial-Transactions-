package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.Transaction;
import model.User;
import service.TransactionService;
import dao.UserDAO;
import util.DeviceUtil;
import util.GeoLocationUtil;

public class TransactionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 🔐 1. Check login
        User sender = (User) req.getSession().getAttribute("user");
        if (sender == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        try {
            // 🔢 2. Read & validate amount
            String amountStr = req.getParameter("amount");
            if (amountStr == null || amountStr.trim().isEmpty()) {
                req.setAttribute("status", "INVALID_AMOUNT");
                req.getRequestDispatcher("fraudResult.jsp").forward(req, res);
                return;
            }

            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                req.setAttribute("status", "INVALID_AMOUNT");
                req.getRequestDispatcher("fraudResult.jsp").forward(req, res);
                return;
            }

            // 👤 3. Read receiver
            String receiverInput = req.getParameter("receiver");
            if (receiverInput == null || receiverInput.trim().isEmpty()) {
                req.setAttribute("status", "INVALID_RECEIVER");
                req.getRequestDispatcher("fraudResult.jsp").forward(req, res);
                return;
            }

            UserDAO userDAO = new UserDAO();
            User receiver = userDAO.findByUsernameOrMobile(receiverInput);

            // 🚫 4. Validate receiver
            if (receiver == null || receiver.getUserId() == sender.getUserId()) {
                req.setAttribute("status", "INVALID_RECEIVER");
                req.getRequestDispatcher("fraudResult.jsp").forward(req, res);
                return;
            }

            // 💳 5. Build transaction
            Transaction tx = new Transaction();
            tx.setUserId(sender.getUserId());      // sender
            tx.setReceiverId(receiver.getUserId()); // receiver
            tx.setAmount(amount);

            // 🖥️ Device detection (safe fallback)
            String device = DeviceUtil.getDeviceType(req);
            tx.setDevice(device != null ? device : "UNKNOWN");

            String lat = req.getParameter("latitude");
            String lng = req.getParameter("longitude");

            String location;
            if (lat != null && lng != null && !lat.isEmpty()) {
                location = GeoLocationUtil.getLocationFromLatLng(lat, lng);
            } else {
                location = GeoLocationUtil.getLocationFromIP(req.getRemoteAddr());
            }

            tx.setLocation(location);


            // 🔍 6. Process transaction (fraud + wallet)
            TransactionService service = new TransactionService();
            String status = service.processTransaction(tx);

            // 📤 7. Show result
            req.setAttribute("status", status);
            req.getRequestDispatcher("fraudResult.jsp").forward(req, res);

        } catch (NumberFormatException e) {
            // ❌ Amount parse error
            req.setAttribute("status", "INVALID_AMOUNT");
            req.getRequestDispatcher("fraudResult.jsp").forward(req, res);

        } catch (Exception e) {
            // ❌ Any unexpected error
            e.printStackTrace();
            req.setAttribute("status", "ERROR");
            req.getRequestDispatcher("fraudResult.jsp").forward(req, res);
        }
    }
}
