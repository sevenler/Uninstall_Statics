package com.example.untitled;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SDCardListenSer extends Service {

    SDCardListener[] listenners;

    @Override
    public void onCreate() {


        SDCardListener[] listenners = {new SDCardListener("/data/data/com.example.untitled", this),
                new SDCardListener(Environment.getExternalStorageDirectory() + File.separator + "1.txt", this)};
        this.listenners = listenners;

        Log.i("onEvent", "=========onCreate============");
        for (SDCardListener listener : listenners) {
            listener.startWatching();
        }

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "1.txt");
        Log.i("onEvent", "dddddddddddddddddddddd nCreate============");
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        for (SDCardListener listener : listenners) {
            listener.stopWatching();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


class SDCardListener extends FileObserver {
    private String mPath;
    private final Context mContext;

    public SDCardListener(String parentpath, Context ctx) {
        super(parentpath);
        this.mPath = parentpath;
        this.mContext = ctx;
    }

    @Override
    public void onEvent(int event, String path) {
        int action = event & FileObserver.ALL_EVENTS;
        switch (action) {

            case FileObserver.DELETE:
                Intent intent = new Intent();
                //exeShell("am start -a android.intent.action.VIEW -d http:aoi.androidesk.com");
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                Log.i("onEvent", "打包请关闭****DELETE 删除 " + mPath + File.separator + path);
                //openBrowser();
                break;

            case FileObserver.MODIFY:
                Log.i("onEvent", "打包请关闭****MODIFY 修改 " + mPath + File.separator + path);
                break;

            case FileObserver.CREATE:
                Log.i("onEvent", "打包请关闭****CREATE 创建 " + mPath + File.separator + path);
                break;

            default:
                break;
        }
    }

    protected void openBrowser() {
        Uri uri = Uri.parse("http://aoi.androidesk.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

    public void exeShell(String cmd){
        try{
            Runtime.getRuntime().exec(cmd);
            /*BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                Log.i("exeShell",line);
            }*/

        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }


}
