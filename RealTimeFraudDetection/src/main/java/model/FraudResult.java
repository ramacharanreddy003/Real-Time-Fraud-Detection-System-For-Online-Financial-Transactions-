package model;

public class FraudResult {

    private int riskScore;
    private String reason;

    public FraudResult(int riskScore, String reason) {
        this.riskScore = riskScore;
        this.reason = reason;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getReason() {
        return reason;
    }
}
