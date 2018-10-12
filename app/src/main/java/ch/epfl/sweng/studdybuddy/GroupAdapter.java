package ch.epfl.sweng.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.epfl.sweng.studdybuddy.CourseHolder;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupHolder> {

        List<String> list;
        public GroupAdapter(List<String> list)
        {
            this.list = list;
        }
        @Override
        public GroupHolder onCreateViewHolder(ViewGroup viewGroup, int itemType)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_card, viewGroup, false);
            return new GroupHolder(view);
        }
        @Override
        public void onBindViewHolder(GroupHolder holder, int position)
        {
            String course = list.get(position);
            holder.bind(course);
        }
        @Override
        public int getItemCount()
        {
            return list.size();
        }
}


