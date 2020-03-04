package tracker.domain;

public class Stock {
    private String ticker;
    private int units;

    public Stock(String ticker, int units) {
        this.ticker = ticker;
        this.units = units;
    }

    public String getTicker() {
        return ticker;
    }

    public int getUnits() {
        return units;
    }
}
