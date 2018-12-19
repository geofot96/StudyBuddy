package ch.epfl.sweng.studdybuddy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.ChatRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

/**
 * A fragment containing the list of available courses
 */
public class ChatListsFragment extends Fragment {

    private final List<Group> userGroups = new ArrayList<>();
    protected ReferenceWrapper firebase;
    private ChatRecyclerAdapter ad;
    private String userID;
    private MetaGroup metabase;

    /**
     *  Required empty public constructor
     */
    public ChatListsFragment() {}

    /**
     * Sets up the graphical elements of the Fragment
     * @param inflater Inflater containing the list of existing chats
     * @param container container of the list
     * @param savedInstanceState previously saved state
     * @return The inflatet view containing the list of chats
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat_lists, container, false);
        firebase = getDB();
        User user = ((StudyBuddy) getActivity().getApplication()).getAuthendifiedUser();
        userID = user.getUserID().toString();
        metabase = new MetaGroup();
        setUI(v);
        setGroupsUp();
        return v;
    }

    /**
     * Retrieves the correct gruops from firebase
     * @return a listener that provides the aforementioned feature
     */
    public ValueEventListener setGroupsUp() {
        metabase.addListenner(new RecyclerAdapterAdapter(ad));
        return metabase.getUserGroups(userID, userGroups);
    }

    /**
     * Sets the Graphical components of the UI
     * @param v the view to be modified
     */
    private void setUI(View v) {
        ad = new ChatRecyclerAdapter(userGroups, userID);
        RecyclerView recyclerView_groups = (RecyclerView) v.findViewById(R.id.chats_list);
        recyclerView_groups.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView_groups.setAdapter(ad);

    }

    /**
     * Getter of the FirebaseReference. Used when testing
     * @return the firebase reference
     */
    public ReferenceWrapper getDB() {
        return new FirebaseReference();
    }

}
