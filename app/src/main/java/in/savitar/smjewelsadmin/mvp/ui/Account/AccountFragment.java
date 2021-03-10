package in.savitar.smjewelsadmin.mvp.ui.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentAccountBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;
import in.savitar.smjewelsadmin.mvp.ui.DialogFragments.UpdateGoldDialogFragment;


public class AccountFragment extends Fragment  implements MainActivityContract.View {

    FragmentAccountBinding mBinding;
    MainActivityPresenter mPresenter;

    public AccountFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false);
        mPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        mBinding.logOutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.signOut(getActivity());
            }
        });

        mBinding.clearDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDaily();
            }
        });

        mBinding.clearWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearWeekly();
            }
        });
        
        mBinding.clearMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMonthly();
            }
        });

        mBinding.updateGoldPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGoldPrice();
            }
        });

        mBinding.addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAdmin();
            }
        });
    }

    private void createAdmin() {

        AddAdminFragment dialogFragment = new AddAdminFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");



    }

    private void updateGoldPrice() {

        UpdateGoldDialogFragment dialogFragment = new UpdateGoldDialogFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");

    }

    private void clearMonthly() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure");
        builder.setMessage("You want to clear monthly earnings record?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("MonthlyEarnings");
                databaseReference.setValue(0);
                Toasty.success(getContext(),"Monthly Record has been reset").show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    private void clearWeekly() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure");
        builder.setMessage("You want to clear weekly earnings record?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("WeeklyEarnings");
                databaseReference.setValue(0);
                Toasty.success(getContext(),"Weekly Record has been reset").show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    private void clearDaily() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure");
        builder.setMessage("You want to clear daily earnings record?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("DailyEarnings");
                databaseReference.setValue(0);
                Toasty.success(getContext(),"Daily Record has been reset").show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}