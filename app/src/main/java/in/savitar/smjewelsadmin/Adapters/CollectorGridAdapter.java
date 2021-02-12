package in.savitar.smjewelsadmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.savitar.smjewelsadmin.MainActivity;
import in.savitar.smjewelsadmin.ModalClasses.PlanListModal;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.mvp.ui.Collectors.CollectorsInfoFragment;

public class CollectorGridAdapter extends ArrayAdapter<PlanListModal> {

    Context context;

    public CollectorGridAdapter(@NonNull Context context, int resource, @NonNull List<PlanListModal> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_plan_user_grid, parent, false);
        }

        final PlanListModal modal = getItem(position);

        CircleImageView photo = convertView.findViewById(R.id.singleUserPhotoGrid);
        TextView name = convertView.findViewById(R.id.singleUserNameGrid);
        TextView id = convertView.findViewById(R.id.singleUserIDGrid);

        name.setText(modal.getName());

        try{
            id.setText(modal.getCollector_ID().toString());
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
                CollectorsInfoFragment fragment = new CollectorsInfoFragment();
                Bundle args = new Bundle();
                args.putString("Name",modal.getName());
                args.putString("ID",modal.getCollector_ID());
                args.putString("Photo",modal.getProfilePhoto());
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
