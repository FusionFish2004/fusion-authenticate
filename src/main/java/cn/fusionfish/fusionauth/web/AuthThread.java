package cn.fusionfish.fusionauth.web;

import cn.fusionfish.fusionauth.FusionAuth;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AuthThread extends BukkitRunnable {

    ServerSocket serverSocket;
    Socket socket = new Socket();

    public AuthThread() {
        try {
            serverSocket = new ServerSocket(11451);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.runTaskTimerAsynchronously(FusionAuth.getInstance(), 0L, 1L);
    }

    public void close() {
        try {
            this.cancel();
            serverSocket.close();
            socket.close();
            FusionAuth.log("正在关闭Socket...");
        } catch (Exception ignored) {

        }

    }


    @Override
    public void run() {

        try {
            socket = serverSocket.accept();
            SubThread thread = new SubThread(socket);
            thread.start();
        } catch (Exception ignored) {

        }
    }
}
