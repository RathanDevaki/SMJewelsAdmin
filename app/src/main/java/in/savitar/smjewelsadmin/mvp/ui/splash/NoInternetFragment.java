package in.savitar.smjewelsadmin.mvp.ui.splash;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.dmoral.toasty.Toasty;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentNoInternetBinding;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtil;



public class NoInternetFragment extends Fragment implements SplashContract.View {

    FragmentNoInternetBinding mBinding;
    SplashPresenter mPresenter;


    public NoInternetFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_no_internet,container,false);
        mPresenter = new SplashPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        mBinding.tryAgainInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.checkInternet(getContext())){
                    NavigationUtil.INSTANCE.setSplash();
                }else {
                    onFailure("Try Again");
                }
            }
        });

    }

    @Override
    public void onSuccess(String message) {
        //  Toasty.success(getContext(),message).show();
    }

    @Override
    public void onFailure(String message) {
        Toasty.error(getContext(),message).show();
    }
}