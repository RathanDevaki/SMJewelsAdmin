package in.savitar.smjewelsadmin.mvp.ui.Collectors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import in.savitar.smjewelsadmin.databinding.FragmentCollectorsInfoBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;


public class CollectorsInfoFragment extends Fragment implements MainActivityContract.View{

    FragmentCollectorsInfoBinding mBinding;
    MainActivityPresenter mPresenter;

    int collectedAmount = 0;



    public CollectorsInfoFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_collectors_info,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        mBinding.collectorName.setText(getArguments().getString("Name"));
        mBinding.collectorID.setText(getArguments().getString("ID"));

        final List<TransactionsModal> list = new ArrayList<>();
        final TransactionsAdapter adapter = new TransactionsAdapter(getContext(), R.layout.single_transaction_list, list);
        mBinding.collectionsListView.setAdapter(adapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("CollectorsInfo").child(getArguments().getString("ID")).child("Transactions")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mBinding.totalTransactionsCount.setText(String.valueOf(snapshot.getChildrenCount()));
                        collectedAmount = 0;
                        adapter.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            collectedAmount = collectedAmount + Integer.parseInt(snapshot.child(ds.getKey()).child("Amount").getValue(String.class));
                            TransactionsModal modal = ds.getValue(TransactionsModal.class);
                            list.add(modal);
                        }

                        Collections.reverse(list);
                        adapter.notifyDataSetChanged();
                        mBinding.totalCollectedAmount.setText("Rs." + String.valueOf(collectedAmount) + "/-");
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