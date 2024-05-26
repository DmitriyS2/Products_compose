package com.sd.products.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val showReviews = remember {
        mutableStateOf(false)
    }

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
                    if (modelCurrent?.product?.images?.size == 1) {
                        AsyncImage(
                            model = modelCurrent?.product?.images!!.firstOrNull(),
                            contentDescription = modelCurrent?.product?.images!!.firstOrNull(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp)
                                .size(250.dp),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )
                    } else {
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
                    }
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(
                                R.string.price,
                                modelCurrent?.product?.price ?: "0.0"
                            ),
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
                            text = stringResource(
                                R.string.brand,
                                modelCurrent?.product?.brand ?: ""
                            ),
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
                            text = stringResource(
                                R.string.stock,
                                modelCurrent?.product?.stock ?: 0
                            ),
                            modifier = Modifier.padding(8.dp),
                            color = Color.White
                        )
                        Button(
                            onClick = {
                                showReviews.value = !showReviews.value
                            },
                            modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 12.dp),
                            colors = ButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Blue,
                                disabledContentColor = Color.White,
                                disabledContainerColor = Color.Blue
                            )
                        ) {
                            Text(
                                text = if (showReviews.value) stringResource(R.string.reviews1) else stringResource(
                                    R.string.reviews2
                                )
                            )
                        }

                        if (showReviews.value) {
                            modelCurrent?.product?.reviews?.forEach { review ->
                                Column(
                                    modifier = Modifier.padding(bottom = 12.dp)
                                ) {
                                    OutlinedCard(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.White
                                        ),
                                        border = BorderStroke(1.dp, Color.White),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 12.dp, end = 12.dp)
                                    ) {
                                        Row() {
                                            for (i in 1..review.rating) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.star_12),
                                                    contentDescription = i.toString(),
                                                    modifier = Modifier.padding(
                                                        start = 8.dp,
                                                        top = 4.dp
                                                    )
                                                )
                                            }
                                        }
                                        Text(
                                            text = review.comment,
                                            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                                            fontStyle = FontStyle.Italic,
                                            fontSize = 15.sp
                                        )
                                        Text(
                                            text = review.reviewerName,
                                            modifier = Modifier.padding(
                                                start = 8.dp,
                                                top = 4.dp,
                                                bottom = 4.dp
                                            ),
                                            fontSize = 12.sp
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
}


