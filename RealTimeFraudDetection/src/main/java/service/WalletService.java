package service;

import dao.WalletDAO;

public class WalletService {

    private final WalletDAO walletDAO = new WalletDAO();

    public double getBalance(int userId) {
        try {
            // 🔑 IMPORTANT LINE
            walletDAO.ensureWalletExists(userId);
            return walletDAO.getBalance(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addBalance(int userId, double amount) {
        try {
            walletDAO.addBalance(userId, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deductBalance(int userId, double amount) {
        try {
            walletDAO.deductBalance(userId, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
