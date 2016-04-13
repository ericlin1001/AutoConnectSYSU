package com.example.kaizeng.autoconnectsysyu;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

class AutoLoginThread extends Thread{
    String username,password;

    AutoLoginThread(String user,String pass){
        username=user;
        password=pass;
    }
    @Override
    public void run() {
        //  while(!isInterrupted()) {
        while (true) {
            try {
                String res = HttpRequest.sendPost("http://10.10.2.22/portal/logon.cgi", "PtUser=" + username + "&PtPwd=" + password + "&PtButton=Logon");
                //String res = HttpRequest.sendPost("http://10.10.2.22/portal/logon.cgi", "PtUser=linjunh5&PtPwd=ljh**wyq520&PtButton=Logon");
                //if(res.contains("Logon")
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //}
}
public class AutoLoginService extends Service  {
    AutoLoginThread autologin;
    String user;
    String pass;

    public AutoLoginService() {

    }
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        user=intent.getStringExtra("username");
        pass=intent.getStringExtra("password");
        autologin=new AutoLoginThread(user,pass);
        autologin.start();
    }

    @Override
        public void onCreate() {
            autologin=new AutoLoginThread(user,pass);
            autologin.start();
        }

        @Override
        public void onDestroy() {
            autologin.interrupt();
        }



}
