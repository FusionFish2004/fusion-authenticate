package cn.fusionfish.fusionauth.auth;

import cn.fusionfish.fusionauth.FusionAuth;
import cn.fusionfish.fusionauth.auth.beans.LoginRequest;
import cn.fusionfish.fusionauth.auth.beans.Mod;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 登陆请求管理器
 * @author JeremyHu
 */
public class RequestManager {

    //存储玩家对应登陆请求的HashMap
    private final Map<UUID, LoginRequest> requestMap = Maps.newHashMap();

    private final FileConfiguration config = FusionAuth.getInstance().getConfig();

    /**
     * 向管理器中添加一个登陆请求
     * @param loginRequest 登陆请求
     */
    public void add(@NotNull LoginRequest loginRequest) {
        String playerName = loginRequest.getUserName();
        UUID uuid = Bukkit.getPlayerUniqueId(playerName);
        requestMap.put(uuid, loginRequest);
        FusionAuth.log("玩家" + playerName + "发送了一条登录验证消息...");
    }

    /**
     * 向管理器中添加一个登陆请求
     * @param json 登陆请求Json
     */
    public void add(@NotNull String json) {
        LoginRequest loginRequest = new Gson().fromJson(json, LoginRequest.class);
        add(loginRequest);
    }

    /**
     * 获取管理器实例
     * @return 管理器实例
     */
    public static RequestManager getInstance() {
        return FusionAuth.getManager();
    }

    /**
     * 获取一个登陆请求
     * @param uuid 玩家UUID
     * @return 登陆请求，若不存在则返回null
     */
    public LoginRequest get(@NotNull UUID uuid) {
        return requestMap.get(uuid);
    }

    /**
     * 获取一个登陆请求
     * @param player 玩家
     * @return 登陆请求，若不存在则返回null
     */
    public LoginRequest get(@NotNull Player player) {
        return get(player.getUniqueId());
    }

    /**
     * 从管理器中移除一个登陆请求
     * @param uuid 玩家UUID
     */
    public void remove(UUID uuid) {
        requestMap.remove(uuid);
    }

    /**
     * 从管理器中移除一个登陆请求
     * @param player 玩家
     */
    public void remove(Player player) {
        remove(player.getUniqueId());
    }

    /**
     * 验证玩家的验证时间是否有效
     * @param uuid 玩家UUID
     * @return 是否有效
     */
    public boolean authTime(UUID uuid) {
        long queryTime = get(uuid).getTime();
        long now = new Date().getTime();

        long authWindow = config.getInt("auth.authenticate-window", 100);

        return (now - queryTime) < authWindow;
    }

    public boolean authMods(UUID uuid) {
        List<Mod> mods = get(uuid).getMods();
        List<String> depends = config.getStringList("auth.filter-mod-id.depends");
        List<String> banned = config.getStringList("auth.filter-mod-id.banned");

        List<String> ids = mods.stream().map(Mod::getId).collect(Collectors.toList());

        for (String id : ids) {
            if (banned.stream().anyMatch(s -> s.equals(id))) {
                return false;
            }
        }

        return true;
    }

    public boolean auth(UUID uuid) {

        if (!requestMap.containsKey(uuid)) return false;

        return authTime(uuid) && authMods(uuid);
    }

    public boolean auth(Player player) {
        return auth(player.getUniqueId());
    }
}
