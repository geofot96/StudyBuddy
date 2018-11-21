package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.activities.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class createMeetingActivityTest {
    /*
    java.lang.IllegalStateException: Could not initialize plugin: interface org.mockito.plugins.MockMaker
at org.mockito.internal.configuration.plugins.PluginLoader$1.invoke(PluginLoader.java:66)
at java.lang.reflect.Proxy.invoke(Proxy.java:913)
at $Proxy3.isTypeMockable(Unknown Source)
at org.mockito.internal.util.MockUtil.typeMockabilityOf(MockUtil.java:29)
at org.mockito.internal.util.MockCreationValidator.validateType(MockCreationValidator.java:22)
at org.mockito.internal.creation.MockSettingsImpl.validatedSettings(MockSettingsImpl.java:186)
at org.mockito.internal.creation.MockSettingsImpl.confirm(MockSettingsImpl.java:180)
at org.mockito.internal.MockitoCore.mock(MockitoCore.java:62)
at org.mockito.Mockito.mock(Mockito.java:1729)
at org.mockito.Mockito.mock(Mockito.java:1642)
at ch.epfl.sweng.studdybuddy.createMeetingActivityTest.setUp(createMeetingActivityTest.java:44)
at java.lang.reflect.Method.invoke(Native Method)
at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
at android.support.test.internal.runner.junit4.statement.RunBefores.evaluate(RunBefores.java:76)
at android.support.test.rule.ActivityTestRule$ActivityStatement.evaluate(ActivityTestRule.java:527)
at org.junit.rules.RunRules.evaluate(RunRules.java:20)
at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runners.Suite.runChild(Suite.java:128)
at org.junit.runners.Suite.runChild(Suite.java:27)
at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
at android.support.test.internal.runner.TestExecutor.execute(TestExecutor.java:56)
at android.support.test.runner.AndroidJUnitRunner.onStart(AndroidJUnitRunner.java:384)
at android.app.Instrumentation$InstrumentationThread.run(Instrumentation.java:2081)
Caused by: java.lang.IllegalStateException: Failed to load interface org.mockito.plugins.MockMaker implementation declared in sun.misc.CompoundEnumeration@eaae078
at org.mockito.internal.configuration.plugins.PluginLoader.loadImpl(PluginLoader.java:101)
at org.mockito.internal.configuration.plugins.PluginLoader.loadPlugin(PluginLoader.java:45)
at org.mockito.internal.configuration.plugins.PluginRegistry.<init>(PluginRegistry.java:18)
at org.mockito.internal.configuration.plugins.Plugins.<clinit>(Plugins.java:17)
at org.mockito.internal.configuration.plugins.Plugins.getMockMaker(Plugins.java:33)
at org.mockito.internal.util.MockUtil.<clinit>(MockUtil.java:24)
... 37 more
Caused by: org.mockito.exceptions.base.MockitoInitializationException:
Could not initialize inline Byte Buddy mock maker. (This mock maker is not supported on Android.)


IMPORTANT INFORMATION FOR ANDROID USERS:

The regular Byte Buddy mock makers cannot generate code on an Android VM!
To resolve this, please use the 'mockito-android' dependency for your application:
http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22mockito-android%22%20g%3A%22org.mockito%22

Java               : 0.9
JVM vendor name    : The Android Project
JVM vendor version : 2.1.0
JVM name           : Dalvik
JVM version        : 0.9
JVM info           : null
OS name            : Linux
OS version         : 3.18.66-perf-gd5ec1d77090d

at org.mockito.internal.creation.bytebuddy.InlineByteBuddyMockMaker.<init>(InlineByteBuddyMockMaker.java:172)
at java.lang.Class.newInstance(Native Method)
at org.mockito.internal.configuration.plugins.PluginLoader.loadImpl(PluginLoader.java:96)
... 42 more
Caused by: java.lang.NoClassDefFoundError: Failed resolution of: Ljava/lang/management/ManagementFactory;
at net.bytebuddy.agent.ByteBuddyAgent$ProcessProvider$ForCurrentVm$ForLegacyVm.resolve(ByteBuddyAgent.java:745)
at net.bytebuddy.agent.ByteBuddyAgent$ProcessProvider$ForCurrentVm.resolve(ByteBuddyAgent.java:730)
at net.bytebuddy.agent.ByteBuddyAgent.install(ByteBuddyAgent.java:332)
at net.bytebuddy.agent.ByteBuddyAgent.install(ByteBuddyAgent.java:300)
at net.bytebuddy.agent.ByteBuddyAgent.install(ByteBuddyAgent.java:286)
at org.mockito.internal.creation.bytebuddy.InlineByteBuddyMockMaker.<clinit>(InlineByteBuddyMockMaker.java:102)
... 44 more
Caused by: java.lang.ClassNotFoundException: Didn't find class "java.lang.management.ManagementFactory" on path: DexPathList[[zip file "/system/framework/android.test.runner.jar", zip file "/data/app/ch.epfl.sweng.studdybuddy.test-azbc6IhLmO3L6trwdfGTyg==/base.apk", zip file "/data/app/ch.epfl.sweng.studdybuddy-qpoZZzaSsN7lemvLeqvNWw==/base.apk"],nativeLibraryDirectories=[/data/app/ch.epfl.sweng.studdybuddy.test-azbc6IhLmO3L6trwdfGTyg==/lib/arm64, /data/app/ch.epfl.sweng.studdybuddy-qpoZZzaSsN7lemvLeqvNWw==/lib/arm64, /system/lib64, /system/vendor/lib64]]
at dalvik.system.BaseDexClassLoader.findClass(BaseDexClassLoader.java:93)
at java.lang.ClassLoader.loadClass(ClassLoader.java:379)
at java.lang.ClassLoader.loadClass(ClassLoader.java:312)
... 50 more

     */
    private Intent param;
    private MeetingLocation mockLocation;
    private Date alwaysBefore;
    private Date alwaysAfter;

    @Rule
    public ActivityTestRule<createMeetingActivity> mActivityRule =
            new ActivityTestRule<>(createMeetingActivity.class, false, false);

    @Before
    public void setUp(){
        param = new Intent();
        param.putExtra(Messages.groupID, Messages.TEST);
        param.putExtra(Messages.userID, Messages.TEST);
        param.putExtra(Messages.maxUser, Messages.TEST);
        mockLocation = mock(MeetingLocation.class);
        alwaysBefore = mock(Date.class);
        alwaysAfter = mock(Date.class);
        when(alwaysBefore.before(any())).thenReturn(true);
        when(alwaysAfter.after(any())).thenReturn(true);
    }

    @Test
    public void EveryThingIsDisplayed(){
        mActivityRule.launchActivity(param);
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
        onView(withId(R.id.textView4)).check(matches(isDisplayed()));
        onView(withId(R.id.datePicker)).check(matches(isClickable()));
        onView(withId(R.id.timePicker)).check(matches(isClickable()));
        onView(withId(R.id.timePicker2)).check(matches(isClickable()));
        onView(withId(R.id.locationTitle)).check(matches(isClickable()));
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }

    @Test
    public void ButtonIsEnabled(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(param);
        mActivity.setLocation(mockLocation);
        mActivity.setStartingDate(alwaysAfter);
        mActivity.setEndingDate(alwaysAfter);
        onView(withId(R.id.setMeeting)).check(matches(isEnabled()));
        onView(withId(R.id.setMeeting)).check(matches(isClickable()));
        mActivityRule.finishActivity();
    }

    @Test
    public void WrongTimeSlot(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(param);
        mActivity.setLocation(mockLocation);
        mActivity.setStartingDate(alwaysAfter);
        mActivity.setEndingDate(alwaysBefore);
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }

    @Test
    public void noLocation(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(param);
        mActivity.setStartingDate(alwaysBefore);
        mActivity.setEndingDate(alwaysAfter);
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }


}
