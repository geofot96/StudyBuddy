package ch.epfl.sweng.studdybuddy.tools;

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;

/**
 * An adapter specialized in Courses
 */
public class CourseAdapter extends Adapter {

    List<String> list;

    /**
     * Constructor of an adapter using a list of courses
     * @param list list of courses
     */
    public CourseAdapter(List<String> list) {
        super(R.layout.course_card, R.id.text);
        this.list = list;
    }

    /**
     * Assign the extracted information to the graphical holder
     * @param holder The holder of the graphical elements of the card
     * @param position the index of the element in the child list
     */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String course = list.get(position);
        holder.bind(course);
    }
    /**
     *
     * @return returns the size of the list of courses
     */
    @Override
    public int getItemCount() {
        return list.size();
    }


}
