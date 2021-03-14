package dk.mummtech.teknologimobilepay;

import android.app.Application;

public class KioskModeApp extends Application {

    public static boolean isInLockMode;

    public static boolean isInLockMode() {
        return isInLockMode;
    }

    public static void setIsInLockMode(boolean isInLockMode) {
        dk.mummtech.teknologimobilepay.KioskModeApp.isInLockMode = isInLockMode;
    }
}
