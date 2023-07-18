package com.example.angkorshopping.screens


import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.DetailActivity
import com.example.angkorshopping.compose.appbar.TopBar
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    content: List<MerchandiseInfo>,
) {
    val context = LocalContext.current

    val intent = Intent(context, DetailActivity::class.java)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AngkorShoppingTheme.colors.Background),

        ) {
        TopBar()

        LazyVerticalGrid(
        columns = GridCells.Fixed(2)
        //columns = StaggeredGridCells.Fixed(2)
        ) {

                items(content.size) { index ->
                    val item = content[index]
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            modifier = Modifier
                                .clickable {
                                    Log.d("TAG-img", "${content[index]}")
                                    intent.putExtra("shopName", content[index].shopName)
                                    intent.putExtra("img", content[index].merchandiseImg)
                                    intent.putExtra("name", content[index].merchandiseName)
                                    intent.putExtra("price", content[index].merchandisePrice)
                                    intent.putExtra("option", content[index].merchandiseOption)
                                    intent.putExtra("quantity", content[index].quantity)
                                    intent.putExtra("pickup", content[index].pickup)
                                    intent.putExtra("tags", content[index].tags)

                                    context.startActivity(intent)
                                }
                        ) {
                            Image(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    ,
                                painter = painterResource(id = item.merchandiseImg),
                                contentDescription = item.merchandiseName

                            )

                        }

                    }


                }



        }

    }
}






