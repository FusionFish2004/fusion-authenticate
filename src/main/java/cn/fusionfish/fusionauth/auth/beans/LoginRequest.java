package cn.fusionfish.fusionauth.auth.beans;

import java.util.Date;
import java.util.List;

public class LoginRequest {

    private final String userName;
    private final long time = new Date().getTime();
    private final List<Mod> mods;


    public LoginRequest(String userName, List<Mod> mods) {
        this.userName = userName;
        this.mods = mods;
    }

    public String getUserName() {
        return userName;
    }

    public long getTime() {
        return time;
    }

    public List<Mod> getMods() {
        return mods;
    }
}
