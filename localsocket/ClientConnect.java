package ching.android_localsocket.localsocket;

import android.content.Context;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ching.android_localsocket.utils.Parameter;
import ching.android_localsocket.listener.ClientCallBack;

/**
 * Created by book871181 on 16/7/17.
 */
public class ClientConnect {
    private static final String TAG = "ClientConnect";
    private Context mContext;
    private LocalSocket client = null;
    private int timeout = 30000;
    private Parameter mParameter = new Parameter();
    private BufferedReader is = null;
    private Boolean isClientThreadRun;
    ClientCallBack mClientCallBack;

    public ClientConnect(Context context,ClientCallBack clientCallBack ) {
        mContext = context;
        mClientCallBack = clientCallBack;
        client = new LocalSocket();
        isClientThreadRun = false;
    }
    public boolean connect() {
        try {
            Log.d(TAG, "intoConnect");
            client.connect(new LocalSocketAddress(mParameter.ServerName));
            client.setSoTimeout(timeout);
            Log.d(TAG, "is Connect?" + client.isConnected());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Connect Failuer"+e.getMessage());
            return false;
        }
    }
    Handler mHandler = new Handler();
    Runnable run;
    public void getData(){

        if(!isClientThreadRun) {
            run = new Runnable() {
                @Override
                public void run() {
                    mHandler.postDelayed(this, 1000);
                    recv();

                }
            };
            mHandler.post(run);
            isClientThreadRun = true;

        }
     }



    private void recv (){
        try {
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String result = is.readLine();
            Log.d(TAG,"get DATA" + result);
            mClientCallBack.receivedMessage(result);


        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }

    }
    public void cencel(){
          mHandler.removeCallbacks(run);
         isClientThreadRun  = false;

    }



}
