package tracker.storage;

import java.util.Map;

public interface Persistence {
    public static final String STOCKS_TABLE_NAME = "stocks";

    boolean open();

    void close();

    void executeQuery(String sql);

    Map<String, Integer> getAllStocks();

}
