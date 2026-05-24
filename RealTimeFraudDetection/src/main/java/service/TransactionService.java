package service;

import dao.TransactionDAO;
import dao.FraudAnalysisDAO;
import model.Transaction;
import util.RiskCalculator;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final FraudAnalysisDAO fraudDAO = new FraudAnalysisDAO();
    private final WalletService walletService = new WalletService();

    /* ===============================
       PROCESS TRANSACTION
       =============================== */
    public String processTransaction(Transaction tx) {

        try {
            // 1️⃣ Calculate risk
            int risk = RiskCalculator.calculateRisk(tx);
            boolean fraud = RiskCalculator.isFraud(risk);

            // 2️⃣ Status
            String status = fraud ? "BLOCKED" : "APPROVED";
            tx.setStatus(status);

            // 3️⃣ Save transaction
            int txId = transactionDAO.save(tx);

            // 4️⃣ Save fraud analysis
            String reason;
            if (risk >= 100) {
                reason = "Limit exceeded or rapid transactions detected";
            } else if (risk >= 70) {
                reason = "Suspicious transaction behavior";
            } else {
                reason = "Normal transaction";
            }

            fraudDAO.save(txId, risk, reason);

            // 5️⃣ Wallet update
            if (!fraud) {
                walletService.deductBalance(tx.getUserId(), tx.getAmount());
                walletService.addBalance(tx.getReceiverId(), tx.getAmount());
            }

            return status;

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /* ===============================
       TRANSACTION HISTORY
       =============================== */
    public List<Transaction> getTransactionsByUser(int userId) {

        try {
            return transactionDAO.findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
