package in.savitar.smjewelsadmin.mvp.ui.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import in.savitar.smjewelsadmin.Adapters.PlanUserGridAdapter;
import in.savitar.smjewelsadmin.Adapters.PlanUserSearch;
import in.savitar.smjewelsadmin.MainActivity;
import in.savitar.smjewelsadmin.ModalClasses.PlanListModal;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentPlanUserBinding;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtilMain;


public class PlanUserFragment extends Fragment implements MainActivityContract.View {

    private PlanUserGridAdapter gridAdapter;
    MainActivity activity;
    private PlanUserSearch mAdapter;
    List<String> keys = new ArrayList<>();

    FragmentPlanUserBinding mBinding;
    MainActivityPresenter mPresenter;

    private List<PlanListModal> contactList;

    public PlanUserFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_plan_user,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        contactList = new ArrayList<>();

        if (activity.planName.compareToIgnoreCase("PlanA") == 0){
            mAdapter = new PlanUserSearch(getContext(), contactList,keys,activity.planName,"Set1");
        } else {
            mAdapter = new PlanUserSearch(getContext(), contactList,keys,activity.planName,"");
        }

        //Recycler view for searching user
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mBinding.planUserListView.setLayoutManager(mLayoutManager);
        mBinding.planUserListView.setItemAnimator(new DefaultItemAnimator());
        mBinding.planUserListView.setAdapter(mAdapter);

        if (activity.planName.compareToIgnoreCase("PlanA") == 0){
            gridAdapter = new PlanUserGridAdapter(getContext(),R.layout.single_plan_user_grid,contactList,activity.planName,"Set1",keys);
        } else {
            gridAdapter = new PlanUserGridAdapter(getContext(),R.layout.single_plan_user_grid,contactList,activity.planName,"",keys);
        }


        mBinding.planUsersGrid.setAdapter(gridAdapter);



        fetchData();

        //Search Implementation
        mBinding.searchUserET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    mBinding.planUserListView.setVisibility(View.VISIBLE);
                } else {
                    mBinding.planUserListView.setVisibility(View.GONE);
                }
                PlanUserFragment.this.mAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBinding.scanPlanUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtilMain.INSTANCE.toScanFragment();
            }
        });

        mBinding.addPlanUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtilMain.INSTANCE.toCreateUser();
            }
        });

    }

    private void fetchData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(activity.planName);

        databaseReference.child("UsersList").child("Set1").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PlanListModal modal = snapshot.getValue(PlanListModal.class);
                keys.add(snapshot.getKey());
                contactList.add(modal);
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

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}