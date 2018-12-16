package ch.epfl.sweng.studdybuddy.tools;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;

public class UserArrayAdapter extends ArrayAdapter<User> {

    private Context context;
    private int resourceId;
    private List<User> items, tempItems, suggestions;

    public UserArrayAdapter(@NonNull Context context, int resourceId, List<User> items) {
        super(context, resourceId, items);

        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            User user = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.text_user_custom_row);
            name.setText(user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            User user = (User) resultValue;
            return user.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (User user: items) {
                    if (user.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(user);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<User> tempValues = (ArrayList<User>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (User userObj : tempValues) {
                    add(userObj);
                    notifyDataSetChanged();
                }

            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
