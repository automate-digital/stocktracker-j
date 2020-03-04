package tracker.app;

import tracker.domain.PricingService;
import tracker.domain.Stock;
import tracker.service.AlphavDataService;
import tracker.features.portfoliomgt.ManagePortfolio;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StockTracker {

    public static void main(String[] args) {
        PricingService pricing = new AlphavDataService();
        ManagePortfolio portfolio = new ManagePortfolio(pricing);
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
            System.out.println("");
        }
        return false;
    }

    private static boolean addStock(Scanner sc, ManagePortfolio portfolio) {
        System.out.println("Enter ticker of stock to add");
        String ticker = sc.nextLine();

        int units = 0;
        try {
            System.out.println("Enter number of units to add");
            units = sc.nextInt();
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
            printCommandError("Problem handling stock data");
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void printStatement(ManagePortfolio portfolio) {
        List<Stock> listing = portfolio.getListing();
        if (listing.size() < 1) return;

        System.out.println("\nCURRENT PORTFOLIO");
        for (Stock stock : portfolio.getListing()) {
            System.out.println(stock);
        }
        System.out.println("Value of portfolio: " + String.format(", price=%.2f", portfolio.getValuation()) + "\n");
    }

    private static void printCommandError() {
        printCommandError("Input not recognised. Please try again.");
    }

    private static void printCommandError(String msg) {
        System.out.println(msg + "\n");
    }

}
