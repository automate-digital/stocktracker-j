package tracker.features.portfoliomgt;

import tracker.domain.Portfolio;
import tracker.domain.PricingService;
import tracker.domain.Stock;
import tracker.domain.Valuation;

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

    public double getValuation() {
        Valuation valuation = new Valuation(pricingService);
        return valuation.getValue(portfolio);
    }

    public List<Stock> getListing() {
        return portfolio.getStocks();
    }

    private Stock getStock(String ticker, int units) throws Exception {
        int price = pricingService.getPrice(ticker);
        return new Stock(ticker, units, price);
    }
}
