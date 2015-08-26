package vikasyadav.iiitb.com.zensehttp;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

public class MyService extends Service{
    private static String TAG = "Vikas ";
    public MyServer vikserver = new MyServer();
    public String la = "lat"; //latitude
    public String lo = "long" ;//longitude

    LocationManager vm;
    LocationListener ll;

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        LocationListener locationListener = new MyLocationListener();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ll = locationListener;
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20, 10, locationListener);
        vm = lm;
        Log.d(TAG, "FirstService started");

        try{
            vikserver.start();
            Log.d(TAG, "Server Started Successfully");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //this.stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    /*Location Listener Code Starts here */



    /* Location Listener Code Ends here */



    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d(TAG, "Service Destroyed");

        Log.d(TAG, "Server Stopped Successfully");
        vikserver.stop();

        vm.removeUpdates(ll);

        super.onDestroy();
    }

    private class MyServer extends NanoHTTPD {
        /*
         * logger to log to.
         */
        //private final Logger LOG = Logger.getLogger(HelloServer.class.getName());


        public void main(String[] args) {
            ServerRunner.run(HelloServer.class);
        }

        public MyServer() {
            super(8080);
        }

        @Override
        public Response serve(IHTTPSession session) {
            String lat = "latitude";
            String longi = "longitude";
            Method method = session.getMethod();
            String uri = session.getUri();

            Log.v("vikas", "coming bere");
            //mHandler.obtainMessage(getGPSLocation(), "");


            Log.v("vikas", "coming here also ");
            //HelloServer.LOG.info(method + " '" + uri + "' ");

            String msg = la + ", "+ lo;
            Map<String, String> parms = session.getParms();
            /*if (parms.get("username") == null) {
                msg += "t' name='username'></p>\n" + "</form>\n";
            } else {
                msg += "<p>Hello, " + parms.get("username") + "!</p>";
            }*/

            msg += "\n";

            return newFixedLengthResponse(msg);
        }
    }

    private final class MyLocationListener implements LocationListener {
        @Override
        public void onProviderEnabled(String provider){
            Log.d(TAG, "onProivderEnabled: "+provider);
        }

        @Override
        public void onProviderDisabled(String provider){
            Log.d(TAG, "onProivderDisabled : "+provider);
        }

        @Override
        public void onLocationChanged(Location location){
            Log.d(TAG, "Location value changed ");
            Float accuracy = location.getAccuracy();
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            la = latitude.toString();
            lo = longitude.toString();
        }

        @Override
        public void onStatusChanged (String provider, int status, Bundle extras){
            Log.d(TAG, "onStatusChanged Location Status Changed, Captured");

        }

    }
}

