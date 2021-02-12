package in.savitar.smjewelsadmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.List;

import in.savitar.smjewelsadmin.MainActivity;
import in.savitar.smjewelsadmin.R;
import in.savitar.smjewelsadmin.mvp.utils.NavigationUtilMain;

public class PlanSetAdapter extends ArrayAdapter<String> {

    public List<String> categories;
    Activity activity;
    public TextView category;
    List<String> setCount;

    public PlanSetAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, List<String> setCount,Activity activity) {
        super(context, resource, objects);
        this.activity = activity;
        this.categories = objects;
        this.setCount = setCount;
    }

    @Override
    public int getCount() {
        try {
            return categories.size();
        }catch (Exception e){
            return 20;
        }
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_set, parent, false);
        }

        category = convertView.findViewById(R.id.single_blog_category_text);
        ImageView imageView = convertView.findViewById(R.id.single_blog_category_image);
        CardView cardView = convertView.findViewById(R.id.single_blog_category_card);
        TextView setcountTV = convertView.findViewById(R.id.single_count_text);

        category.setText(categories.get(position));
        setcountTV.setText(setCount.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtilMain.INSTANCE.toPlanUserFragment();
                ((MainActivity)activity).planName = "PlanA";
                ((MainActivity)activity).setName = categories.get(position);
            }
        });



       return convertView;
    }
}
