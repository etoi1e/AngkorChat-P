package com.example.bankangkorprototype

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankangkorprototype.appbar.BankAngkorAppBar
import com.example.bankangkorprototype.appbar.BankAngkorBottomNavigationBar
import com.example.bankangkorprototype.banner.AdBanner
import com.example.bankangkorprototype.button.AddButtonLarge
import com.example.bankangkorprototype.card.Account
import com.example.bankangkorprototype.card.AccountCardPager
import com.example.bankangkorprototype.card.Bank
import com.example.bankangkorprototype.card.Transfer
import com.example.bankangkorprototype.card.TransferCard
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngkorBankMainScreen(
    accounts: List<Account>,
    frequentlyUsedTransfer: List<Transfer>,
    recentTransfer: List<Transfer>,
    ads: List<Int>
) {
    var selectedBottomNavBarPosition by remember {
        mutableStateOf(0)
    }
    var bannerHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    BankAngkorPrototypeTheme {
        Scaffold(
            topBar = {
                BankAngkorAppBar()
            },
            bottomBar = {
                BankAngkorBottomNavigationBar(
                    selectedPosition = selectedBottomNavBarPosition,
                    onSelectionChange = {
                        selectedBottomNavBarPosition = it
                    })
            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) { scaffoldPaddingValues ->
            Box(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                LazyColumn(
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentPadding = PaddingValues(bottom = bannerHeight)
                ) {
                    item {
                        AccountCardPager(accounts = accounts)
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    item {
                        TransferCard(
                            frequentlyUsedTransfers = frequentlyUsedTransfer,
                            recentTransfers = recentTransfer
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        AddButtonLarge {

                        }
                    }
                }

                AdBanner(
                    modifier = Modifier
                        .onGloballyPositioned {
                            with(density) {
                                bannerHeight = it.size.height.toDp() + 32.dp
                            }
                        }
                        .padding(bottom = 16.dp),
                    ads = ads
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val accounts = listOf(
        Account("Bank Account", "123-4567-890", 1486),
        Account("Bank Account2", "123-4567-891", 14860),
        Account("Bank Account3", "123-4567-892", 124),
    )

    val frequentlyUsedTransfers = listOf(
        Transfer(to = "Elly", bank = Bank.KB),
        Transfer(to = "Jone", bank = Bank.Shinhan),
        Transfer(to = "Mina", bank = Bank.Hana)
    )

    val recentTransfers = listOf(
        Transfer(to = "Jone", bank = Bank.Shinhan),
        Transfer(to = "Elly", bank = Bank.KB),
        Transfer(to = "Mina", bank = Bank.Hana)
    )

    AngkorBankMainScreen(
        accounts = accounts,
        frequentlyUsedTransfer = frequentlyUsedTransfers,
        recentTransfer = recentTransfers,
        ads = List(5) { R.drawable.img_banner_1 }
    )
}