package com.example.quiztest1;

import android.content.res.Resources;
import android.widget.EditText;
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

public class ProfileActivityTest {
    @Mock
    Toast myToast;
    @Mock
    EditText nameViewText;
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
    ProfileActivity profileActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        profileActivity.onCreate(null);
    }

    @Test
    public void testGet_scores() throws Exception {
        profileActivity.get_scores();
    }

    @Test
    public void testUpdate_levelCard() throws Exception {
        profileActivity.update_levelCard();
    }

    @Test
    public void testUpdate_goals() throws Exception {
        profileActivity.update_goals();
    }

    @Test
    public void testGo_to_questions() throws Exception {
        profileActivity.go_to_questions(null);
    }

    @Test
    public void testLoad_bottom_bar() throws Exception {
        profileActivity.load_bottom_bar();
    }

    @Test
    public void testDark_mode() throws Exception {
        profileActivity.dark_mode();
    }

    @Test
    public void testInitialise_variables() throws Exception {
        profileActivity.initialise_variables();
    }

    @Test
    public void testGet_userName() throws Exception {
        profileActivity.get_userName();
    }

    @Test
    public void testUpdate_ui() throws Exception {
        profileActivity.update_ui();
    }

    @Test
    public void testGet_uid() throws Exception {
        profileActivity.get_uid();
    }
}