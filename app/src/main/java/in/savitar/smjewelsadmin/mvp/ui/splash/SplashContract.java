package in.savitar.smjewelsadmin.mvp.ui.splash;

import android.app.Activity;
import android.content.Context;

public interface SplashContract {

    interface View {
        void onSuccess(String message);
        void onFailure(String message);
    }

    interface Presenter{
        boolean checkInternet(Context context);
        boolean checkLogin();
        void showDialog(Context context);
        void hideDialog();
        boolean checkPermission(Activity activity, String... permissions);
    }



}
