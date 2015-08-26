package vikasyadav.iiitb.com.zensehttp;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int PORT = 8080;
    private TextView hello;
    private Handler handler = new Handler();
    private Handler mHandler = new Handler();
    public String longi = "longitude";
    public String lat = "latitude";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = (TextView) findViewById(R.id.hello);

        Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {

            }
        };

        ((Button) findViewById(R.id.server)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GPSTracker gps = new GPSTracker(MainActivity.this);
                startService(new Intent(getApplicationContext(), MyService.class));
                makeToast("Server Started");

            }
        });

        ((Button) findViewById(R.id.stopserver)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GPSTracker gps = new GPSTracker(MainActivity.this);
                stopService(new Intent(getApplicationContext(), MyService.class));
                makeToast("Server Stopped");
            }
        });
    }

    public void makeToast(String s) {
        //helper function to make toast
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView textIpaddr = (TextView) findViewById(R.id.ipaddr);
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        textIpaddr.setText("Please access! http://" + formatedIpAddress + ":" + PORT);


    }


}