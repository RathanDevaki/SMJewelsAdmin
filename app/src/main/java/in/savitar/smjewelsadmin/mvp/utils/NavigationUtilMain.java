package in.savitar.smjewelsadmin.mvp.utils;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.lang.ref.WeakReference;

import in.savitar.smjewelsadmin.databinding.ActivityMainBinding;
import in.savitar.smjewelsadmin.mvp.ui.Collectors.ScanCollectorFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.CreateUserFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.HomeFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.PlanA.PlanASetFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.PlanUserFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.ScanFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.UserCardFragment;

public enum NavigationUtilMain {

    @SuppressLint("StaticFieldLeak")
    INSTANCE;

    private FragmentManager mFragMngr;
    private ViewDataBinding mBinding;
    private AppCompatActivity mActivity;
    private static WeakReference<AppCompatActivity> actRef = new WeakReference<>(null);

    public AppCompatActivity getActivity() {
        final AppCompatActivity activity = actRef.get();
        return activity;
    }

    public void setupNavigator(AppCompatActivity activity,
                               ActionBar actionBar,
                               ViewDataBinding binding,
                               ActionBarDrawerToggle actionBarDrawerToggle) {
        actRef = new WeakReference<>(activity);
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity fragmentActivity = activity;
            mFragMngr = fragmentActivity.getSupportFragmentManager();
        }
        mActivity = activity;
        this.mBinding = binding;
    }

    private void addFragment(Fragment fragment,
                             String tag,
                             boolean addToBackStack) {
        if (mFragMngr == null) {
            return;
        }
        try {
            FragmentTransaction transaction = mFragMngr.beginTransaction();
            //transaction.setCustomAnimations(R.anim.animation_right_in, R.anim.animation_left_out, R.anim.animation_right_in, R.anim.animation_left_out);

            if (mBinding instanceof ActivityMainBinding)
                transaction.replace(((ActivityMainBinding) mBinding).mainContainer.getId(), fragment, tag);
            if (addToBackStack)
                transaction.addToBackStack(tag);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            // Logger.e(Logger.TAG, e.getMessage());
        }
    }

    public void setUpHomeScreen() {
        addFragment(new HomeFragment(), HomeFragment.class.getSimpleName(),false);
    }

    public void toPlanUserFragment()
    {
        addFragment(new PlanUserFragment(),PlanUserFragment.class.getSimpleName(),true);
    }

    public void toScanFragment(){
        addFragment(new ScanFragment(),ScanFragment.class.getSimpleName(),false);
    }

    public void toCollectorsScanFragment(){
        addFragment(new ScanCollectorFragment(),ScanCollectorFragment.class.getSimpleName(),false);
    }

    public void toUserCard() {
        addFragment(new UserCardFragment(),UserCardFragment.class.getSimpleName(),false);
    }

    public void toCreateUser() {
        addFragment(new CreateUserFragment(),CreateUserFragment.class.getSimpleName(),false);
    }

    public void toSetFragment(){
        addFragment(new PlanASetFragment(),PlanASetFragment.class.getSimpleName(),true);
    }





}
