package in.savitar.smjewelsadmin.mvp.ui.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import in.savitar.smjewelsadmin.mvp.ui.splash.SplashActivity;


public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ACProgressFlower dialog;

    FirebaseAuth mAuth;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchDashboardData() {

    }

    @Override
    public void fetchCollectors() {

    }

    @Override
    public void fetchUsers() {

    }

    @Override
    public void fetchNotifications() {

    }

    @Override
    public void scanCode() {

    }

    @Override
    public void showDialog(Context context) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Checking")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void signOut(Activity activity) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        clearPreferences(activity);
        Intent i = new Intent(activity, SplashActivity.class);
        activity.startActivity(i);
        activity.finish();
    }

    private void clearPreferences(Activity activity) {

        SharedPreferences preferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
