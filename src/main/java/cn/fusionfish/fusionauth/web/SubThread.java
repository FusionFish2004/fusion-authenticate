package cn.fusionfish.fusionauth.web;

import cn.fusionfish.fusionauth.auth.RequestManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SubThread extends Thread {

    private final Socket socket;

    public SubThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info;

            while((info = br.readLine()) != null){
                RequestManager.getInstance().add(info);
            }


        } catch (Exception e) {
            // TODO: handle exception
        } finally{
            //关闭资源
            try {
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
