package com.example.quiztest1;

import android.content.res.Resources;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class LeaderboardActivityTest {
    @Mock
    List<HashMap.Entry<String, Integer>> list;
    @Mock
    HashMap<String, Integer> userList;
    @Mock
    HashMap<String, Integer> endlessUserList;
    @Mock
    ArrayList<String> sortedUsers;
    @Mock
    ArrayList<Integer> sortedScores;
    @Mock
    ArrayList<String> sortedEndlessUsers;
    @Mock
    ArrayList<Integer> sortedEndlessScores;
    @Mock
    ArrayList<TextView> sbPosList;
    @Mock
    ArrayList<TextView> sbNameList;
    @Mock
    ArrayList<TextView> sbScoreList;
    @Mock
    ArrayList<TextView> endPosList;
    @Mock
    ArrayList<TextView> endNameList;
    @Mock
    ArrayList<TextView> endScoreList;
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
    LeaderboardActivity leaderboardActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        leaderboardActivity.onCreate(null);
    }

    @Test
    public void testGo_to_questions() throws Exception {
        leaderboardActivity.go_to_questions(null);
    }

    @Test
    public void testCreate_leaderboards() throws Exception {
        leaderboardActivity.create_leaderboards();
    }

    @Test
    public void testGetUsers() throws Exception {
        leaderboardActivity.getUsers();
    }

    @Test
    public void testSortUsers() throws Exception {
        leaderboardActivity.sortUsers(Arrays.<HashMap.Entry<String, Integer>>asList(null));
    }

    @Test
    public void testSortEndlessUsers() throws Exception {
        leaderboardActivity.sortEndlessUsers(Arrays.<HashMap.Entry<String, Integer>>asList(null));
    }

    @Test
    public void testClearLeaderBoard() throws Exception {
        leaderboardActivity.clearLeaderBoard();
    }

    @Test
    public void testClearEndlessBoard() throws Exception {
        leaderboardActivity.clearEndlessBoard();
    }

    @Test
    public void testOutputToLeaderBoard() throws Exception {
        leaderboardActivity.outputToLeaderBoard();
    }

    @Test
    public void testOutputToEndlessBoard() throws Exception {
        leaderboardActivity.outputToEndlessBoard();
    }

    @Test
    public void testGatherTextViews() throws Exception {
        leaderboardActivity.gatherTextViews();
    }

    @Test
    public void testGatherEndlessTextViews() throws Exception {
        leaderboardActivity.gatherEndlessTextViews();
    }

    @Test
    public void testLoad_leaderboards() throws Exception {
        leaderboardActivity.load_leaderboards(null);
    }

    @Test
    public void testLoad_leaderboards2() throws Exception {
        leaderboardActivity.load_leaderboards();
    }

    @Test
    public void testLoad_bottom_bar() throws Exception {
        leaderboardActivity.load_bottom_bar();
    }

    @Test
    public void testDark_mode() throws Exception {
        leaderboardActivity.dark_mode();
    }

    @Test
    public void testCreate_chip_listeners() throws Exception {
        leaderboardActivity.create_chip_listeners();
    }

    @Test
    public void testInitialise_variables() throws Exception {
        leaderboardActivity.initialise_variables();
    }

    @Test
    public void testRemove_cover() throws Exception {
        leaderboardActivity.remove_cover();
    }
}