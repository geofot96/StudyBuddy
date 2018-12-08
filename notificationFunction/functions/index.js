'use strict'

const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/notifications/{user_id}/{notification_id}').onWrite(
  (data, context)=>{
    const user_id = context.params.user_id;
    const notification_id = context.params.notification_id;

    console.log('We have a notification to send to : ', user_id);

    if(!data.after.val()){
      console.log('A Notification has been deleted from the database: ', notification_id);
      return 0;
    }

    const origin = admin.database().ref(`/notifications/${user_id}/${notification_id}`).once('value');
    return origin.then(fromOrigin => {
        const from_user_id = fromOrigin.val().FROM;
        const from_group_id = fromOrigin.val().GROUP;

        console.log('You have new notification from: ', from_user_id);

        const groupQuery = admin.database().ref(`/groups/${from_group_id}/course/courseName`).once('value');
        return groupQuery.then(groupResult=>{
          const course = groupResult.val();

          const userQuery = admin.database().ref(`/users/${from_user_id}/name`).once('value');
          return userQuery.then(userResult => {
            const userName = userResult.val();

            const deviceToken = admin.database().ref(`/tokens/${user_id}/token`).once('value');
            return deviceToken.then(result => {
              const token_id = result.val();

              const payload = {
                notification: {
                  title : `${course}`,
                  body: `${userName} has sent you a message`,
                  icon: "default",
                  click_action: "ch.epfl.sweng.studdybuddy_TARGET_NOTIFICATION"
                },
                data: {
                  group_id: from_group_id
                }
              };

              return admin.messaging().sendToDevice(token_id, payload).then(response => {
                return console.log('This was the notification feature');
              });
            });
          });
        });
    });
});
