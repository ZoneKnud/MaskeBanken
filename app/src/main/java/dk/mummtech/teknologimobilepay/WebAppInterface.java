package dk.mummtech.teknologimobilepay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
//    @JavascriptInterface
//    public void showToast(String toast) {
//        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
//    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @JavascriptInterface
    public void updatePrice(String price) {
        NotificationListener.updatePrice(price);
        Log.i("updatePrice", price);
    }
}