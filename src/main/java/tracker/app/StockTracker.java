package tracker.app;

import tracker.service.PricingService;
import tracker.domain.Stock;
import tracker.service.AlphavDataService;
import tracker.features.portfoliomgt.ManagePortfolio;
import tracker.storage.Persistence;
import tracker.storage.SqlLiteStorage;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

public class StockTracker {

    public static void main(String[] args) {
        PricingService pricing = new AlphavDataService();
        Persistence persistence = new SqlLiteStorage();
        ManagePortfolio portfolio = new ManagePortfolio(persistence, pricing);

        loadStocks(persistence, portfolio);

        printStatement(portfolio);
        doCommandLoop(portfolio, persistence);
    }

    private static void loadStocks(Persistence persistence, ManagePortfolio portfolio) {
        Map<String, Integer> stocks = persistence.getAllStocks();
        for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
            try {
                portfolio.load(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                printCommandError("Unable to retrieve stock data; may have exceeded API request limit.");
                System.exit(0);
             }
        }
    }

    private static void printStatement(ManagePortfolio portfolio) {
        List<Stock> listing = portfolio.getListing();
        if (listing.size() < 1) {
            System.out.println("\nPORTFOLIO IS EMPTY");
            return;
        }

        System.out.println("\nCURRENT PORTFOLIO");
        for (Stock stock : portfolio.getListing()) {
            System.out.println(stock);
        }
        System.out.println("Value of portfolio: " + String.format("%.2f", portfolio.getValuation()) + "\n");
    }

    private static void doCommandLoop(ManagePortfolio portfolio, Persistence persistence) {
        Scanner sc = new Scanner(System.in);
        boolean exiting = false;
        while(!exiting) {
            System.out.println("Select menu number: (1) Add Stock, (2) Delete Stock, (3) Exit");
            int option = 0;
            try {
                option = sc.nextInt();
                sc.nextLine();
                if (option < 1 || option > 3) {
                    printCommandError();
                } else if (option == 3) {
                    System.out.println("");
                    exiting = true;
                } else {
                    boolean complete = doCommand(option, sc, portfolio);
                    if (complete) printStatement(portfolio);
                }
            } catch(InputMismatchException e) {
                printCommandError();
                sc.next();
            }
        }
    }

    private static boolean doCommand(int command, Scanner sc, ManagePortfolio portfolio) {
        if (command == 1) { // add stock to portfolio
            return addStock(sc, portfolio);
        } else if (command == 2) { // delete stock from portfolio
            return deleteStock(sc, portfolio);
        }
        return false;
    }

    private static boolean addStock(Scanner sc, ManagePortfolio portfolio) {
        System.out.println("Enter ticker of stock to add");
        String ticker = sc.nextLine().toUpperCase();
        int units = 0;
        try {
            System.out.println("Enter number of units to add");
            units = sc.nextInt();
            sc.nextLine();
            if (units < 1) {
                printCommandError("Units must be more than 0.");
                return false;
            }
        } catch (InputMismatchException e) {
            printCommandError();
            sc.next();
            return false;
        }

        try {
            portfolio.add(ticker, units);
            return true;
        } catch (IllegalArgumentException e){
            printCommandError("Ticker not found");
        } catch (IOException e) {
            printCommandError("Unable to retrieve stock data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteStock(Scanner sc, ManagePortfolio portfolio) {
        System.out.println("Enter ticker of stock to delete");
        String ticker = sc.nextLine().toUpperCase();
        portfolio.delete(ticker);
        return true;
    }

    private static void printCommandError() {
        printCommandError("Input not recognised. Please try again.");
    }

    private static void printCommandError(String msg) {
        System.out.println(msg + "\n");
    }

}
