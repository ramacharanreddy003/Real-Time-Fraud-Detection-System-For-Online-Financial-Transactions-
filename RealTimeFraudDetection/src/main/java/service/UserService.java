package service;

import dao.UserDAO;
import dao.WalletDAO;
import model.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final WalletDAO walletDAO = new WalletDAO();

    // Register new user
    public boolean register(User user) {
        try {
            int userId = userDAO.register(user);

            if (userId > 0) {
                // Create wallet for new user
                walletDAO.createWallet(userId);
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login
    public User login(String username, String password) {
        try {
            return userDAO.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
