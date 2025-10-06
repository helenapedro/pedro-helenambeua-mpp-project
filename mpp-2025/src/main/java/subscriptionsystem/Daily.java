package subscriptionsystem;

import java.time.LocalDate;

public final class Daily extends Service {
    public Daily(double dailyPrice, LocalDate subscribedOn) {
        super(dailyPrice, subscribedOn);
    }

    @Override
    public double calcFee() {
        return getDailyPrice() * dateDifference();
    }
}

