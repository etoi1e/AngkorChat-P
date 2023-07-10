package com.example.bankangkorprototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bankangkorprototype.card.Account
import com.example.bankangkorprototype.card.Bank
import com.example.bankangkorprototype.card.Transfer
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme
import kotlinx.coroutines.delay

class AngkorBankActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isSplash by rememberSaveable {
                mutableStateOf(true)
            }
            LaunchedEffect(true) {
                delay(1000)
                isSplash = false
            }

            AnimatedVisibility(visible = isSplash, enter = fadeIn(), exit = fadeOut()) {
                AngkorBankSplashScreen()
            }

            AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()) {
                val accounts = listOf(
                    Account("Angkor Account", "123-4567-890", 1486),
                    Account("Savings", "123-4567-891", 2120),
                    Account("Nest Egg", "123-4567-892", 983),
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
                    ads = listOf(R.drawable.img_banner_1, R.drawable.img_banner_main_emoticon, R.drawable.img_banner_h_180)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BankAngkorPrototypeTheme {
        Greeting("Android")
    }
}