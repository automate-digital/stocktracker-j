package tracker.features.portfoliomgt;

import tracker.domain.Portfolio;
import tracker.domain.PricingService;
import tracker.domain.Stock;
import tracker.domain.Valuation;

import java.util.List;

public class ManagePortfolio {
    PricingService pricing;
    Portfolio portfolio;

    public ManagePortfolio(PricingService pricing) {
        this.pricing = pricing;
        this.portfolio = new Portfolio();
    }

    public void add(String ticker, int units) {
        portfolio.add(getStock(ticker, units));
    }

    public double getValuation() {
        Valuation valuation = new Valuation(pricing);
        return valuation.getValue(portfolio);
    }

    public List<Stock> getListing() {
        return portfolio.getStocks();
    }

    private Stock getStock(String ticker, int units) {
        int price = pricing.getPrice(ticker);
        return new Stock(ticker, units, price);
    }

}
