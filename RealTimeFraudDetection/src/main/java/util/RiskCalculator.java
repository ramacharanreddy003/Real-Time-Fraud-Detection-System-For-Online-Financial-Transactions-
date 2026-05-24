package util;

import dao.TransactionDAO;
import model.Transaction;

public class RiskCalculator {

    private static final TransactionDAO dao = new TransactionDAO();

    public static int calculateRisk(Transaction tx) {

        int risk = 0;
        int userId = tx.getUserId();
        double amount = tx.getAmount();

        try {
            /* 🔥 RULE 1: Amount > 50,000 */
            if (amount > 50000) {
                return 100; // instant block
            }

            /* 🔥 RULE 2: Count 50k transactions today */
            int count50kToday =
                dao.countExactAmountToday(userId, 50000);

            if (amount == 50000 && count50kToday >= 2) {
                return 100;
            }

            /* 🔥 RULE 3: Daily total limit */
            double todayTotal =
                dao.getTodayTotalAmount(userId);

            if (todayTotal + amount > 100000) {
                return 100;
            }

            /* 🔥 RULE 4: Velocity (5 seconds) */
            int txLast5Sec =
                dao.countTransactionsLastSeconds(userId, 10);

            if (txLast5Sec >= 2) {
                return 100; // block
            }

            if (txLast5Sec == 1) {
                risk += 40; // suspicious
            }

            /* 🔥 RULE 5: High amount behavior */
            if (amount >= 30000) {
                risk += 30;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 100; // fail-safe
        }

        return risk;
    }

    public static boolean isFraud(int risk) {
        return risk >= 70;
    }
}
