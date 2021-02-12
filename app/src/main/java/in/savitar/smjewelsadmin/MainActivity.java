package in.savitar.smjewelsadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;

import in.savitar.smjewelsadmin.databinding.ActivityMainBinding;

import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtilMain;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    MainActivityContract.Presenter mPresenter;
    ActivityMainBinding mBinding;

    CountDownTimer countDownTimer;
    int i;

    public String planName = "PlanA";
    public String setName = "Set1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mPresenter = new MainActivityPresenter(this);
        init();
        mBinding.getRoot();

        //generateFirebaseKeys();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    private void init() {
        setupNavigator();

        //generateFirebaseKeys();

        NavigationUtilMain.INSTANCE.setUpHomeScreen();
    }

    private void setupNavigator() {
        NavigationUtilMain.INSTANCE.setupNavigator(this, getSupportActionBar(), getmBinding(), null);
    }

    public ActivityMainBinding getmBinding() {
        return (ActivityMainBinding) mBinding;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
