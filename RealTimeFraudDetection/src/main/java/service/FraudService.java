package service;

import model.Transaction;
import util.RiskCalculator;

public class FraudService {

    public int calculateRisk(Transaction tx) {
        return RiskCalculator.calculateRisk(tx);
    }

    public boolean isFraud(int riskScore) {
        return RiskCalculator.isFraud(riskScore);
    }

    public String getFraudReason(Transaction tx) {

        if (tx.getAmount() > 50000)
            return "High transaction amount";

        if ("NEW".equalsIgnoreCase(tx.getDevice()))
            return "New device detected";

        if ("FOREIGN".equalsIgnoreCase(tx.getLocation()))
            return "Foreign location";

        return "Normal transaction";
    }
}
