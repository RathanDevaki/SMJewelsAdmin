package in.savitar.smjewelsadmin.mvp.ui.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.savitar.smjewelsadmin.Adapters.ViewPagerAdapter;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentHomeBinding;
import in.savitar.smjewelsadmin.mvp.ui.Collectors.CollectorsFragment;
import in.savitar.smjewelsadmin.mvp.ui.Transactions.TransactionsFragment;
import in.savitar.smjewelsadmin.mvp.ui.Account.AccountFragment;


public class HomeFragment extends Fragment implements MainActivityContract.View {

    MainActivityPresenter mPresenter;
    FragmentHomeBinding mBinding;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        setupViewPager(mBinding.mainViewPager);

        //Setting OnClick Listener to Bottom Bar
        mBinding.bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard_menu:
                        mBinding.mainViewPager.setCurrentItem(0);
                        break;
                    case R.id.collectors_menu:
                        mBinding.mainViewPager.setCurrentItem(1);
                        break;
                    case R.id.account_menu:
                        mBinding.mainViewPager.setCurrentItem(2);
                        break;
                    case R.id.transactions_menu:
                        mBinding.mainViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        //Level-1 Fragments
        DashboardFragment dashboardFragment =new DashboardFragment();
        CollectorsFragment collectorsFragment =new CollectorsFragment();
        AccountFragment accountFragment =new AccountFragment();
        TransactionsFragment notificationsFragment = new TransactionsFragment();

        adapter.addFragment(dashboardFragment);
        adapter.addFragment(collectorsFragment);
        adapter.addFragment(accountFragment);
        adapter.addFragment(notificationsFragment);

        //Level 2 Fragments
//        PlanUserFragment planUsersFragment = new PlanUserFragment();
//        adapter.addFragment(planUsersFragment);
//        adapter.addFragment(collectorsFragment);
//        adapter.addFragment(usersFragment);
//        adapter.addFragment(notificationsFragment);

        viewPager.setAdapter(adapter);
    }



    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}