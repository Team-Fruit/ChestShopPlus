package net.ciebus.chestshopplus.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "shops")
// @DatabaseFileName("shops.db")
public class Transaction {
    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField
    private int shopId;
    @DatabaseField
    private UUID actionUUID;
    @DatabaseField
    private String action;
    @DatabaseField
    private int amount;
    @DatabaseField
    private Date date;

    public Transaction () {
    }

    public Transaction(int id,int shopId,UUID actionUUID,String action,int amount,Date date) {
        this.id = id;
        this.shopId = shopId;
        this.actionUUID = actionUUID;
        this.action = action;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return  "TODO"; //"Shop [id=" + id + ", location=" + worldName + ":" + location + ", owner=" + owner + ", itemName=" + itemName + ", amount=" + amount + ", sellPrice=" + sellPrice + ", buyPrice=" + buyPrice + "]";
    }

    public void setTransaction(int shopId,UUID actionUUID,String action,int amount,Date date) {
        this.shopId = shopId;
        this.actionUUID = actionUUID;
        this.action = action;
        this.amount = amount;
        this.date = date;
    }
}