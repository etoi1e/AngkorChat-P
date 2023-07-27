package com.digitalangkor.mobility.sheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalangkor.mobility.R
import com.digitalangkor.mobility.button.DestinationButton
import com.digitalangkor.mobility.button.HomeButton
import com.digitalangkor.mobility.button.OfficeButton
import com.digitalangkor.mobility.button.PickUpPointButton
import com.digitalangkor.mobility.dash.PickupDestinationDash
import com.digitalangkor.mobility.recent.RecentItem
import com.digitalangkor.mobility.ui.theme.AngKorMobilityTheme

@Composable
fun AngkorMobilityBottomSheet() {
    Box(
        modifier = Modifier.navigationBarsPadding().shadow(elevation = 8.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 27.dp, bottom = 19.dp)
                    .padding(horizontal = 36.dp),
                text = "Where are you going today?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            PickUpPointButton()
            PickupDestinationDash()
            DestinationButton()

            LazyRow(
                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
                contentPadding = PaddingValues(horizontal = 36.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { HomeButton() }
                item { OfficeButton() }
                item {
                    Image(
                        painter = painterResource(id = R.drawable.img_arrow_right),
                        contentDescription = ""
                    )
                }
            }

            RecentItem(
                title = """ព្រះ​បរម​រាជ​វាំង​ចតុម្មុខ​មង្គល""",
                description = """មហាវិថី សម្ដេចសុធារស (៣), ភ្នំពេញ"""
            )

            RecentItem(
                title = """ពហុកីឡដ្ឋាន​ជាតិ​អូឡាំពិក""",
                description = """Charles de Gaulle Boulevard រាជធានី​ភ្នំពេញ, 12253"""
            )
        }
    }
}

@Preview
@Composable
fun AngkorMobilityBottomSheetPreview() {
    AngKorMobilityTheme {
        AngkorMobilityBottomSheet()
    }
}