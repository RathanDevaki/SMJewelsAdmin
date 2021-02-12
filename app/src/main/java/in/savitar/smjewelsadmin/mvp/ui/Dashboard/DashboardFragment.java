package in.savitar.smjewelsadmin.mvp.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import in.savitar.smjewelsadmin.MainActivity;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentDashboardBinding;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtil;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtilMain;


public class DashboardFragment extends Fragment implements View.OnClickListener, MainActivityContract.View {

    //Firebase Connections
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    MainActivity activity;

    FragmentDashboardBinding mBinding;
    MainActivityPresenter mPresenter;
    public static final String MyPREFERENCES = "MyPrefs" ;


    public DashboardFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }


    private void init() {

        //mPresenter.showDialog(getContext());

        activity = new MainActivity();
        //Firebase Init
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        //Setting plan A Earnings
        reference.child("PlanA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.planATv.setText("Rs." + getFormatedAmount((Long) snapshot.child("TotalEarnings").getValue()) + "/-");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Setting plan B Earnings
        reference.child("PlanB").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.planBTv.setText("Rs." + getFormatedAmount((Long) snapshot.child("TotalEarnings").getValue()) + "/-");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Setting plan C Earnings
        reference.child("PlanC").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.planCTv.setText("Rs." + getFormatedAmount((Long) snapshot.child("TotalEarnings").getValue()) + "/-");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Setting Daily Earnings
        reference.child("DailyEarnings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.dailyEarningsTV.setText("Rs." + getFormatedAmount((Long) snapshot.getValue()) + "/-");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Setting Daily Earnings
        reference.child("WeeklyEarnings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.weeklyEarningsTV.setText("Rs." + getFormatedAmount((Long) snapshot.getValue()) + "/-");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Setting Daily Earnings
        reference.child("MonthlyEarnings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.monthlyEarningsTV.setText("Rs." + getFormatedAmount((Long) snapshot.getValue()) + "/-");
                //mPresenter.hideDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mBinding.planACard.setOnClickListener(this);
        mBinding.planBCard.setOnClickListener(this);
        mBinding.planCCard.setOnClickListener(this);

        pushDummyData();

        calculateTotalEarnings();


    }

    private void calculateTotalEarnings() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("PlanA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Long planAEarnings = snapshot.child("TotalEarnings").getValue(Long.class);
                databaseReference.child("PlanB").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final Long planBEarnings = snapshot.child("TotalEarnings").getValue(Long.class);
                        databaseReference.child("PlanC").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Long planCEarnings = snapshot.child("TotalEarnings").getValue(Long.class);
                                Long totalEarnings = planAEarnings + planBEarnings + planCEarnings;
                                mBinding.totalEarningsTv.setText("Rs." + getFormatedAmount(totalEarnings) + "/-");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void pushDummyData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList");

    }

    private String getFormatedAmount(Long amount) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return removeDecimal(format.format(new BigInteger(String.valueOf(amount))));
    }

    private String removeDecimal(String amount) {

       // String[] _amount = amount.split("\\s+");
        //Log.v("TAG", _amount[1]);
        String[] _amount_ = amount.split("\\.");
        return _amount_[0];

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.plan_a_card:
                NavigationUtilMain.INSTANCE.toSetFragment();
                activity.planName = "PlanA";
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("PlanName","PlanA");
                editor.commit();
                break;
            case R.id.plan_b_card:
                NavigationUtilMain.INSTANCE.toPlanUserFragment();
                activity.planName = "PlanB";
                SharedPreferences sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.putString("PlanName","PlanB");
                editor1.commit();
                break;
            case R.id.plan_c_card:
                NavigationUtilMain.INSTANCE.toPlanUserFragment();
                activity.planName = "PlanC";
                SharedPreferences sharedpreferences2 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedpreferences2.edit();
                editor2.putString("PlanName","PlanC");
                editor2.commit();
                break;
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}