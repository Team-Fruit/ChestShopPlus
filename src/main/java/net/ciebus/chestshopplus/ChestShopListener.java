package net.ciebus.chestshopplus;

import com.Acrobot.ChestShop.Events.ItemParseEvent;
import com.Acrobot.ChestShop.Events.PreTransactionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class ChestShopListener implements Listener {
    @EventHandler
    public void onShopInitEvent(PreTransactionEvent evt) {

        System.out.println(evt.g);
    }
}
