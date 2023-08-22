package com.example.angkorwebtoonprototype.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp



@SuppressLint("DiscouragedApi")
@Composable
fun WebtoonDetail(
    name: String,
    onBack : () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(Color.White)
        ) {

            if(name.contains("_1")){

                items(6) { index ->

                    val name = "${name}_${index}"
                    val id: Int = context.resources.getIdentifier(
                        name,
                        "drawable",
                        context.packageName
                    )

                    Image(painter = painterResource(id = id), contentDescription = "$index")
                }

            }

            if (name.contains("_2")) {


                items(7) { index ->

                    val name = "${name}_${index}"
                    val id: Int = context.resources.getIdentifier(
                        name,
                        "drawable",
                        context.packageName
                    )

                    Image(painter = painterResource(id = id), contentDescription = "$index")
                }

            }

            if (name.contains("_3")) {


                items(8) { index ->

                    val name = "${name}_${index}"
                    val id: Int = context.resources.getIdentifier(
                        name,
                        "drawable",
                        context.packageName
                    )

                    Image(painter = painterResource(id = id), contentDescription = "$index")
                }

            }


        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0x7E838383))
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .clickable {
                        onBack()
                    },
                painter = painterResource(id = com.example.angkorwebtoonprototype.R.drawable.ic_arrow_left_line_28),
                contentDescription = ""
            )
        }


    }


}

