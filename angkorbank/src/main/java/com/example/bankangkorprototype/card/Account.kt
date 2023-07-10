package com.example.bankangkorprototype.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankangkorprototype.R
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme
import kotlinx.coroutines.launch
import java.text.DecimalFormat

data class Account(
    val accountName: String,
    val accountNumber: String,
    val accountBalance: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountCardPager(
    accounts: List<Account>
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            pageCount = accounts.size,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
            pageSpacing = 8.dp,
            state = pagerState
        ) {
            AccountCard(account = accounts[it])
        }

        Indicator(
            currentPosition = pagerState.currentPage,
            maxPositions = accounts.size,
            onDotClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(it) }
            }
        )
    }
}

@Composable
internal fun AccountCard(
    account: Account
) {
    BankAngkorCard {
        Icon(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopEnd)
                .clickable {  },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_fill_bk_24),
            contentDescription = "More",
            tint = MaterialTheme.colorScheme.onBackground
        )

        Column {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = account.accountName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 4.dp),
                text = account.accountNumber,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 32.dp),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 20.sp, letterSpacing = (-0.56).sp)) {
                        append("$")
                    }
                    withStyle(SpanStyle(fontSize = 28.sp, letterSpacing = (-0.4).sp)) {
                        append(DecimalFormat("#,###").format(account.accountBalance))
                    }
                }
            )

            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(37.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Transfer",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(37.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffe9e9ef))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
internal fun Indicator(
    currentPosition: Int,
    maxPositions: Int,
    onDotClick: (Int) -> Unit
) {
    Row {
        repeat(maxPositions) { position ->
            IndicatorDot(isSelected = currentPosition == position) {
                onDotClick(position)
            }
        }
    }
}

@Composable
internal fun IndicatorDot(
    isSelected: Boolean,
    onDotClick: () -> Unit
) {
    val dotColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xffd9d9e0)
    )

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .clickable(enabled = !isSelected, onClick = onDotClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
    }
}

@Preview
@Composable
fun AccountsPreview() {
    BankAngkorPrototypeTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            val accounts = listOf(
                Account("Bank Account", "123-4567-890", 1486),
                Account("Bank Account2", "123-4567-891", 14860),
                Account("Bank Account3", "123-4567-892", 124),
            )

            AccountCardPager(accounts)
        }
    }
}