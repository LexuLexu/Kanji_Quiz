package com.example.quiztest1;

import android.content.res.Resources;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentController;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegisterActivityTest {
    @Mock
    EditText emailInput;
    @Mock
    EditText passwordInput;
    @Mock
    EditText confPasswordInput;
    @Mock
    FirebaseAuth mAuth;
    @Mock
    Button registerButton;
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
    RegisterActivity registerActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        registerActivity.onCreate(null);
    }

    @Test
    public void testCheckUserDetails() throws Exception {
        boolean result = registerActivity.checkUserDetails();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testToggleHidePassword() throws Exception {
        registerActivity.toggleHidePassword(null);
    }

    @Test
    public void testToggleHideConfPassword() throws Exception {
        registerActivity.toggleHideConfPassword(null);
    }

    @Test
    public void testOpen_profile_if_user() throws Exception {
        registerActivity.open_profile_if_user();
    }

    @Test
    public void testSet_register() throws Exception {
        registerActivity.set_register();
    }

    @Test
    public void testGo_to_login() throws Exception {
        registerActivity.go_to_login(null);
    }
}