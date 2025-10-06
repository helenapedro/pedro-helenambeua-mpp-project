package subscriptionsystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Service {
    private final double dailyPrice;
    private final LocalDate subscribedOn;

    protected Service(double dailyPrice, LocalDate subscribedOn) {
        if (dailyPrice < 0) throw new IllegalArgumentException("dailyPrice must be >= 0");
        if (subscribedOn == null) throw new NullPointerException("subscribedOn is required");
        this.dailyPrice = dailyPrice;
        this.subscribedOn = subscribedOn;
    }

    public double getDailyPrice() { return dailyPrice; }
    public LocalDate getSubscribedOn() { return subscribedOn; }

    // days between subscribedOn and today (non-negative)
    protected int dateDifference() {
        long days = ChronoUnit.DAYS.between(subscribedOn, LocalDate.now());
        return (int) Math.max(0, days);
    }

    public abstract double calcFee();
}
