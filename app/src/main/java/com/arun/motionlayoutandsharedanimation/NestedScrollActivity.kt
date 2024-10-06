package com.arun.motionlayoutandsharedanimation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arun.motionlayoutandsharedanimation.data.foodList
import com.arun.motionlayoutandsharedanimation.ui.theme.MotionLayoutAndSharedAnimationTheme


class NestedScrollActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutAndSharedAnimationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NestedScrollScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NestedScrollPreview() {
    NestedScrollScreen()
}

@Composable
fun NestedScrollScreen(modifier: Modifier = Modifier) {
    val isDetailsScreen = remember {
        mutableStateOf(false)
    }
    val index = remember {
        mutableStateOf(-1)
    }
    val maxHeight = 200f
    val minHeight = 60f
    val d = LocalDensity.current.density

    val toolbarHeightPx = with(LocalDensity.current) {
        maxHeight.dp.roundToPx().toFloat()
    }

    val toolbarMinHeightPx = with(LocalDensity.current) {
        minHeight.dp.roundToPx().toFloat()
    }

    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                Log.d("TAG", "NestedScrollScreen: delta $delta")

                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value =
                    newOffset.coerceIn(toolbarMinHeightPx - toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = toolbarOffsetHeightPx.value) {
        progress =
            ((toolbarHeightPx + toolbarOffsetHeightPx.value) / toolbarHeightPx - minHeight / maxHeight) / (1f - minHeight / maxHeight)
        Log.d("TAG", "NestedScrollScreen:progress $progress ")
    }

    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        FoodList(
            modifier = Modifier.padding(PaddingValues(top = maxHeight.dp)),
            isDetailsScreen = isDetailsScreen,
            index = index
        )
        if (!isDetailsScreen.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(((toolbarHeightPx + toolbarOffsetHeightPx.value) / d).dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xffffc6f3),
                                Color(0xffd2d2d2),
                                Color(0xffbed1df),
                                Color(0xffd2d2d2),
                                Color(0xffffb7c3)
                            )
                        )
                    )

            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(progress + 0.001f)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(vertical = 10.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape)
                                .background(Color.Red)
                                .border(2.dp, getBorderColor(progress), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.linkedin_profile_pic),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        if (progress != 0f)
                            Text(
                                "Arun Kumar Aditya",
                                color = Color(0xff9400d3),
                                modifier = Modifier
                                    .alpha(progress)
                                    .padding((8 * (progress * progress * progress)).dp),
                                fontSize = (24 * (progress)).sp,

                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                    }
                    Text(
                        "Arun Aditya",
                        color = Color.White,
                        modifier = Modifier
                            .alpha(1f - progress)
                            .weight(1.001f - progress)
                            .padding(start = 20.dp),
                        fontSize = (24 * (1f - progress)).sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FoodList(
    modifier: Modifier = Modifier,
    isDetailsScreen: MutableState<Boolean>,
    index: MutableState<Int>
) {
    val maxHeight = 200f
    SharedTransitionLayout {
        AnimatedContent(targetState = isDetailsScreen.value, label = "") { targetState ->
            if (targetState) {
                Column(
                    modifier = Modifier
                        .clickable {
                            isDetailsScreen.value = isDetailsScreen.value.not()
                        }
                        .padding(horizontal = 12.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painter = painterResource(id = foodList.get(index.value).image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .sharedElement(
                                state = rememberSharedContentState(key = "image-${index.value}"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                            .height(300.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = foodList.get(index.value).name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "name-${index.value}"),
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(foodList.get(index.value).desc),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "desc-${index.value}"),
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(top = maxHeight.dp)
                ) {
                    itemsIndexed(foodList) { i, item ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                if (it == SwipeToDismissBoxValue.EndToStart) {
                                    foodList.remove(item)
                                    true
                                } else {
                                    false
                                }
                            }
                        )
                        SwipeToDismissBox(state = dismissState,
                            backgroundContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                                        contentDescription = null
                                    )
                                }
                            }) {
                            Card(
                                modifier = Modifier
                                    .clickable {
                                        isDetailsScreen.value = isDetailsScreen.value.not()
                                        index.value = i
                                    },
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xfff2f2f2),
                                )
                            ) {
                                Row {
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp)
                                            .weight(2f)
                                    ) {
                                        Text(
                                            text = item.name,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.sharedElement(
                                                state = rememberSharedContentState(key = "name-${i}"),
                                                animatedVisibilityScope = this@AnimatedContent
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = stringResource(id = item.desc),
                                            style = MaterialTheme.typography.bodyMedium,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 4,
                                            modifier = Modifier.sharedElement(
                                                state = rememberSharedContentState(key = "desc-${i}"),
                                                animatedVisibilityScope = this@AnimatedContent
                                            )
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = item.image),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(1f)
                                            .sharedElement(
                                                state = rememberSharedContentState(key = "image-${i}"),
                                                animatedVisibilityScope = this@AnimatedContent
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getBorderColor(progress: Float): Color = when (progress) {
    in 0.0f..0.2f -> Color.Red
    in 0.2f..0.4f -> Color.Green
    in 0.4f..0.6f -> Color.Blue
    in 0.6f..0.8f -> Color.Yellow
    in 0.8f..1.0f -> Color.Cyan
    else -> Color.Cyan
}
