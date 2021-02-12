package in.savitar.smjewelsadmin.mvp.ui.splash;

import android.Manifest;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentSplashBinding;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtil;


public class SplashFragment extends Fragment implements SplashContract.View {

    FragmentSplashBinding mBinding;
    SplashPresenter mPresenter;

    //Permissions Needed
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECEIVE_SMS
    };

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash,container,false);
        mPresenter = new SplashPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPresenter.checkInternet(getContext())){// Checking of internet connection

                    if (!mPresenter.checkPermission(getActivity(),PERMISSIONS)){
                        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
                    }

                    if (mPresenter.checkLogin()) { //Already user is logged in
                        NavigationUtil.INSTANCE.toMainActivity();
                    } else { //No User is logged In
                        NavigationUtil.INSTANCE.toLogin();
                    }

                } else{ //No Internet connection
                    NavigationUtil.INSTANCE.toNoInternet();
                }
            }
        },3000);

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}