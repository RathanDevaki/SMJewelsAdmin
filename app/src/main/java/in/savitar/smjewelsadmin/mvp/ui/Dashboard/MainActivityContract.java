package in.savitar.smjewelsadmin.mvp.ui.Dashboard;

import android.app.Activity;
import android.content.Context;

public interface MainActivityContract {

    interface View{
        void onSuccess();
        void onFailure();
    }

    interface Presenter {
        void fetchDashboardData();
        void fetchCollectors();
        void fetchUsers();
        void fetchNotifications();
        void scanCode();
        void showDialog(Context context);
        void hideDialog();
        void signOut(Activity activity);

    }


}
