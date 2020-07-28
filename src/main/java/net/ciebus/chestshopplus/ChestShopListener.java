package net.ciebus.chestshopplus;

import com.Acrobot.ChestShop.Events.PreTransactionEvent;
import com.Acrobot.ChestShop.Events.ShopCreatedEvent;
import com.Acrobot.ChestShop.Events.ShopDestroyedEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import net.ciebus.chestshopplus.Database.Shop;
import net.ciebus.chestshopplus.Database.Transaction;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ChestShopListener implements Listener {
    @EventHandler
    public void onShopInitEvent(PreTransactionEvent evt) throws SQLException {
        Sign sign = evt.getSign();
        System.out.println(checkRegisteredShop(sign));
        if(!checkRegisteredShop(sign)) {
            registerShop(sign, evt.getOwnerAccount().getUuid(),sign.getLine(3));
        }
    }

    @EventHandler
    public void onShopDeleted(ShopDestroyedEvent evt) throws  SQLException {
        Sign sign = evt.getSign();
        deleteShop(sign);
    }

    @EventHandler
    public void onShopCreated(ShopCreatedEvent evt) throws  SQLException {
        Sign sign = evt.getSign();
        registerShop(sign, evt.getOwnerAccount().getUuid(),evt.getSignLine((short) 3));
    }

    @EventHandler
    public void onShopUpdated(SignChangeEvent evt) throws SQLException {
        System.out.println(evt.getLine(3));
        updateShop((Sign)evt.getBlock().getState(),evt.getLine(3));
    }

    @EventHandler
    public void onShopTransaction(TransactionEvent evt) throws SQLException {
        Dao<Transaction,String> transationDao = DaoManager.createDao(ChestShopPlus.transactionConnectionSource,Transaction.class);
        Transaction transaction = new Transaction();
        Dao<Shop, String> shopDao = DaoManager.createDao(ChestShopPlus.shopConnectionSource, Shop.class);
        Sign sign = evt.getSign();
        List<Shop> shops = shopDao.queryBuilder().where().eq("worldName",sign.getBlock().getWorld().getName()).and().eq("location",sign.getX() + ":" + sign.getY() + ":" + sign.getZ()).query(); //queryForEq("worldName",sign.getBlock().getWorld().getName())
        transaction.setTransaction(shops.get(0).getShopId(),evt.getClient().getUniqueId(),evt.getTransactionType() == TransactionEvent.TransactionType.BUY ? "BUY" : "SELL",evt.getStock()[0].getAmount(),new Date());
        transationDao.create(transaction);
    }

    public boolean checkRegisteredShop(Sign sign) throws SQLException {
        Dao<Shop, String> shopDao =
                DaoManager.createDao(ChestShopPlus.shopConnectionSource, Shop.class);
        List<Shop> shops = shopDao.queryBuilder().where().eq("worldName",sign.getBlock().getWorld().getName()).and().eq("location",sign.getX() + ":" + sign.getY() + ":" + sign.getZ()).query(); //queryForEq("worldName",sign.getBlock().getWorld().getName())
        if(shops.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void registerShop(Sign sign, UUID owner, String itemName) throws SQLException {
        Dao<Shop, String> shopDao =
                DaoManager.createDao(ChestShopPlus.shopConnectionSource, Shop.class);
        Shop shop = new Shop();
        shop.setShop(sign.getBlock().getWorld().getName(),sign.getX() + ":" + sign.getY() + ":" + sign.getZ(),owner,itemName,Integer.parseInt(sign.getLine(1)),getPrice(sign.getLine(2),'s'),getPrice(sign.getLine(2),'b'));
        shopDao.create(shop);
    }

    public void updateShop(Sign sign, String itemName) throws SQLException {
        Dao<Shop, String> shopDao =
                DaoManager.createDao(ChestShopPlus.shopConnectionSource, Shop.class);
        UpdateBuilder<Shop,String> updateBuilder = shopDao.updateBuilder();
        updateBuilder.where().eq("worldName",sign.getBlock().getWorld().getName()).and().eq("location",sign.getX() + ":" + sign.getY() + ":" + sign.getZ()); //queryForEq("worldName",sign.getBlock().getWorld().getName())
        updateBuilder.updateColumnValue("itemName",itemName);
        updateBuilder.update();
    }

    public void deleteShop(Sign sign) throws SQLException {
        Dao<Shop, String> shopDao =
                DaoManager.createDao(ChestShopPlus.shopConnectionSource, Shop.class);
        DeleteBuilder<Shop,String> deleteBuilder =  shopDao.deleteBuilder();
        deleteBuilder.where().eq("worldName",sign.getBlock().getWorld().getName()).and().eq("location",sign.getX() + ":" + sign.getY() + ":" + sign.getZ()); //queryForEq("worldName",sign.getBlock().getWorld().getName())
        deleteBuilder.delete();
        System.out.println("deteled");
    }

    public static int getPrice(String text,char indicator) {
        String[] split = text.replace(" ", "").toLowerCase().split(":");
        String character = String.valueOf(indicator).toLowerCase();

        for (String part : split) {
            if (!part.startsWith(character) && !part.endsWith(character)) {
                continue;
            }

            part = part.replace(character, "");

            if (part.equals("free")) {
                return 0;
            } else {
                return Integer.parseInt(part);
            }
        }
        return 0;
    }
}
