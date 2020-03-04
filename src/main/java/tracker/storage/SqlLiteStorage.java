package tracker.storage;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SqlLiteStorage implements Persistence {
    private static final String DB_CONNECTION_STR_PREFIX = "jdbc:sqlite:";
    private static final String DATABASE_FILEPATH = "portfolio.db";

    private String url;
    private Connection conn;

    public SqlLiteStorage() {
        url = DB_CONNECTION_STR_PREFIX + DATABASE_FILEPATH;
        createNewDatabase();
    }

    @Override
    public boolean open() {
        try {
            this.conn = DriverManager.getConnection(url);
            return true;
        }  catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Problem closing database connection");
            System.exit(0);
        }
    }

    @Override
    public void executeQuery(String sql) {
        open();
        try (Statement stmnt = conn.createStatement()) {
            stmnt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Unable to execute sql query");
        } finally {
            close();
        }
    }

    @Override
    public Map<String, Integer> getAllStocks() {
        Map<String, Integer> stocks = new HashMap<>();
        String sql = String.format("SELECT * FROM %s", STOCKS_TABLE_NAME);

        open();
        ResultSet results = null;
        try (Statement stmnt = conn.createStatement()) {
            results = stmnt.executeQuery(sql);
            while(results.next()) {
                stocks.put(results.getString(1),
                        Integer.parseInt(results.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("Unable to retrieve portfolio from database");
        } finally {
            try {
                if (results != null) results.close();
            } catch (SQLException e) {
                System.out.println("Problem completing sql query");
            }
            close();
        }
        return stocks;
    }

    private void createNewDatabase() {
        boolean databaseExists = new File(DATABASE_FILEPATH).exists();
        if (databaseExists) return; // do not over-write existing database

        boolean dbOpen = open();
        if (dbOpen) {
            createSchema();
            close();
        } else {
            System.out.println("Unable to establish database connection");
            System.exit(0);
        }
    }

    private void createSchema() {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (ticker text PRIMARY KEY, units integer)",
                STOCKS_TABLE_NAME);
        try (Statement stmt = this.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Problem with database operation");
            System.exit(0);
        }

    }
}
