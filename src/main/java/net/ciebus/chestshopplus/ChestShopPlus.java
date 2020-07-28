package net.ciebus.chestshopplus;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.ciebus.chestshopplus.API.ApiServer;
import net.ciebus.chestshopplus.Database.ConnectionManager;
import net.ciebus.chestshopplus.Database.Shop;
import net.ciebus.chestshopplus.Database.SqliteDatabaseType;
import net.ciebus.chestshopplus.Database.Transaction;
import org.bukkit.plugin.java.JavaPlugin;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.Executors;

public final class ChestShopPlus extends JavaPlugin  {

    private static File dataFolder;

    public static ConnectionSource shopConnectionSource;
    public static ConnectionSource transactionConnectionSource;

    public ChestShopPlus() {
        dataFolder = getDataFolder();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ChestShopListener(), this);

        String shopURI = ConnectionManager.getURI(ChestShopPlus.loadFile("shops.db"));
        String transactionURI = ConnectionManager.getURI(ChestShopPlus.loadFile("transactions.db"));

        // ConnectionSource connectionSource = null;
        try {
            shopConnectionSource = new JdbcConnectionSource(shopURI, new SqliteDatabaseType());
            transactionConnectionSource = new JdbcConnectionSource(transactionURI, new SqliteDatabaseType());

            // Dao<Shop, String> shopDao = DaoManager.createDao(shopConnectionSource, Shop.class);
            // Dao<Transaction, String> transationDao = DaoManager.createDao(transactionConnectionSource, Transaction.class);
            TableUtils.createTable(shopConnectionSource, Shop.class);
            TableUtils.createTable(transactionConnectionSource, Transaction.class);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ApiServer api = new ApiServer();


    }

    /** リクエストのヘッダなどを表示 */
    private static void logRequest(HttpExchange exchange){
        // 「GET / HTTP1.0」などと表示
        System.out.println(exchange.getRequestMethod() + " / " + exchange.getProtocol());
        // リクエストのヘッダを表示
        exchange.getRequestHeaders().forEach((k,v) -> System.out.println(k + ": "+v));
        System.out.println();
    }

    /** レスポンスを返す */
    private static void respond(HttpExchange exchange, String responseText){
        byte[] responseBody = responseText.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        try {
            exchange.sendResponseHeaders(200, responseBody.length);  // 明示的に返す必要あり
            exchange.getResponseBody().write(responseBody);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            shopConnectionSource.close();
            transactionConnectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File loadFile(String string) {
        File file = new File(dataFolder, string);

        return loadFile(file);
    }

    private static File loadFile(File file) {
        if (!file.exists()) {
            try {
                if (file.getParent() != null) {
                    file.getParentFile().mkdirs();
                }

                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

}
