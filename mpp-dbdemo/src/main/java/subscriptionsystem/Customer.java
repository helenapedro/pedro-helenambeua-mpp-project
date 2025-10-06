package subscriptionsystem;

import java.util.*;

public class Customer {
    private String name;
    private int id;
    private List<Service> services = new ArrayList<>();

    public Customer(String name, int id) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name required");
        this.name = name;
        this.id = id;
    }

    public void addService(Service s) {
        if (s == null) throw new IllegalArgumentException("service cannot be null");
        services.add(s);
    }

    public void removeService(Service s) {
        services.remove(s);
    }

    public double totalFee() {
        return services.stream()
                .mapToDouble(Service::calcFee)
                .sum();
    }

    // helper to list services
    public List<Service> getServices() {
        return Collections.unmodifiableList(services);
    }

    @Override
    public String toString() {
        return String.format("Customer[%s, id=%d, totalFee=%.2f]", name, id, totalFee());
    }
}

