@file:Suppress("UNUSED_EXPRESSION")

package com.example.angkorshopping.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.compose.appbar.BuyBottomBar
import com.example.angkorshopping.compose.item.Reviews
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.compose.state.ReviewsState
import com.example.angkorshopping.compose.state.ShopInfoState
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme
import java.math.RoundingMode
import java.text.DecimalFormat


@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    merchandise: MerchandiseInfo
) {

    val context = LocalContext.current

    val item = merchandise.imgNumber
    val imgList = ArrayList<Int>()
    val itemDetail = context.resources.getIdentifier("${item}_detail","drawable",context.packageName)


    for(i in 1 .. 4){
        imgList.add(context.resources.getIdentifier("${item}_${i}","drawable",context.packageName))
    }


    AngkorShoppingTheme {
        val dataFormat = DecimalFormat("##")
        dataFormat.roundingMode = RoundingMode.DOWN


        if (merchandise != null) {


            Scaffold(containerColor = AngkorShoppingTheme.colors.Background,
                topBar = {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 15.dp, bottom = 8.dp)
                            .height(28.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .width(IntrinsicSize.Max)
                        ) {
                            Icon(modifier = Modifier,
                                painter = painterResource(id = R.drawable.ic_arrow_left_bk_28),
                                contentDescription = "",
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp),
                                text = merchandise.shopName,
                                style = AngkorShoppingTheme.typography.sansM18,
                                color = AngkorShoppingTheme.colors.TextBlack
                            )
                        }

                        Icon(
                            modifier = Modifier
                                .width(IntrinsicSize.Max)
                                .padding(end = 16.dp),
                            painter = painterResource(id = R.drawable.ic_more_28),
                            contentDescription = ""
                        )

                    }
                },
                bottomBar = {
                    BuyBottomBar()
                }
            ) { scaffoldPaddingValues ->


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AngkorShoppingTheme.colors.Background)
                        .padding(scaffoldPaddingValues)
                ) {

                    //상품 이미지 가로 스크롤 출력
                    item {

                        LazyRow(
                            modifier = Modifier.defaultMinSize(100.dp, 200.dp),
                            contentPadding = PaddingValues(start = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(imgList) { img ->
                                Image(
                                    painter = painterResource(id = img),
                                    contentDescription = merchandise.toString(),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(250.dp)
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                            }
                        }
                    }

                    //상품 Pickup 태그
                    if (merchandise.pickup) {
                        item() {
                            Box(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 8.dp)
                                    .width(40.dp)
                                    .height(16.dp)
                                    .background(AngkorShoppingTheme.colors.TextBlack),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Pick up",
                                    style = AngkorShoppingTheme.typography.sansM9,
                                    color = AngkorShoppingTheme.colors.Background
                                )
                            }
                        }
                    }

                    //상품 이름 출력
                    item {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 12.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "${merchandise.merchandiseName}",
                                style = AngkorShoppingTheme.typography.sansM17,
                                color = AngkorShoppingTheme.colors.TextBlack
                            )
                        }
                    }

                    //상품 #태그 출력
                    item {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Row() {
                                Text(
                                    text = merchandise.tags,
                                    style = AngkorShoppingTheme.typography.sansR14,
                                    color = AngkorShoppingTheme.colors.TextGray
                                )

                            }
                        }
                    }

                    //상품 가격, 바우처 아이콘 출력란
                    item() {
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 15.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(
                                    text = "25%",
                                    style = AngkorShoppingTheme.typography.sansM24,
                                    color = AngkorShoppingTheme.colors.red
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 12.dp),
                                    text = "$${merchandise.merchandisePrice.toInt()}",
                                    style = AngkorShoppingTheme.typography.sansB24,
                                    color = AngkorShoppingTheme.colors.TextBlack
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(start = 8.dp),
                                    text = "$${dataFormat.format(merchandise.merchandisePrice.toInt() / 0.75)}",
                                    style = AngkorShoppingTheme.typography.sansR16,
                                    color = AngkorShoppingTheme.colors.TextGray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }

                            Row(modifier = Modifier.padding(end = 16.dp)) {
                                Icon(
                                    modifier = Modifier.padding(end = 16.dp),
                                    painter = painterResource(id = R.drawable.ic_voucher_24),
                                    contentDescription = ""
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_share_24),
                                    contentDescription = ""
                                )
                            }
                        }
                    }

                    //판매 쇼핑몰 출력 구간
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 13.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .width(328.dp)
                                    .height(48.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .border(
                                        width = 1.dp,
                                        color = AngkorShoppingTheme.colors.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val shop = ShopInfoState(merchandise.merchandiseImg, merchandise.shopName, "add")

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .padding(start = 8.dp),
                                        painter = painterResource(id = shop.shopImg),
                                        contentDescription = shop.shopName
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 8.dp),
                                        text = shop.shopName,
                                        style = AngkorShoppingTheme.typography.sansR15,
                                        color = AngkorShoppingTheme.colors.TextGray
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(start = 8.dp),
                                        painter = painterResource(id = R.drawable.ic_arrow_right_16),
                                        contentDescription = ""
                                    )
                                }

                            }


                        }

                    }


                    //후기 사진 리스트 출력
                    item {
                        val reviews = listOf<ReviewsState>(
                            ReviewsState("userName", R.drawable.img_profile_default_28, R.drawable.img_11_1),
                            ReviewsState("userName", R.drawable.img_profile_default_28, R.drawable.img_17_1),
                            ReviewsState("userName", R.drawable.img_profile_default_28, R.drawable.img_6_1),
                            ReviewsState("userName", R.drawable.img_profile_default_28, R.drawable.img_8_1),
                            ReviewsState("userName", R.drawable.img_profile_default_28, R.drawable.img_15_1)

                        )

                        LazyRow(modifier = Modifier.padding(start = 16.dp, top = 24.dp))
                        {
                            items(reviews) { item ->
                                Box(modifier = Modifier.padding(start = 8.dp)) {
                                    Reviews(
                                        itemImg = item.merchandiseImg,
                                        userImg = item.userImg,
                                        userName = item.userName
                                    )
                                }
                            }
                        }
                    }

                    item() {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                            painter = painterResource(id = itemDetail),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }


            }

        }


    }

}

