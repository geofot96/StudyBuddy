package ch.epfl.sweng.studdybuddy.activities.group;

import android.os.Bundle;
import android.app.Activity;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class InviteFriendsActivity extends Activity {
    private String gId, uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        getBundleData();
    }
    private void getBundleData(){
        Bundle bundle = GlobalBundle.getInstance().getSavedBundle();
        gId = bundle.getString(Messages.groupID);
        uId = bundle.getString(Messages.userID);
    }




}
