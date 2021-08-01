package cn.fusionfish.libs.plugin;

import cn.fusionfish.libs.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FusionPlugin extends JavaPlugin {

    private static FusionPlugin instance;
    private CommandManager commandManager;

    /**
     * 获取插件实例
     * @return 插件实例
     */
    public static FusionPlugin getInstance() {
        return instance;
    }

    public static void log(String msg) {
        instance.getLogger().info(msg);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public final void onEnable() {
        instance = this;
        commandManager = new CommandManager();

        if (!getDataFolder().exists()) {
            log("正在创建插件数据文件夹...");
            boolean result = getDataFolder().mkdir();
            if (!result) {
                log("创建插件数据文件夹失败！");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }

        init();
    }

    @Override
    public final void onDisable() {

        commandManager.unregisterCommands();
        disable();
    }

    public void registerListener(Class<? extends Listener> clazz) {
        try {
            Listener instance = clazz.newInstance();
            Bukkit.getPluginManager().registerEvents(instance, this);
            log("监听器" + clazz.getSimpleName() + "注册成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    protected abstract void init();

    protected abstract void disable();
}
