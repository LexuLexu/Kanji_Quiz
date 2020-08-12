package com.example.quiztest1;

import android.content.res.Resources;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentController;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class FriendsActivityTest {
    @Mock
    Toast myToast;
    @Mock
    ArrayList<String> friendsList;
    @Mock
    AppCompatDelegate mDelegate;
    @Mock
    Resources mResources;
    @Mock
    FragmentController mFragments;
    @Mock
    LifecycleRegistry mFragmentLifecycleRegistry;
    @Mock
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    @Mock
    LifecycleRegistry mLifecycleRegistry;
    //Field mSavedStateRegistryController of type SavedStateRegistryController - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    ViewModelStore mViewModelStore;
    @Mock
    ViewModelProvider.Factory mDefaultFactory;
    //Field mOnBackPressedDispatcher of type OnBackPressedDispatcher - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    SimpleArrayMap<Class<? extends ComponentActivity.ExtraData>, ComponentActivity.ExtraData> mExtraDataMap;
    @InjectMocks
    FriendsActivity friendsActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        friendsActivity.onCreate(null);
    }

    @Test
    public void testGo_to_questions() throws Exception {
        friendsActivity.go_to_questions(null);
    }

    @Test
    public void testLoad_bottom_bar() throws Exception {
        friendsActivity.load_bottom_bar();
    }

    @Test
    public void testDark_mode() throws Exception {
        friendsActivity.dark_mode();
    }

    @Test
    public void testGet_friends_data() throws Exception {
        friendsActivity.get_friends_data();
    }

    @Test
    public void testAddFriendButton() throws Exception {
        friendsActivity.addFriendButton(null);
    }

    @Test
    public void testAdd_to_friends_list() throws Exception {
        friendsActivity.add_to_friends_list("friendUid");
    }

    @Test
    public void testGet_friendsCount() throws Exception {
        friendsActivity.get_friendsCount();
    }

    @Test
    public void testGet_friendsList() throws Exception {
        friendsActivity.get_friendsList();
    }

    @Test
    public void testUpdate_friends_ui() throws Exception {
        friendsActivity.update_friends_ui();
    }

    @Test
    public void testInitialise_variables() throws Exception {
        friendsActivity.initialise_variables();
    }

    @Test
    public void testRemove_friend_info() throws Exception {
        friendsActivity.remove_friend_info(null);
    }

    @Test
    public void testRemove_friend() throws Exception {
        friendsActivity.remove_friend(null);
    }
}