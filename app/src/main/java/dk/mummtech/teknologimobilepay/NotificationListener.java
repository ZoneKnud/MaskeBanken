package dk.mummtech.teknologimobilepay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
@SuppressLint("OverrideAbstract")
public class NotificationListener extends NotificationListenerService {

    private static String price;
    private final String TAG = NotificationListener.class.getSimpleName();

    private Context context;

    public static void updatePrice(String pris) {
        price = pris;
        Log.i("updatePrice2", price);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        NotificationObject no = new NotificationObject(sbn,context);
        if(no.getText().length() == 0)
            return;
//        String appName = no.getAppName();
//        String title = no.getTitle();
//        String text = no.getText();
//        String time = utils.getTime(no.getSystemTime());
//        String date = utils.getDate(no.getSystemTime());
//        String packageName = no.getPackageName();

        if(sbn.getPackageName().equals("dk.danskebank.mobilepay")) {
            Log.i(TAG, "MOBILEPAY NOTIFICATION");
            Log.i(TAG, "MOBILEPAY NOTIFICATION");
            Log.i(TAG, "MOBILEPAY NOTIFICATION");
            Log.i(TAG, "Title:" + no.getTitle());
            Log.i(TAG, "Text:" + no.getText());

//            String text = "Du har modtaget 5,00 kr. fra Nina Bruus Mumm med den store maske på sit hoved: MaskeBanken betaling";
            String text = no.getText();


            // Sorting message
            List<String> container;

            if(text.contains(":")) {
                container = new ArrayList<>(Arrays.asList(text.split(":")));
                container.remove(1);
                container = new ArrayList<>(Arrays.asList(container.get(0).split(" ")));
            } else {
                container = new ArrayList<>(Arrays.asList(text.split(" ")));
            }

            Log.i(TAG, String.valueOf(container));

            container.remove(0);
            container.remove(0);
            container.remove(0);
            container.remove(1);
            container.remove(1);

            Log.i(TAG, String.valueOf(container));

            StringBuilder name = new StringBuilder();

            for (int i = 1; i < container.size(); i++) {
                if(name.toString().equals("")) {
                    name = new StringBuilder(container.get(i));
                }else {
                    name.append(" ").append(container.get(i));
                }
            }

            Log.i(TAG, "Name:" + name);
            Log.i(TAG, "Container:" + container);
            Log.i(TAG, "Container(0):" + container.get(0));

//            if(container.get(container.size() - 2).equals("MaskeBanken")) {
            if(container.get(0).equals(price)) {
                Log.i(TAG, "Maskebanken betaling");

                String url = "https://mumm.tech?id=transaction&status=sucess&funds=" + container.get(0) + "&name=" + name + "&raw=" + text;
                MainActivity.SendRequest(url);

            }else {
                Log.i(TAG, "Insufficient funds");

                String url = "https://mumm.tech?id=transaction&status=insufficient-funds&funds=" + container.get(0) + "&name=" + name + "&raw=" + text;
                MainActivity.SendRequest(url);
            }
//            }


        } else {
            Log.i(TAG, "ID:" + sbn.getId());
            Log.i(TAG, "KEY:" + sbn.getKey());
            Log.i(TAG, "Posted by:" + sbn.getPackageName());
            Log.i(TAG, "Title:" + no.getTitle());
            Log.i(TAG, "Text:" + no.getText());
            Log.i(TAG, "Notification:" + sbn.getNotification());
            Log.i(TAG, "RAW STRING:" + sbn.toString());

//            MainActivity.SendRequest("https://mumm.tech");
        }

//        try {
//            NotificationHandler notificationHandler = new NotificationHandler(this);
//            notificationHandler.handlePosted(sbn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Connected");
    }

    @Override
    public void onListenerDisconnected (){
        Log.v(TAG,"Disconnected");
    }
}