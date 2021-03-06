package net.ciebus.chestshopplus.Database;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.UUID;

@DatabaseTable(tableName = "shops")
// @DatabaseFileName("shops.db")
public class Shop {
    @DatabaseField(generatedId=true)
    public int id;
    @DatabaseField
    public String worldName;
    @DatabaseField
    public String location;
    @DatabaseField
    public UUID owner;
    @DatabaseField
    public String itemName;
    @DatabaseField
    public int amount;
    @DatabaseField
    public double  sellPrice;
    @DatabaseField
    public double buyPrice;

    public Shop () {
    }

    public Shop(int id,String worldName,String location,UUID owner,String itemName,int amount,double sellPrice,double buyPrice) {
        this.id = id;
        this.worldName = worldName;
        this.location = location;
        this.owner = owner;
        this.itemName = itemName;
        this.amount = amount;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return "Shop [id=" + id + ", location=" + worldName + ":" + location + ", owner=" + owner + ", itemName=" + itemName + ", amount=" + amount + ", sellPrice=" + sellPrice + ", buyPrice=" + buyPrice + "]";
    }

    public void setShop(String worldName,String location,UUID owner,String itemName, int amount,double sellPrice,double buyPrice) {
        this.worldName = worldName;
        this.location = location;
        this.owner = owner;
        this.itemName = itemName;
        this.amount = amount;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    public int getShopId() {
        return this.id;
    }
}
