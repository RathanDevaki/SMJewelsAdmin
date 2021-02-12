package in.savitar.smjewelsadmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

import in.savitar.smjewelsadmin.ModalClasses.TransactionsModal;
import in.savitar.smjewelsadmin.R;

public class TransactionsAdapter extends ArrayAdapter<TransactionsModal> {


    public TransactionsAdapter(@NonNull Context context, int resource, @NonNull List<TransactionsModal> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_transaction_list, parent, false);
        }

        TransactionsModal modal = getItem(position);
        TextView amount = convertView.findViewById(R.id.single_transaction_amount);
        TextView comments = convertView.findViewById(R.id.single_transaction_comment);
        TextView date = convertView.findViewById(R.id.single_transaction_date);
        TextView id = convertView.findViewById(R.id.single_transaction_id);

        amount.setText("Rs." + modal.getAmount() + "/-");
        comments.setText(modal.getComments());
        date.setText(modal.getDate());
        id.setText(modal.getUserID());


        return convertView;


    }
}
