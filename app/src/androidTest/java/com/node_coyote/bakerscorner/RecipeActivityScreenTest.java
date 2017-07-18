package com.node_coyote.bakerscorner;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.node_coyote.bakerscorner.recipes.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by node_coyote on 6/26/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTest {

    @Rule
    public ActivityTestRule<RecipeActivity> testRule = new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void clickRecipe_OpensRecipeDetailActivity() {
        //onData(anything()).inAdapterView(withId(R.id.recipe_item_container)).atPosition(1).perform(click());
        onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        //onView(allOf(withId(R.id.recipe_title_view),withText("Brownies"))).perform(click());
        //onView(withId(R.id.recipe_title_view)).check(matches(withText("Nutella Pie")));
    }
}
