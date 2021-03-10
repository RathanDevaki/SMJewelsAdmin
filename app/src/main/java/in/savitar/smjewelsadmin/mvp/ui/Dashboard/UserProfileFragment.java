package in.savitar.smjewelsadmin.mvp.ui.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.savitar.smjewelsadmin.Adapters.TransactionsAdapter;
import in.savitar.smjewelsadmin.ModalClasses.TransactionsModal;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentUserProfileBinding;


public class UserProfileFragment extends Fragment implements MainActivityContract.View {

    FragmentUserProfileBinding mBinding;
    MainActivityPresenter mPresenter;

    String planName;
    String setName;
    String userKey;



    public UserProfileFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        mBinding.userNameDashboard.setText(getArguments().getString("Name"));
        mBinding.userIdDashboard.setText(getArguments().getString("ID"));

        planName = getArguments().getString("PlanName");
        userKey = getArguments().getString("UserKey");

        if (getArguments().getString("SetName")!=null){
            setName = getArguments().getString("SetName");
        }

        mBinding.planNameProgress.setText(planName);

        fetchBasicData();
    }

    private void fetchBasicData() {

        if (planName.compareToIgnoreCase("PlanA") == 0){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            databaseReference.child(planName).child("UsersList").child(setName).child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (getActivity() == null) {
                        return;
                    }

                    if (snapshot.hasChild("ProfilePhoto")){
                        Glide
                                .with(getActivity())
                                .load(snapshot.child("ProfilePhoto").getValue(String.class))
                                .into(mBinding.userPhotoDashboard);
                    } else {
                        Glide
                                .with(getActivity())
                                .load("https://firebasestorage.googleapis.com/v0/b/sm-jewels.appspot.com/o/img_162044.png?alt=media&token=c5445416-61d0-4ea7-90e7-a77a5e65cd09")
                                .into(mBinding.userPhotoDashboard);
                    }
                    if (snapshot.hasChild("TotalMonths")){
                        long totalMonths = snapshot.child("TotalMonths").getValue(Long.class);
                        long completedMonths = snapshot.child("CompletedMonths").getValue(Long.class);
                        long percentageCompleted = (completedMonths*100)/totalMonths;
                        int _completedPercentage = (int)percentageCompleted;

                        mBinding.progressPercentage.setText(String.valueOf(_completedPercentage) + "% Completed");
                        mBinding.planProgress.setProgress(_completedPercentage);


                        mBinding.progressPercentageMonths.setText(String.valueOf(completedMonths) + "/" + String.valueOf(totalMonths) + " Months");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            final List<TransactionsModal> list = new ArrayList<>();
            final TransactionsAdapter adapter = new TransactionsAdapter(getContext(),R.layout.single_transaction_list,list);
            mBinding.transactionsList.setAdapter(adapter);

            databaseReference.child(planName).child("UsersList").child(setName).child(userKey).child("Transactions")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            TransactionsModal modal = snapshot.getValue(TransactionsModal.class);
                            list.add(modal);
                            Collections.reverse(list);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }


    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}