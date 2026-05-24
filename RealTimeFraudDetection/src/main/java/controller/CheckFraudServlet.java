package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import model.Transaction;
import util.DeviceUtil;
import util.RiskCalculator;

public class CheckFraudServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            Transaction tx = new Transaction();

            // Amount
            tx.setAmount(Double.parseDouble(req.getParameter("amount")));

            // Device
            tx.setDevice(DeviceUtil.getDeviceType(req));

            // 🌍 Browser location (LIVE)
            String lat = req.getParameter("latitude");
            String lng = req.getParameter("longitude");

            String location =
                (lat != null && lng != null)
                ? "Lat: " + lat + ", Lng: " + lng
                : "Location unavailable";

            tx.setLocation(location);

            // 🔥 Risk calculation
            int risk = RiskCalculator.calculateRisk(tx);

            String result;
            if (risk < 30) {
                result = "LOW RISK";
            } else if (risk <= 60) {
                result = "MEDIUM RISK";
            } else {
                result = "HIGH RISK";
            }

            // UI data
            req.setAttribute("risk", risk);
            req.setAttribute("result", result);
            req.setAttribute("location", location);

            req.getRequestDispatcher("fraudResult.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("risk", 0);
            req.setAttribute("result", "ERROR");
            req.getRequestDispatcher("fraudResult.jsp").forward(req, res);
        }
    }
}
