package tracker.app;

import tracker.domain.PricingService;
import tracker.domain.Stock;
import tracker.service.AlphavDataService;
import tracker.features.portfoliomgt.ManagePortfolio;

import java.io.IOException;

public class StockTracker {

    public static void main(String[] args) {
        PricingService pricing = new AlphavDataService();
        ManagePortfolio portfolio = new ManagePortfolio(pricing);

        try {
            portfolio.add("AAPL", 10);
            portfolio.add("MSFT", 10);
        } catch (IOException e) {
            System.out.println("Problem handling stock data");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        printStatement(portfolio);
    }

    private static void printStatement(ManagePortfolio portfolio) {
        System.out.println("CURRENT PORTFOLIO");
        for (Stock stock : portfolio.getListing()) {
            System.out.println(stock);
        }
        System.out.println("Value of portfolio: " + String.format(", price=%.2f", portfolio.getValuation()));
    }
}
