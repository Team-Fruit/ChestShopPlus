package net.ciebus.chestshopplus.Database;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "shops")
// @DatabaseFileName("shops.db")
public class Shop {
    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField
    private String worldName;
    @DatabaseField
    private String location;
    @DatabaseField
    private String owner;
    @DatabaseField
    private String itemName;
    @DatabaseField
    private int amount;
    @DatabaseField
    private double  sellPrice;
    @DatabaseField
    private double buyPrice;

    public Shop () {
    }

    public Shop(int id,String worldName,String location,String owner,String itemName,int amount,double sellPrice,double buyPrice) {
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

    public void setShop(String worldName,String location,String owner,String itemName, String amount) {
        this.location = location;
    }
}
