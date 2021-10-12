package ching.android_localsocket.localsocket;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;

import ching.android_localsocket.utils.Parameter;

/**
 * Created by book871181 on 16/7/16.
 */
public class ServerThread extends Thread {
    private final static String TAG = "ServerThread";
    private LocalServerSocket server = null;
    private LocalSocket connect = null;
    Parameter mParameter = new Parameter();
    PrintWriter os = null;


    @Override
    public void run() {

        try {
            Log.d(TAG,"Server Start" );

            server = new LocalServerSocket(mParameter.ServerName);
            Log.d(TAG,"Server Create" );

            connect = server.accept();
            os = new PrintWriter(connect.getOutputStream());

            while (true) {
                radom();
            }
        } catch (IOException e) {
            Log.e(TAG,"Error: "+ e.getMessage());
            e.printStackTrace();
        }

    }
    public void radom() {
        Log.d(TAG, "Send Data");

        int Random = (int) (Math.random() * 4);
        try {

            os = new PrintWriter(connect.getOutputStream());


            switch (Random) {
                case 0:
                    os.println("Up");
                    Log.d(TAG, "Up");
                    os.flush();

                    break;

                case 1:
                    os.println("Down");
                    Log.d(TAG, "Down");
                    os.flush();
                    break;

                case 2:
                    os.println("Left");
                    Log.d(TAG, "Left");
                    os.flush();

                    break;
                case 3:
                    os.println("Right");
                    Log.d(TAG, "Right");
                    os.flush();

                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
