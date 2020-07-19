package net.ciebus.chestshopplus.Database;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "shops")
// @DatabaseFileName("shops.db")
public class Shop {
    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField
    private String location;
    @DatabaseField
    private String owner;
    @DatabaseField
    private String itemName;
    @DatabaseField
    private int amount;
    @DatabaseField
    private int sellPrice;
    @DatabaseField
    private int buyPrice;

    public Shop () {

    }

    public Shop(int id,String location,String owner,String itemName,int amount,int sellPrice,int buyPrice) {
        this.id = id;
        this.location = location;
        this.owner = owner;
        this.itemName = itemName;
        this.amount = amount;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return "Shop [id=" + id + ", location=" + location + ", owner=" + owner + ", itemName=" + itemName + ", amount=" + amount + ", sellPrice=" + sellPrice + ", buyPrice=" + buyPrice + "]";
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
