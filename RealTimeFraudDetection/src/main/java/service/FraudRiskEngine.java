package service;

import model.FraudResult;
import model.Transaction;

public class FraudRiskEngine {

    public FraudResult calculateRisk(
            Transaction tx,
            double userAvgAmount,
            int txCount1Min,
            int txCount5Min,
            boolean newDevice,
            boolean newLocation,
            boolean unusualTime,
            double mlProbability
    ) {

        int amountRisk = 0;
        int velocityRisk = 0;
        int behaviorRisk = 0;
        int locationRisk = 0;
        int deviceRisk = 0;

        StringBuilder reason = new StringBuilder();

        /* 🔥 Feature 1: Dynamic Amount Anomaly */
        if (tx.getAmount() > 3 * userAvgAmount) {
            amountRisk += 25;
            reason.append("High amount deviation, ");
        }

        /* 🔥 Feature 2: Transaction Velocity */
        if (txCount1Min > 3) {
            velocityRisk += 30;
            reason.append("Multiple rapid transactions, ");
        }

        /* 🔥 Feature 3: User Behavior Profiling */
        if (unusualTime) {
            behaviorRisk += 15;
            reason.append("Unusual transaction time, ");
        }

        /* 🔥 Feature 4: Receiver Location Awareness */
        if (newLocation && tx.getAmount() > userAvgAmount) {
            locationRisk += 20;
            reason.append("New receiver location, ");
        }

        /* 🔥 Feature 5: Device Change */
        if (newDevice) {
            deviceRisk += 20;
            reason.append("New device detected, ");
        }

        /* 🔥 Final Risk Score */
        int finalRisk =
                (int) (mlProbability * 50)
                + amountRisk
                + velocityRisk
                + behaviorRisk
                + locationRisk
                + deviceRisk;

        return new FraudResult(finalRisk, reason.toString());
    }
}
