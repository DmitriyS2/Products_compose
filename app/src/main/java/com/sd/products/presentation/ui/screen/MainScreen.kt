package com.sd.products.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.sd.products.R
import com.sd.products.presentation.ui.Routes
import com.sd.products.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel,
    navController: NavHostController
) {

    val modelProducts by vm.dataModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.catalog))
            },
            colors = TopAppBarColors(
                containerColor = Color.Blue,
                scrolledContainerColor = Color.Blue,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
        )

        when {
            modelProducts?.loading == true -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        CircularProgressIndicator()
                    }
                }
            }

            modelProducts?.error == true -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.try_later))
                    Button(onClick = {
                        vm.loadAllProducts()
                    }) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(modelProducts?.products ?: emptyList()) { _, item ->
                        Card(modifier = Modifier
                            .padding(4.dp)
                            .height(300.dp)
                            .clickable {
                                vm.loadCurrentProduct(item.id)
                                navController.navigate(Routes.Current.route)
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(4.dp)
                            ) {
                                AsyncImage(
                                    model = item.thumbnail, contentDescription = item.title,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .height(100.dp),
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = item.title,
                                    fontSize = 22.sp,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = item.description,
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}