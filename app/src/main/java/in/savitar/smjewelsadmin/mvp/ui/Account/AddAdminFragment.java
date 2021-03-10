package in.savitar.smjewelsadmin.mvp.ui.Account;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.databinding.FragmentAddAdminBinding;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityContract;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.MainActivityPresenter;


public class AddAdminFragment extends DialogFragment implements MainActivityContract.View {

    FragmentAddAdminBinding mBinding;
    MainActivityPresenter mPresenter;
     String winner1 = null, winner2 = null, photo1 = null, photo2 = null;
     String[] winnerID=new String[2];
     String[] winnerName = new String[2];
     String[] winnerPlan = new String[2];
    String[] winnerSet = new String[2];
    String[] winnerPhoto = new String[2];
    String monthYear="";
    String drawOne="";
    String drawTwo="";
    int flag=0;
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
    private void progressVisible()
    {
        mBinding.addDrawWinner.setVisibility(View.GONE);
        mBinding.progressBar.setVisibility(View.VISIBLE);

    }
    private void progressGone()
    { mBinding.drawWinnerOne.setBackgroundResource(R.drawable.edittext_grey_border);
        mBinding.drawWinnerTwo.setBackgroundResource(R.drawable.edittext_grey_border);
        mBinding.addDrawWinner.setVisibility(View.VISIBLE);
        mBinding.progressBar.setVisibility(View.GONE);
    }
    private void init() {

        monthYear=getDateAndTime();
        mBinding.drawWinnerDate.setText(monthYear);
        mBinding.addDrawWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressVisible();
                drawOne=mBinding.drawWinnerOne.getText().toString();
                drawTwo=mBinding.drawWinnerTwo.getText().toString();
                Log.v("Dr1",drawOne);
                pushDrawWinners();
            }
        });


    }
    private String getDateAndTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        Log.v("Month date",currentDateandTime);
        return currentDateandTime;
    }
    public void pushDrawWinners() {

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("UsersList");
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("PlanA");


        if (mBinding.drawWinnerOne.getText().toString().compareToIgnoreCase("") == 0) {
            //  Toasty.error(getContext(),"Enter Winner 1").show();
            mBinding.drawWinnerOne.setError("Enter Winner1 ID");
            mBinding.drawWinnerOne.setBackgroundResource(R.drawable.edittext_red_border);
            progressGone();
        } else if (mBinding.drawWinnerTwo.getText().toString().compareToIgnoreCase("") == 0) {
            // Toasty.error(getContext(),"Enter Winner 2").show();
            mBinding.drawWinnerTwo.setError("Enter Winner2 ID");
            mBinding.drawWinnerTwo.setBackgroundResource(R.drawable.edittext_red_border);
            progressGone();
        } else {
            flag=1;
        }
        if(flag==1){
            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(drawOne) && snapshot.hasChild(drawTwo))
                    {
                        winnerID[0] = snapshot.child(drawOne).child("UserID").getValue(String.class);
                    winnerID[1] = snapshot.child(drawTwo).child("UserID").getValue(String.class);

                    winnerPlan[0] = snapshot.child(drawOne).child("PlanName").getValue(String.class);
                    winnerPlan[1] = snapshot.child(drawTwo).child("PlanName").getValue(String.class);

                    winnerSet[0] = snapshot.child(drawOne).child("SetName").getValue(String.class);
                    winnerSet[1] = snapshot.child(drawTwo).child("SetName").getValue(String.class);
                    Log.v("Winner Plan", winnerID[0] + "--" + winnerID[1]);
                    Log.v("Winner Plan", winnerPlan[0] + "--" + winnerPlan[1]);
                    Log.v("Winner Set", winnerSet[0] + "--" + winnerSet[1]);


                    if (winnerPlan[0].compareToIgnoreCase("PlanA") != 0) {
                        Toasty.error(getContext(), "1'st winner is not belongs to Plan-A").show();
                        mBinding.drawWinnerOne.setBackgroundResource(R.drawable.edittext_red_border);
                        progressGone();
                    } else if (winnerPlan[1].compareToIgnoreCase("PlanA") != 0) {
                        Toasty.error(getContext(), "2'nd winner is not belongs to Plan-A").show();
                        mBinding.drawWinnerTwo.setBackgroundResource(R.drawable.edittext_red_border);
                        progressGone();
                    } else {
                        DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("PlanA").child("UsersList").child("Set1");//.child(winnerSet[0]);

                        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.v("oh god", "godd");
                                winnerName[0] = snapshot.child(winnerID[0]).child("Name").getValue(String.class);
                                winnerName[1] = snapshot.child(winnerID[1]).child("Name").getValue(String.class);
                                winnerPhoto[0] = snapshot.child(winnerID[0]).child("ProfilePhoto").getValue(String.class);
                                winnerPhoto[1] = snapshot.child(winnerID[1]).child("ProfilePhoto").getValue(String.class);
                                Log.v("Winner name 1 & two", winnerName[0] + "--" + winnerName[1]);

                                final String pushKey = databaseReference2.push().getKey();
                                HashMap<String, String> winnersMap = new HashMap<>();
                                winnersMap.put("UserID1", drawOne);
                                winnersMap.put("UserID2", drawTwo);
                                winnersMap.put("Month", monthYear);
                                winnersMap.put("Winner1", winnerName[0]);
                                winnersMap.put("Winner2", winnerName[1]);
                                winnersMap.put("Photo1", winnerPhoto[0]);
                                winnersMap.put("Photo2", winnerPhoto[1]);
                                databaseReference.child("DrawWinners").child(pushKey).setValue(winnersMap);

                                Toasty.success(getContext(), "Draw Winners Added Successfully").show();
                                progressVisible();
                                dismiss();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                    else
                    {
                        Toasty.error(getContext(),"Invalid User ID",Toasty.LENGTH_LONG).show();
                        progressGone();
                    }
            }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException();
                }
            });
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