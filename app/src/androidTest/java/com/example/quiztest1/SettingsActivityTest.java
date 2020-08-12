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

import static org.mockito.Mockito.*;

public class SettingsActivityTest {
    @Mock
    Toast myToast;
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
    SettingsActivity settingsActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        settingsActivity.onCreate(null);
    }

    @Test
    public void testMute_sounds() throws Exception {
        settingsActivity.mute_sounds(null);
    }

    @Test
    public void testToggle_dark_mode() throws Exception {
        settingsActivity.toggle_dark_mode(null);
    }

    @Test
    public void testEnable_notifications() throws Exception {
        settingsActivity.enable_notifications(null);
    }

    @Test
    public void testGo_to_questions() throws Exception {
        settingsActivity.go_to_questions(null);
    }

    @Test
    public void testGo_to_about() throws Exception {
        settingsActivity.go_to_about(null);
    }

    @Test
    public void testGo_to_FAQ() throws Exception {
        settingsActivity.go_to_FAQ(null);
    }

    @Test
    public void testGo_to_help() throws Exception {
        settingsActivity.go_to_help(null);
    }

    @Test
    public void testLoad_bottom_bar() throws Exception {
        settingsActivity.load_bottom_bar();
    }

    @Test
    public void testDark_mode() throws Exception {
        settingsActivity.dark_mode();
    }

    @Test
    public void testSet_switches() throws Exception {
        settingsActivity.set_switches();
    }

    @Test
    public void testSet_freqBar() throws Exception {
        settingsActivity.set_freqBar();
    }

    @Test
    public void testSet_logoutButton() throws Exception {
        settingsActivity.set_logoutButton();
    }

    @Test
    public void testNotAvailable() throws Exception {
        settingsActivity.notAvailable();
    }
}