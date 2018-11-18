package ch.epfl.sweng.studdybuddy.util;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;

public final class CoreFactory {
    public static List<Pair> userGroup1() {
        return Arrays.asList(new Pair("456", "123"), new Pair("789", "123"), new Pair("ghi", "abc"));
    }

    public static Group blankGroupWId(String id) {
        return new Group(1, new Course(""), "", id, UUID.randomUUID().toString());
    }

    public static Group withAdmin(String aId) {
        return new Group(1, new Course(""), "", "-" ,aId);
    }

    public static Group blankGroupWithCourse(String course) {
        return new Group(1, new Course(course), "", "", "");
    }

    public static Group groupOf(int parti) {
        return new Group(parti, new Course("test"), "fr", UUID.randomUUID().toString());
    }


    public static User johnDoe(String id) {
        return new User("John Doe", id);
    }

    public static List<User> users1() {
        return Arrays.asList(johnDoe("ghi"), johnDoe("789"), johnDoe("456"), johnDoe("k"), johnDoe("kk"));
    }

    public static List<Group> groups1() {
        return Arrays.asList(blankGroupWId("123"), blankGroupWId("abc"), blankGroupWId("v"), blankGroupWId("vv"));
    }

    public static List<Group> groups2() {
        return Arrays.asList(blankGroupWithCourse("algo"), blankGroupWithCourse("compnet"), blankGroupWithCourse("clp"), blankGroupWithCourse("algebra"), blankGroupWithCourse("sweng"));
    }
    public static Meeting randomMeeting() {
        return new Meeting();
    }
    private CoreFactory(){
        throw new IllegalMonitorStateException(); //Safe but will drop code cvg a little
    }
}
