package in.savitar.smjewelsadmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.savitar.smjewelsadmin.MainActivity;
import in.savitar.smjewelsadmin.ModalClasses.PlanListModal;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.mvp.ui.Collectors.CollectorsInfoFragment;
import in.savitar.smjewelsadmin.mvp.ui.Dashboard.UserProfileFragment;


public class PlanUserGridAdapter extends ArrayAdapter<PlanListModal> {

    Context context;
    String planName;
    String setName;
    List<String> userKeys = new ArrayList<>();


    public PlanUserGridAdapter(@NonNull Context context, int resource, @NonNull List<PlanListModal> objects, String planName,
                               String setName, List<String> userKeys) {
        super(context, resource, objects);
        this.context = context;

        this.planName = planName;
        this.setName = setName;
        this.userKeys = userKeys;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_plan_user_grid, parent, false);
        }

        final PlanListModal modal = getItem(position);

        CircleImageView photo = convertView.findViewById(R.id.singleUserPhotoGrid);
        TextView name = convertView.findViewById(R.id.singleUserNameGrid);
        TextView id = convertView.findViewById(R.id.singleUserIDGrid);

        Log.v("TAG","ID=>"+modal.getID());
        name.setText(modal.getName());

        try{
            id.setText(modal.getID().toString());
        } catch (Exception e){
            Log.v("TAG","Position=>"+position);
        }


        if (modal.getProfilePhoto() != null){
            Glide.with(getContext()).load(modal.getProfilePhoto()).into(photo);
        }




        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NavigationUtilityMain.INSTANCE.toUserDetailsFragment();
                UserProfileFragment fragment = new UserProfileFragment();
                Bundle args = new Bundle();
                args.putString("Name",modal.getName());
                args.putString("ID",modal.getID().toString());
                args.putString("Photo",modal.getProfilePhoto());
                args.putString("PlanName",planName);
                args.putString("UserKey",userKeys.get(position));
                if (planName.compareToIgnoreCase("PlanA")==0){
                    args.putString("SetName","Set1");
                }
                fragment.setArguments(args);
                FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return convertView;
    }
}
