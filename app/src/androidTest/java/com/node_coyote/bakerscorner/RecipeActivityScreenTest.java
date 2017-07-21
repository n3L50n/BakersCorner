package com.node_coyote.bakerscorner;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.node_coyote.bakerscorner.recipes.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by node_coyote on 6/26/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTest {

    @Rule
    public ActivityTestRule<RecipeActivity> testRule = new ActivityTestRule<>(RecipeActivity.class);
//
//    @Rule
//    public ActivityTestRule activityRule = new ActivityTestRule<>(
//            SpecialActivity.class,
//            true,    // initialTouchMode
//            false);  //Lazy launching
//
//    @Test
//    public void specialTest() {
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString(SpecialActivity.KEY_SPECIAL_VALUE, "789");
//        intent.putExtra(SpecialActivity.ARG_SPECIAL_BUNDLE, bundle);
//        activityRule.launchActivity(intent);
//
//        onView(withId(R.id.special))
//                .check(matches(withText("789")));
//    }

//    @Rule
//    public ActivityTestRule<RecipeActivity> mActivityRule =
//            new ActivityTestRule<RecipeActivity>(RecipeActivity.class) {
//                @Override
//                protected Intent getActivityIntent() {
//                    Context targetContext = InstrumentationRegistry.getInstrumentation()
//                            .getTargetContext();
//                    Intent result = new Intent(targetContext, RecipeActivity.class);
//                    result.getLongExtra("ROW_ID", 1);
//                    return result;
//                }
//            };


    @Test
    public void clickRecipe_OpensRecipeDetailActivity() {

        onView(withId(R.id.recipe_recycler_view))
                .check(matches(hasDescendant(withText("Brownies"))));

        onView(withId(R.id.recipe_recycler_view))
                .check(matches(hasDescendant(withId(R.id.recipe_item_container)))).perform(click());

    }
}
