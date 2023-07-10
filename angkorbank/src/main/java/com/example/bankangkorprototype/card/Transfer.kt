package com.example.bankangkorprototype.card

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankangkorprototype.R
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme
import kotlinx.coroutines.launch

sealed class Bank(@DrawableRes val imageResourceId: Int, val name: String) {
    object KB : Bank(R.drawable.img_logo_bank_1, "KB Bank")
    object Shinhan : Bank(R.drawable.img_logo_bank_2, "Shinhan Bank")
    object Hana : Bank(R.drawable.img_logo_bank_3, "Hana Bank")
}

data class Transfer(
    val to: String,
    val bank: Bank
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransferCard(
    frequentlyUsedTransfers: List<Transfer>,
    recentTransfers: List<Transfer>
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    BankAngkorCard(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransferTab(text = "Frequently used", isSelected = pagerState.currentPage == 0) {
                    coroutineScope.launch { pagerState.animateScrollToPage(0) }
                }
                TransferTab(text = "Recent transfer", isSelected = pagerState.currentPage == 1) {
                    coroutineScope.launch { pagerState.animateScrollToPage(1) }
                }
            }

            HorizontalPager(
                pageCount = 2,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) { position ->
                Row(
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    when (position) {
                        0 -> frequentlyUsedTransfers
                        1 -> recentTransfers
                        else -> emptyList()
                    }.map {
                        TransferItem(to = it.to, bank = it.bank)
                    }
                }
            }
        }
    }
}

@Composable
internal fun RowScope.TransferItem(
    to: String,
    bank: Bank
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(4.dp))
            .clickable { }
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = bank.imageResourceId),
            contentDescription = bank.name
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = to,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = bank.name,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
internal fun RowScope.TransferTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val buttonBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            Color(0xffe9e9ef)
        }
    )
    val buttonTextColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onBackground
        }
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .height(32.dp)
            .clip(RoundedCornerShape(100))
            .background(buttonBackgroundColor)
            .clickable(enabled = !isSelected, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = buttonTextColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun TransferCardPreview() {
    val frequentlyUsedTransfers = listOf(
        Transfer(to = "Elly", bank = Bank.KB),
        Transfer(to = "Jone", bank = Bank.Shinhan),
        Transfer(to = "Mina", bank = Bank.Hana)
    )

    BankAngkorPrototypeTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            TransferCard(
                frequentlyUsedTransfers = frequentlyUsedTransfers,
                recentTransfers = frequentlyUsedTransfers
            )
        }
    }
}