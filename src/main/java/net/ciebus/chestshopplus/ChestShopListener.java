package net.ciebus.chestshopplus;

import com.Acrobot.ChestShop.Events.ItemParseEvent;
import com.Acrobot.ChestShop.Events.PreTransactionEvent;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class ChestShopListener implements Listener {
    @EventHandler
    public void onShopInitEvent(PreTransactionEvent evt) {
        Block sign = evt.getSign().getBlock();
        System.out.println(sign.getWorld().getName() + ":" + sign.getX() + ":" + sign.getY() + ":" + sign.getZ());
    }
}
