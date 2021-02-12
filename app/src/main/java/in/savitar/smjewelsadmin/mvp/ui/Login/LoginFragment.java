package in.savitar.smjewelsadmin.mvp.ui.Login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OtpTextView;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentLoginBinding;
import in.savitar.smjewelsadmin.mvp.ui.splash.SplashContract;
import in.savitar.smjewelsadmin.mvp.ui.splash.SplashPresenter;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtil;



public class LoginFragment extends Fragment implements SplashContract.View {

    SplashPresenter mPresenter;
    FragmentLoginBinding mBinding;

    //Bottom Sheet References
    OtpTextView otpTextView;
    Button verifyOtp;
    TextView otpTextMessage;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet;
    private String mVerificationId;
    FirebaseAuth mAuth;
    private ProgressBar progress;

    private String userUniqueID;
    private String Name;

    String phoneNumber;


    public LoginFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mPresenter = new SplashPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        //Initialize Ad Banner
        AdRequest adRequest = new AdRequest.Builder().build();
        mBinding.loginAdBanner.loadAd(adRequest);


        mAuth = FirebaseAuth.getInstance();
        //Bottom Sheet
        sheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet.bottomSheetOtp);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBinding.getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mBinding.userIdEt.getText()) || mBinding.userIdEt.getText().length() < 9) {
                    mBinding.userIdEt.setError("Invalid ID");
                } else {
                    getPhoneNumber(mBinding.userIdEt.getText().toString());
                }
            }
        });

        //Verify OTP Button
        mBinding.bottomSheet.verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.bottomSheet.otpView.getText().toString().length() < 5)
                {
                    Toast toasty=Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_LONG);
                    toasty.setGravity(Gravity.CENTER, 0, 0);
                    toasty.show();
                } else {
                    verifyVerificationCode(mBinding.bottomSheet.otpView.getText().toString());
                }
            }
        });

        displayImageSlider();


    }

    //To Load and display images into image slider
    private void displayImageSlider() {
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://images.freekaamaal.com/post_images/1569908385.png"));
        slideModels.add(new SlideModel("https://images.freekaamaal.com/post_images/1569908385.png"));
        slideModels.add(new SlideModel("https://images.freekaamaal.com/post_images/1569908385.png"));
        slideModels.add(new SlideModel("https://images.freekaamaal.com/post_images/1569908385.png"));
        mBinding.loginSlider.setImageList(slideModels, true);
    }

    public void getPhoneNumber(final String userID) {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("AdminInfo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userID)) {
                    phoneNumber = snapshot.child(userID).child("Phone").getValue(String.class);
                    //Toast.makeText(context,"Phone Number=>"+phoneNumber,Toast.LENGTH_LONG).show();
                    Name = snapshot.child(userID).child("Name").getValue(String.class);
                    Toasty.info(getContext(),"Hellow"+Name+" An OTP will be sent to your number",Toasty.LENGTH_SHORT).show();
                    //userUniqueID = snapshot.child(userID).child("UserID").getValue(String.class);
                    sendOtp(phoneNumber); // Sending OTP to the number
                } else {
                    Toasty.error(getContext(), "Admin Does not exist. Please Register", Toast.LENGTH_SHORT).show();
                    mBinding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendOtp(String phoneNumber) {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,   // Activity (for callback binding)
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            //progress.setVisibility(View.GONE);
            mBinding.progressBar.setVisibility(View.GONE);
            Toasty.success(getContext(), "verification completed", Toast.LENGTH_SHORT).show();
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                mBinding.bottomSheet.otpView.setText(code);
                //progress.setVisibility(View.GONE);
                //verifyOtp.setEnabled(true);
                //verifyButton.setBackgroundColor(getColor(R.color.primaryTrans));
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            //progress.setVisibility(View.GONE);
            mBinding.progressBar.setVisibility(View.GONE);
            Toasty.error(getContext(), "verification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            mBinding.progressBar.setVisibility(View.VISIBLE);
            super.onCodeSent(s, forceResendingToken);
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mVerificationId = s;
            mBinding.progressBar.setVisibility(View.GONE);
            mBinding.bottomSheet.otpMessageView.append(" " + phoneNumber);
            Toasty.info(getContext(), "OTP sent", Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        //signing the user
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()) {
                        //Code to set user session
                        saveUserID(); //Saves user ID and plan Name in shared preferences
                        NavigationUtil.INSTANCE.toMainActivity();
                    } else {

                        String message = "Somthing is wrong, we will fix it soon...";
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            // message = "Incorrect OTP";

                            Toast toasty=Toast.makeText(getContext(), "Incorrect OTP", Toast.LENGTH_LONG);
                            toasty.setGravity(Gravity.CENTER, 0, 0);
                            toasty.show();

                        }
                    }
                } catch (Exception e) {
                    Log.v("TAG", "OTP Exception" + e);
                    Toast.makeText(getContext(),"Enter OTP",Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private void saveUserID() { // Save user ID and Plan Name in shared Preferences for future use
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserID", mBinding.userIdEt.getText().toString());
        editor.commit();
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}