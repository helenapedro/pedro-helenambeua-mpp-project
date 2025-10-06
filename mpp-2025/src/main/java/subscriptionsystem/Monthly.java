package subscriptionsystem;

import java.time.LocalDate;

public final class Monthly extends Service {
    public Monthly(double dailyPrice, LocalDate subscribedOn) {
        super(dailyPrice, subscribedOn);
    }

//    @Override
//    public double calcFee() {
//        return getDailyPrice() * 30;
//    }

    @Override
    public double calcFee() {
        int days = dateDifference();
        int months = (int) Math.ceil(days / 30.0);   // round up partial months
        return getDailyPrice() * 30 * months;
    }
}

