package in.savitar.smjewelsadmin.mvp.ui.Collectors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.savitar.smjewelsadmin.Adapters.CollectorGridAdapter;
import in.savitar.smjewelsadmin.Adapters.CollectorSearch;
import in.savitar.smjewelsadmin.Adapters.PlanUserGridAdapter;
import in.savitar.smjewelsadmin.Adapters.PlanUserSearch;
import in.savitar.smjewelsadmin.MainActivity;
import in.savitar.smjewelsadmin.ModalClasses.PlanListModal;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentCollectorsBinding;
import in.savitar.smjewelsadmin.databinding.FragmentPlanUserBinding;
import in.savitar.smjewelsadmin.mvp.ui.Account.AddAdminFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.PlanUserFragment;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtilMain;


public class CollectorsFragment extends Fragment implements MainActivityContract.View {

    private CollectorGridAdapter gridAdapter;
    MainActivity activity;
    private CollectorSearch mAdapter;

    FragmentCollectorsBinding mBinding;
    MainActivityPresenter mPresenter;

    private List<PlanListModal> contactList;

    public CollectorsFragment() {
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
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_collectors,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        contactList = new ArrayList<>();
        mAdapter = new CollectorSearch(getContext(), contactList);

        //Recycler view for searching user
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mBinding.collectorListView.setLayoutManager(mLayoutManager);
        mBinding.collectorListView.setItemAnimator(new DefaultItemAnimator());
        mBinding.collectorListView.setAdapter(mAdapter);

        gridAdapter = new CollectorGridAdapter(getContext(),R.layout.single_plan_user_grid,contactList);
        mBinding.collectorsGrid.setAdapter(gridAdapter);



        //Search Implementation
        mBinding.searchCollectorET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    mBinding.collectorListView.setVisibility(View.VISIBLE);
                } else {
                    mBinding.collectorListView.setVisibility(View.GONE);
                }
                CollectorsFragment.this.mAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBinding.scanCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtilMain.INSTANCE.toCollectorsScanFragment();
            }
        });

        mBinding.addCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCollector();
            }
        });

        fetchData();

    }

    private void createCollector() {

        CreateCollectorFragment dialogFragment = new CreateCollectorFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");

    }

    private void fetchData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("CollectorsInfo");

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot ds:snapshot.getChildren()){
                   PlanListModal modal = ds.getValue(PlanListModal.class);
                   contactList.add(modal);
               }
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