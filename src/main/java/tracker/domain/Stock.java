package tracker.domain;

public class Stock {
    private String ticker;
    private int units;
    private int price;

    public Stock(String ticker, int units, int price) {
        this.ticker = ticker;
        this.units = units;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public int getUnits() {
        return units;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "ticker='" + ticker + '\'' +
                ", units=" + units +
                String.format(", price=%.2f", price/100.0) +
                '}';
    }
}
