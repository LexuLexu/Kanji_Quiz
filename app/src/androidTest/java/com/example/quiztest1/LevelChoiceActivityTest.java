package com.example.quiztest1;

import android.content.res.Resources;

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

public class LevelChoiceActivityTest {
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
    LevelChoiceActivity levelChoiceActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        levelChoiceActivity.onCreate(null);
    }

    @Test
    public void testGo_to_n5English() throws Exception {
        levelChoiceActivity.go_to_n5English(null);
    }

    @Test
    public void testGo_to_n5Kana() throws Exception {
        levelChoiceActivity.go_to_n5Kana(null);
    }

    @Test
    public void testGo_to_n4English() throws Exception {
        levelChoiceActivity.go_to_n4English(null);
    }

    @Test
    public void testGo_to_n4Kana() throws Exception {
        levelChoiceActivity.go_to_n4Kana(null);
    }

    @Test
    public void testGo_to_endless() throws Exception {
        levelChoiceActivity.go_to_endless(null);
    }

    @Test
    public void testGo_to_profile() throws Exception {
        levelChoiceActivity.go_to_profile(null);
    }

    @Test
    public void testNotAvailable() throws Exception {
        levelChoiceActivity.notAvailable(null);
    }

    @Test
    public void testLoad_bottom_bar() throws Exception {
        levelChoiceActivity.load_bottom_bar();
    }

    @Test
    public void testDark_mode() throws Exception {
        levelChoiceActivity.dark_mode();
    }

    @Test
    public void testCheck_for_name() throws Exception {
        levelChoiceActivity.check_for_name();
    }
}