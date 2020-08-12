package com.example.quiztest1;

import android.content.res.Resources;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentController;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class N5KanaActivityTest {
    @Mock
    Toast myToast;
    @Mock
    TextView questionView;
    @Mock
    Button answer1;
    @Mock
    Button answer2;
    @Mock
    Button answer3;
    @Mock
    Button answer4;
    //Field button_array of type Button[] - was not mocked since Mockito doesn't mock arrays
    @Mock
    Button correctButton;
    @Mock
    ProgressBar scoreBar;
    @Mock
    ProgressBar scoreBar2;
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
    N5KanaActivity n5KanaActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        n5KanaActivity.onCreate(null);
    }

    @Test
    public void testGetnewQuestion() throws Exception {
        n5KanaActivity.getnewQuestion(null);
    }

    @Test
    public void testGet_random_button() throws Exception {
        Button result = n5KanaActivity.get_random_button();
        Assert.assertEquals(null, result);
    }

    @Test
    public void testAnswer_question() throws Exception {
        n5KanaActivity.answer_question(null);
    }

    @Test
    public void testCorrectAnswer() throws Exception {
        n5KanaActivity.correctAnswer();
    }

    @Test
    public void testIncorrectAnswer() throws Exception {
        n5KanaActivity.incorrectAnswer();
    }

    @Test
    public void testGo_to_questions() throws Exception {
        n5KanaActivity.go_to_questions(null);
    }

    @Test
    public void testEndQuizButton() throws Exception {
        n5KanaActivity.endQuizButton(null);
    }

    @Test
    public void testUpdateScoreBar() throws Exception {
        n5KanaActivity.updateScoreBar(0);
    }

    @Test
    public void testLoad_bottom_bar() throws Exception {
        n5KanaActivity.load_bottom_bar();
    }

    @Test
    public void testDark_mode() throws Exception {
        n5KanaActivity.dark_mode();
    }

    @Test
    public void testInitialise_variables() throws Exception {
        n5KanaActivity.initialise_variables();
    }
}