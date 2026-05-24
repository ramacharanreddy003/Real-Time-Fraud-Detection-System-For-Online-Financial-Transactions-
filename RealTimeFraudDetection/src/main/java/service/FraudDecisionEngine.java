package service;

public class FraudDecisionEngine {

    public static String decide(int riskScore) {

        if (riskScore < 30) {
            return "APPROVED";
        } else if (riskScore <= 70) {
            return "OTP_REQUIRED";
        } else {
            return "BLOCKED";
        }
    }
}
