package in.savitar.smjewelsadmin.mvp.ui.DialogFragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentUpdateGoldDialogBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;


public class UpdateGoldDialogFragment extends DialogFragment implements MainActivityContract.View {


    FragmentUpdateGoldDialogBinding mBinding;
    MainActivityPresenter mPresenter;

    public UpdateGoldDialogFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_gold_dialog, container, false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        getGoldPrice();

        mBinding.updateGoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.goldRateEdit.getText().toString().isEmpty()){
                    Toasty.success(getContext(),"Please enter new Rate").show();
                }else {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("PlanB")
                            .child("GoldRate");
                    databaseReference.setValue(Double.parseDouble(mBinding.goldRateEdit.getText().toString()));
                    Toasty.success(getContext(),"Updated Successfully").show();
                    dismiss();
                }
            }
        });


    }

    private void getGoldPrice() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("PlanB")
                .child("GoldRate");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.currentGoldRate.setText("Rs." + String.valueOf(Double.valueOf(snapshot.getValue(Long.class))) + "/g");
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

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}