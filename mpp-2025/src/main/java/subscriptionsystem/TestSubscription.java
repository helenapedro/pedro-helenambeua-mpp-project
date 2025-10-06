package subscriptionsystem;

import java.time.LocalDate;

public class TestSubscription {
    public static void main(String[] args) {
        Customer c = new Customer("Helena", 101);

        c.addService(new Daily(5.0, LocalDate.now().minusDays(3)));    // 3 days * $5 = 15
        c.addService(new Monthly(2.0, LocalDate.now().minusDays(45))); // 45 days = 2 months * (30*2) = 120

        System.out.println(c);
    }
}

