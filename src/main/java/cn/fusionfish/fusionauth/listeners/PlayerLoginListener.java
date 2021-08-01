package cn.fusionfish.fusionauth.listeners;

import cn.fusionfish.fusionauth.FusionAuth;
import cn.fusionfish.fusionauth.auth.RequestManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Objects;

public class PlayerLoginListener implements Listener {

    private final FileConfiguration config = FusionAuth.getInstance().getConfig();

    @EventHandler
    public void onJoin(PlayerLoginEvent event) {

        RequestManager manager = RequestManager.getInstance();
        String kickMsg = Objects.requireNonNull(config.getString("message.not-using-admitted-client", "未使用指定客户端"));
        Player player = event.getPlayer();

        if (!manager.auth(player)) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
            return;
        }
        event.allow();
    }
}
