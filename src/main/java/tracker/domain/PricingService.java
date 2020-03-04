package tracker.domain;

public interface PricingService {
    int getPrice(String ticker) throws Exception; // Stock price in pence
}
