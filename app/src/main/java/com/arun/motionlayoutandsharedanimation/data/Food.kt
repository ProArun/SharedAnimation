package com.arun.motionlayoutandsharedanimation.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arun.motionlayoutandsharedanimation.R


/**
 * Created by Arun Aditya on 06-10-2024.
 * LinkedIn: https://www.linkedin.com/in/arun-aditya-82a94914a/
 */
data class Food(
    val name: String,
    @DrawableRes val image: Int,
    @StringRes val desc: Int
)

val foodList = mutableListOf(
    Food(
        name = "Italian Pizza",
        image = R.drawable.pizza_1,
        desc = R.string.italian_pizza
    ),
    Food(
        name = "Mexican Pizza",
        image = R.drawable.pizza_1,
        desc = R.string.mexican_pizza
    ),
    Food(
        name = "Cheese Burger",
        image = R.drawable.burger,
        desc = R.string.cheese_burger
    ),
    Food(
        name = "Italian Pizza",
        image = R.drawable.pizza_3,
        desc = R.string.italian_pizza
    ),
    Food(
        name = "Mexican Pizza",
        image = R.drawable.pizza_4,
        desc = R.string.mexican_pizza
    ),
    Food(
        name = "Cheese Burger",
        image = R.drawable.burger,
        desc = R.string.cheese_burger
    ),
    Food(
        name = "Italian Pizza",
        image = R.drawable.pizza_5,
        desc = R.string.italian_pizza
    ),
    Food(
        name = "Mexican Pizza",
        image = R.drawable.burger_1,
        desc = R.string.mexican_pizza
    ),
    Food(
        name = "Cheese Burger",
        image = R.drawable.burger,
        desc = R.string.cheese_burger
    ),
    Food(
        name = "Italian Pizza",
        image = R.drawable.burger_2,
        desc = R.string.italian_pizza
    ),
    Food(
        name = "Mexican Pizza",
        image = R.drawable.pizza_1,
        desc = R.string.mexican_pizza
    ),
    Food(
        name = "Cheese Burger",
        image = R.drawable.burger,
        desc = R.string.cheese_burger
    ),
)

