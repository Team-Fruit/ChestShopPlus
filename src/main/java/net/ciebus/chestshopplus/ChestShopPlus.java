package net.ciebus.chestshopplus;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.ciebus.chestshopplus.Database.ConnectionManager;
import net.ciebus.chestshopplus.Database.Shop;
import net.ciebus.chestshopplus.Database.SqliteDatabaseType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class ChestShopPlus extends JavaPlugin  {

    private static File dataFolder;

    public ChestShopPlus() {
        dataFolder = getDataFolder();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ChestShopListener(), this);
        System.out.println("hisushi");

        String uri = ConnectionManager.getURI(ChestShopPlus.loadFile("test.db"));

        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(uri, new SqliteDatabaseType());
            Dao<Shop, String> shopDao =
                    DaoManager.createDao(connectionSource, Shop.class);
            TableUtils.createTable(connectionSource, Shop.class);
            Shop shop = new Shop();
            // shop.setLocation("testLocation");
            shopDao.create(shop);
            connectionSource.close();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
