package tracker.app;

import tracker.domain.PricingService;
import tracker.domain.Stock;
import tracker.service.AlphavDataService;
import tracker.features.portfoliomgt.ManagePortfolio;

public class StockTracker {

    public static void main(String[] args) {
        PricingService pricing = new AlphavDataService();
        ManagePortfolio portfolio = new ManagePortfolio(pricing);

        portfolio.add("AAPL", 10);
        portfolio.add("MSFT", 10);

        for (Stock stock : portfolio.getListing()) {
            System.out.println(stock);
        }

        System.out.println("Value of portfolio: " + portfolio.getValuation());
    }
}
