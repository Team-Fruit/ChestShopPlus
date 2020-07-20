package net.ciebus.chestshopplus;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.ciebus.chestshopplus.Database.ConnectionManager;
import net.ciebus.chestshopplus.Database.Shop;
import net.ciebus.chestshopplus.Database.SqliteDatabaseType;
import net.ciebus.chestshopplus.Database.Transaction;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
