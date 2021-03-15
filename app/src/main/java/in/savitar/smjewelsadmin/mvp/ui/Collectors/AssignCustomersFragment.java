package in.savitar.smjewelsadmin.mvp.ui.Collectors;

import android.app.Dialog;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentAddAdminBinding;
import in.savitar.smjewelsadmin.databinding.FragmentAssignCustomersBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;

public class AssignCustomersFragment extends Fragment {
    FragmentAssignCustomersBinding mBinding;
    MainActivityPresenter mPresenter;
    String winner1 = null, winner2 = null, photo1 = null, photo2 = null;
    String[] winnerID=new String[2];
    String[] winnerName = new String[2];
    String[] winnerPlan = new String[2];
    String[] winnerSet = new String[2];
    String[] winnerPhoto = new String[2];
    String monthYear="";
    String drawOne="";
    String customer_id="";
    int flag=0;
    public AssignCustomersFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_assign_customers, container, false);

        init();
        return mBinding.getRoot();
    }

    private void progressVisible()
    {
        mBinding.assignCustomers.setVisibility(View.GONE);
        mBinding.progressBar.setVisibility(View.VISIBLE);

    }
    private void progressGone()
    {
        mBinding.collectorId.setBackgroundResource(R.drawable.edittext_grey_border);
        mBinding.customerId.setBackgroundResource(R.drawable.edittext_grey_border);
        mBinding.assignCustomers.setVisibility(View.VISIBLE);
        mBinding.progressBar.setVisibility(View.GONE);
    }
    private void init() {

       String collector_id="202008001";
        mBinding.collectorId.setText(collector_id);
        mBinding.assignCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressVisible();
                customer_id=mBinding.customerId.getText().toString();
                Log.v("Dr1",customer_id);
                assignCustomers();
            }
        });


    }
    private void assignCustomers(){
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("UsersList");
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("CollectorsInfo");

        if (mBinding.customerId.getText().toString().compareToIgnoreCase("") == 0) {
            //  Toasty.error(getContext(),"Enter Winner 1").show();
            mBinding.customerId.setError("Enter Customer ID");
            mBinding.customerId.setBackgroundResource(R.drawable.edittext_red_border);
            progressGone();
        } else {
            flag=1;
        }
        if(flag==1){
            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(customer_id))
                    {
                      Toasty.success(getContext(),"Customer xists",Toasty.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toasty.error(getContext(),"Invalid Customer ID",Toasty.LENGTH_LONG).show();
                        progressGone();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException();
                }
            });
        }
    }

}
