package com.example.angkorshopping.compose.appbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

@Preview
@Composable
fun OpenOption(){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .width(90.dp)
                    .height(25.dp),
                shape = RoundedCornerShape(topStart = 19.dp, topEnd = 19.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(50.dp)
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 19.dp,
                                topEnd = 19.dp
                            )
                        )
                        .background(AngkorShoppingTheme.colors.Background)
                        .clickable { },
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(16.dp),
                        painter = painterResource(id = R.drawable.ic_chevron_down_bk_16),
                        contentDescription = ""
                    )
                }


            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .height(260.dp),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp
                            )
                        )
                        .background(AngkorShoppingTheme.colors.Background)

                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            text = "Option",
                            style = AngkorShoppingTheme.typography.sansR15
                        )
                        Row(modifier = Modifier.padding(top = 13.dp)) {
                            Button(
                                modifier = Modifier
                                    .height(36.dp),
                                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.MainYellow),
                                onClick = { }
                            ) {
                                Text(
                                    text = "Option",
                                    style = AngkorShoppingTheme.typography.sansR14,
                                    color = AngkorShoppingTheme.colors.Background
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .height(36.dp),
                                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.Background),
                                onClick = { },
                                border = BorderStroke(
                                    1.dp,
                                    AngkorShoppingTheme.colors.DarkGray
                                )
                            ) {
                                Text(
                                    text = "Option",
                                    style = AngkorShoppingTheme.typography.sansR14,
                                    color = AngkorShoppingTheme.colors.TextGray
                                )
                            }

                        }

                        Text(
                            modifier = Modifier
                                .padding(top = 14.dp),
                            text = "Option2",
                            style = AngkorShoppingTheme.typography.sansR15
                        )
                        Row(modifier = Modifier.padding(top = 13.dp)) {
                            Button(
                                modifier = Modifier
                                    .height(36.dp),
                                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.Background),
                                onClick = { },
                                border = BorderStroke(
                                    1.dp,
                                    AngkorShoppingTheme.colors.DarkGray
                                )
                            ) {
                                Text(
                                    text = "Option",
                                    style = AngkorShoppingTheme.typography.sansR14,
                                    color = AngkorShoppingTheme.colors.TextGray
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .height(36.dp),
                                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.Background),
                                onClick = { },
                                border = BorderStroke(
                                    1.dp,
                                    AngkorShoppingTheme.colors.DarkGray
                                )
                            ) {
                                Text(
                                    text = "Option",
                                    style = AngkorShoppingTheme.typography.sansR14,
                                    color = AngkorShoppingTheme.colors.TextGray
                                )
                            }

                        }
                        Row(
                            modifier = Modifier.padding(top = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.Background),
                                onClick = { },
                                border = BorderStroke(
                                    1.dp,
                                    AngkorShoppingTheme.colors.DarkGray
                                )
                            ) {
                                Text(
                                    text = "Add to Cart",
                                    style = AngkorShoppingTheme.typography.sansM14,
                                    color = AngkorShoppingTheme.colors.TextGray
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                                    .height(44.dp),
                                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.MainYellow),
                                onClick = { }
                            ) {
                                Text(
                                    text = "Buy",
                                    style = AngkorShoppingTheme.typography.sansM14,
                                    color = AngkorShoppingTheme.colors.Background
                                )
                            }
                        }
                    }


                }
            }
        }

    }
}