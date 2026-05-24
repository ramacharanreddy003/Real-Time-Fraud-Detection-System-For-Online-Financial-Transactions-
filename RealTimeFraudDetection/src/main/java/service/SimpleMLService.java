package service;

import model.Transaction;

public class SimpleMLService {

    public double predictProbability(
            Transaction tx,
            boolean newDevice,
            boolean newLocation,
            int velocityCount
    ) {

        double score = 0;

        if (tx.getAmount() > 50000) score += 0.3;
        if (newDevice) score += 0.2;
        if (newLocation) score += 0.2;
        if (velocityCount > 3) score += 0.3;

        // Clamp between 0 and 1
        return Math.min(score, 1.0);
    }
}
