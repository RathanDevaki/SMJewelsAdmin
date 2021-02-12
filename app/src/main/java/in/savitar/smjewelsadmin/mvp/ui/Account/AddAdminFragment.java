package in.savitar.smjewelsadmin.mvp.ui.Account;

import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentAddAdminBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;


public class AddAdminFragment extends DialogFragment implements MainActivityContract.View {

    FragmentAddAdminBinding mBinding;
    MainActivityPresenter mPresenter;


    public AddAdminFragment() {
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
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_admin, container, false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {


        mBinding.signUpAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAdmin();
            }
        });


    }

    private void createAdmin() {

        if (mBinding.adminIdSignup.getText().toString().compareToIgnoreCase("") == 0){
            Toasty.error(getContext(),"Enter ID").show();
        } else if (mBinding.adminNameSignUp.getText().toString().compareToIgnoreCase("") == 0) {
            Toasty.error(getContext(),"Enter Name").show();
        } else if (mBinding.adminPhoneSignUp.getText().toString().compareToIgnoreCase("") == 0){
            Toasty.error(getContext(),"Enter Phone").show();
        } else {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("AdminInfo");

            HashMap<String,String> adminMap = new HashMap<>();
            adminMap.put("Name",mBinding.adminNameSignUp.getText().toString());
            adminMap.put("Phone",mBinding.adminPhoneSignUp.getText().toString());

            databaseReference.child(mBinding.adminIdSignup.getText().toString()).setValue(adminMap);

            Toasty.success(getContext(),"Admin Created Successfully").show();

            dismiss();

        }

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