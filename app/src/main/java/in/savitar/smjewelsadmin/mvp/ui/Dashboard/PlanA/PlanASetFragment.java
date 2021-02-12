package in.savitar.smjewelsadmin.mvp.ui.Dashboard.PlanA;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.savitar.smjewelsadmin.Adapters.PlanSetAdapter;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentPlanASetBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;
import in.savitar.smjewelsadmin.mvp.ui.DialogFragments.CreateSetFragment;
import in.savitar.smjewelsadmin.mvp.ui.DialogFragments.UpdateGoldDialogFragment;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtil;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtilMain;


public class PlanASetFragment extends Fragment implements MainActivityContract.View {


    FragmentPlanASetBinding mBinding;
    MainActivityPresenter mPresenter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> setList;
    PlanSetAdapter adapter;



    public PlanASetFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_plan_a_set,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        loadSetNames();

        mBinding.createSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewSet();
            }
        });

    }

    private void createNewSet() {

        CreateSetFragment dialogFragment = new CreateSetFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");


    }

    private void loadSetNames() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        setList = new ArrayList<>();
        final List<String> setCount = new ArrayList<>();
        adapter = new PlanSetAdapter(getContext(),R.layout.single_set,setList,setCount,getActivity());


        databaseReference.child("PlanA").child("UsersList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setList.clear();
                adapter.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    setList.add(ds.getKey());
                    setCount.add(String.valueOf(snapshot.child(ds.getKey()).getChildrenCount()));
                    adapter.notifyDataSetChanged();
                }
                mBinding.planSetListView.setAdapter(adapter);
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