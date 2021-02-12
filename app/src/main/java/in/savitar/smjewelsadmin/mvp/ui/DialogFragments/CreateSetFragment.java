package in.savitar.smjewelsadmin.mvp.ui.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.savitar.smjewelsadmin.MainActivity;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentCreateSetBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtilMain;


public class CreateSetFragment extends DialogFragment implements MainActivityContract.View {

   FragmentCreateSetBinding mBinding;
   MainActivityPresenter mainActivityPresenter;

   MainActivity activity;

    public static final String MyPREFERENCES = "MyPrefs" ;

    public CreateSetFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_set,container,false);
        mainActivityPresenter = new MainActivityPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        activity = new MainActivity();

        mBinding.createSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSet();
            }
        });

    }

    private void createSet() {

        if (mBinding.setNameEdit.getText().toString().compareToIgnoreCase("") != 0){
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("SetName",mBinding.setNameEdit.getText().toString());
            editor.commit();
            NavigationUtilMain.INSTANCE.toCreateUser();
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