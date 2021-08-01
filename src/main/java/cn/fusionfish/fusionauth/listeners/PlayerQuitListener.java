package cn.fusionfish.fusionauth.listeners;

import cn.fusionfish.fusionauth.auth.RequestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        RequestManager.getInstance().remove(player);
    }
}
