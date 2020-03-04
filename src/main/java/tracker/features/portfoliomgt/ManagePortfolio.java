package tracker.features.portfoliomgt;

import tracker.domain.Portfolio;
import tracker.domain.Stock;
import tracker.service.PricingService;

import java.util.List;

public class ManagePortfolio {
    PricingService pricingService;
    Portfolio portfolio;

    public ManagePortfolio(PricingService pricing) {
        this.pricingService = pricing;
        this.portfolio = new Portfolio();
    }

    public void add(String ticker, int units) throws Exception {
        portfolio.add(getStock(ticker, units));
    }

    public void delete(String ticker) {
        portfolio.delete(ticker);
    }

    public double getValuation() {
        return portfolio.getValue();
    }

    public List<Stock> getListing() {
        return portfolio.getStocks();
    }

    private Stock getStock(String ticker, int units) throws Exception {
        int price = pricingService.getPrice(ticker);
        return new Stock(ticker, units, price);
    }
}
