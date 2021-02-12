package in.savitar.smjewelsadmin.mvp.ui.Transactions;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.savitar.smjewelsadmin.Adapters.TransactionsAdapter;
import in.savitar.smjewelsadmin.ModalClasses.TransactionsModal;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentTransactionsBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;

public class TransactionsFragment extends Fragment implements MainActivityContract.View {
    
    FragmentTransactionsBinding mBinding;
    MainActivityPresenter mPresenter;

    public TransactionsFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transactions, container, false);
        mPresenter = new MainActivityPresenter(this);
        initialize();
        return mBinding.getRoot();
    }

    private void initialize() {

        final List<TransactionsModal> list = new ArrayList<>();
        final TransactionsAdapter adapter = new TransactionsAdapter(getContext(), R.layout.single_transaction_list, list);
        mBinding.collectionsListView.setAdapter(adapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("TransactionDetails")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        adapter.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Log.v("TAG","NOde=>"+ds.getKey());
                            TransactionsModal modal = ds.getValue(TransactionsModal.class);
                            list.add(modal);
                        }
                        Collections.reverse(list);
                        adapter.notifyDataSetChanged();
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