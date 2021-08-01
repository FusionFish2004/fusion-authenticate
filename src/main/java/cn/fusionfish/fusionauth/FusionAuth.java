package cn.fusionfish.fusionauth;

import cn.fusionfish.fusionauth.auth.RequestManager;
import cn.fusionfish.fusionauth.listeners.PlayerLoginListener;
import cn.fusionfish.fusionauth.listeners.PlayerQuitListener;
import cn.fusionfish.fusionauth.web.AuthThread;
import cn.fusionfish.libs.plugin.FusionPlugin;

import java.io.File;

/**
 * 插件主类
 */
public class FusionAuth extends FusionPlugin {

    private final AuthThread authThread = new AuthThread();
    private static RequestManager manager;
    private final File CONFIG_FILE = new File(getDataFolder(), "config.yml");

    @Override
    protected void init() {

        registerListener(PlayerLoginListener.class);
        registerListener(PlayerQuitListener.class);

        manager = new RequestManager();

        if (!CONFIG_FILE.exists()) {
            saveDefaultConfig();
        }

        authThread.start();
    }

    @Override
    protected void disable() {
        authThread.close();
    }

    public static RequestManager getManager() {
        return manager;
    }
}
