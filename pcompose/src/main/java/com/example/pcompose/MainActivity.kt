package com.example.pcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pcompose.ui.theme.PractiseTheme
import kotlinx.coroutines.launch
import java.lang.Math.max
import java.lang.Math.min
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    val list = arrayListOf<Name>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NameList(messages = getNameList())
//            HelloView(name = Name("Maomao", "Chong"))
//            PractiseTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
        }
    }

    fun getNameList(): List<Name> {
        if (list.size != 0) return list
        for (i in 0..10) {
            list.add(Name("MaoMao$i", "Chong$i"))
        }
        return list;
    }
}

data class Name(val firstName: String, val lastName: String)


/**
 * 单条消息
 */
@Composable
fun HelloView(name: Name) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .padding(all = 4.dp)
                .background(MaterialTheme.colors.background)
                .border(2.dp, MaterialTheme.colors.secondaryVariant)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "avater"
        )
        Spacer(modifier = Modifier.width(8.dp))
        var showWelcome by remember {
            mutableStateOf(false)
        }
        MyBasicColumn(
            modifier = Modifier.clickable { showWelcome = !showWelcome },
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "firstName: ${name.firstName}",
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2

            )
            Spacer(modifier = Modifier.width(8.dp))
            Row {
//                Text(
//                    text = "laseName: ${name.lastName}",
//                    style = MaterialTheme.typography.body2
//                )
//                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (showWelcome) "Welcome to China!" else "独孤求败！"
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {},
//            contentPadding = PaddingValues(
//                start = 20.dp,
//                top = 12.dp,
//                end = 20.dp,
//                bottom = 12.dp
//            )
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Like")
        }
        ExtendedFloatingActionButton(
            text = { Text(text = "Like") },
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorite") })
    }
}

/**
 * 多条消息
 */
@Composable
fun NameList(messages: List<Name>) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Show snackbar") },
                    onClick = {
                        scope.launch {
//                            scaffoldState.snackbarHostState.showSnackbar(
//                                message = "Snackbar",
//                                actionLabel = "Action",
//                                duration = SnackbarDuration.Indefinite
//                            )
                            scaffoldState.drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    })
            }
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))) {

            }
        },
        drawerContent = {
            Text(text = "Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
        }, drawerGesturesEnabled = false
    ) {
        LazyColumn {
            items(messages) { message ->
                MyBasicColumn {
                    HelloView(name = message)
                    TestBox()
                }
            }
        }
    }
}


/**
 * 预览
 */
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreViewHelloView() {
    PractiseTheme {
        MyBasicColumn {
            HelloView(name = Name("Chong", "Maomao"))
            TestBox()
        }
    }
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        placeable.placeRelative(0, placeableY)
    }
}

/**
 * 自定义Column
 */
@Composable
fun MyBasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier) { measurables, constraints ->
        var maxWidth = 0;
        var allHeight = 0;
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints).apply {
                if (width > maxWidth) maxWidth = width
                allHeight += height
            }
        }
        layout(maxWidth, allHeight) {
            var yPosition = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun TestBox() {
    BoxWithConstraints {
        if (maxWidth < 400.dp) {
            MyBasicColumn {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "testBoxIcon",
                    modifier = Modifier.size(40.dp)
                )
                Text(text = "TestBox")
            }
        } else {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "testBoxIcon",
                    modifier = Modifier.size(40.dp)
                )
                Text(text = "TestBox")
            }
        }
    }
}

val MaxChartValue = HorizontalAlignmentLine(merger = { old, new -> min(old, new) })
val MinChartValue = HorizontalAlignmentLine(merger = { old, new -> max(old, new) })

@Composable
fun BarChart(dataPoints: List<Int>, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        val maxValue: Float = remember(dataPoints) { dataPoints.maxOrNull()!! * 1.2f }
        BoxWithConstraints(modifier) {
            val density = LocalDensity.current
            with(density) {
                val yPositionRatio = remember(density, maxHeight, maxValue) {
                    maxHeight.toPx() / maxValue
                }
                val xPositionRatio = remember(density, maxWidth, dataPoints) {
                    maxWidth.toPx() / (dataPoints.size + 1)
                }
                val xOffset = remember(density) {
                    xPositionRatio / dataPoints.size
                }
                val maxYBaseline = remember(dataPoints) {
                    dataPoints.maxOrNull()?.let { (maxValue - it) * yPositionRatio } ?: 0f
                }
                val minYBaseline = remember(dataPoints) {
                    dataPoints.minOrNull()?.let { (maxValue - it) * yPositionRatio } ?: 0f
                }
                Layout(content = {}, modifier = Modifier.drawBehind {
                    dataPoints.forEachIndexed { index, dataPoint ->
                        val rectSize = Size(60f, dataPoint * yPositionRatio)
                        val topLeftOffset = Offset(
                            x = xPositionRatio * (index + 1) - xOffset,
                            y = (maxValue - dataPoint) * yPositionRatio
                        )
                        drawRect(Color(0xFF3DDC84), topLeftOffset, rectSize)
                    }
                    drawLine(
                        Color(0xFF073042), start = Offset(0f, 0f),
                        end = Offset(0f, maxHeight.toPx()), strokeWidth = 6f
                    )
                    drawLine(
                        Color(0xFF073042), start = Offset(0f, maxHeight.toPx()),
                        end = Offset(maxWidth.toPx(), maxHeight.toPx()), strokeWidth = 6f
                    )
                }) { _, constraints ->
                    with(constraints) {
                        layout(
                            width = if (hasBoundedWidth) maxWidth else minWidth,
                            height = if (hasBoundedHeight) maxHeight else minHeight,
                            alignmentLines = mapOf(
                                MinChartValue to minYBaseline.roundToInt(),
                                MaxChartValue to maxYBaseline.roundToInt()
                            )
                        ) {}
                    }
                }
            }
        }
    }
}

@Composable
fun BarChartMinMax(
    dataPoints: List<Int>, maxText: @Composable () -> Unit,
    minText: @Composable () -> Unit, modifier: Modifier = Modifier
) {
    Layout(content = {
        maxText
        minText
        BarChart(dataPoints = dataPoints, Modifier.size(200.dp))
    }, modifier) { measurables, constraints ->
        check(measurables.size == 3)
        val placeables = measurables.map {
            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }
        val maxTextPlaceable = placeables[0]
        val minTextPlaceable = placeables[1]
        val barChartPlaceable = placeables[2]
        val minValueBaseline = barChartPlaceable[MinChartValue]
        val maxValueBaseline = barChartPlaceable[MaxChartValue]
        layout(constraints.maxWidth, constraints.maxHeight) {
            maxTextPlaceable.placeRelative(
                x = 0,
                y = maxValueBaseline - minTextPlaceable.height / 2
            )
            minTextPlaceable.placeRelative(0, minValueBaseline - minTextPlaceable.height / 2)
            barChartPlaceable.placeRelative(
                max(
                    maxTextPlaceable.width,
                    minTextPlaceable.width
                ) + 20, 0
            )
        }
    }
}

@Preview
@Composable
fun ChartDataPreview() {
    MaterialTheme {
        BarChartMinMax(
            dataPoints = listOf(4, 24, 5),
            maxText = { Text(text = "Max") },
            minText = { Text(text = "Min") },
            modifier = Modifier.padding(24.dp)
        )
    }
}