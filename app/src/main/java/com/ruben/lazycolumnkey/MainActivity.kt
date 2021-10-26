package com.ruben.lazycolumnkey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.ruben.lazycolumnkey.ui.theme.LazyColumnKeyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnKeyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    TestLazy(onClick = {viewModel.addItem()}, items = viewModel.snapshotStateList)
                }
            }
        }
    }
}

@Composable
fun TestLazy(onClick: () -> Unit, items: List<RandomIdiot>) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
        constraintSet = ConstraintSet {
            val list = createRefFor("list")
            val button = createRefFor("button")

            constrain(button) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            constrain(list) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(button.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
    ) {
        LazyColumn(modifier = Modifier.layoutId("list"), reverseLayout = true) {
            items(key = { item -> item.id }, items = items) { item ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(modifier = Modifier.padding(8.dp), text = item.id, textAlign = TextAlign.Center)
                    Text(modifier = Modifier.padding(8.dp), text = item.name, textAlign = TextAlign.Center)
                }
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("button"),
            onClick = { onClick.invoke() }
        ) {
            Text(text = "ADD")
        }
    }
}