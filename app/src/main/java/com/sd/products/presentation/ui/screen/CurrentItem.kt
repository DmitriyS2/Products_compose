package com.sd.products.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.sd.products.R
import com.sd.products.presentation.ui.Routes
import com.sd.products.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentItem(
    vm: MainViewModel,
    navController: NavHostController
) {
    val modelCurrent by vm.currentProduct.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        TopAppBar(
            title = {
                Text(text = modelCurrent?.product?.title ?: "")
            },
            colors = TopAppBarColors(
                containerColor = Color.Blue,
                scrolledContainerColor = Color.Blue,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Routes.Main.route)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backFromCurrent"
                    )
                }
            }
        )

        when {
            modelCurrent?.loading == true -> {
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

            modelCurrent?.error == true -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.try_later))
                    Button(onClick = {
                        vm.loadCurrentProduct(vm.currentId)
                    }) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            }

            else -> {
                Column() {
                    LazyRow(modifier = Modifier.padding(24.dp)) {
                        itemsIndexed(
                            items = modelCurrent?.product?.images ?: emptyList()
                        ) { _, item ->
                            AsyncImage(
                                model = item, contentDescription = item,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(200.dp),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                        }
                    }
                    Text(
                        text = stringResource(R.string.price, modelCurrent?.product?.price ?: 0),
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(
                            R.string.discount,
                            modelCurrent?.product?.discountPercentage ?: "0.0"
                        ),
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(
                            R.string.rating,
                            modelCurrent?.product?.rating ?: "0.0"
                        ),
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(
                            R.string.category,
                            modelCurrent?.product?.category ?: ""
                        ),
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(R.string.brand, modelCurrent?.product?.brand ?: ""),
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(
                            R.string.desc,
                            modelCurrent?.product?.description ?: ""
                        ),
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(R.string.stock, modelCurrent?.product?.stock ?: 0),
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}

