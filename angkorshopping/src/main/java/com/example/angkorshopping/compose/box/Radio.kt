package com.example.angkorshopping.compose.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.compose.state.ShopInfoState
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme


@Composable
fun Radio(
    shopName: List<ShopInfoState>
) {
    val selectedValue = remember { mutableStateOf("") }

    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = { selectedValue.value = it }

    Column(
        Modifier
            .background(AngkorShoppingTheme.colors.Background)
            .padding(8.dp)
    ) {
        shopName.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isSelectedItem(item.shopName),
                        onClick = { onChangeState(item.shopName) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    modifier = Modifier.padding(start = 16.dp),
                    selected = isSelectedItem(item.shopName),
                    onClick = null
                )
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxWidth(),
                    text = item.shopName,
                    style = AngkorShoppingTheme.typography.sansM15
                )
            }
            Text(
                modifier = Modifier.padding(start = 56.dp),
                text = item.shopAdd,
                style = AngkorShoppingTheme.typography.sansR12
            )
        }
    }
}

@Preview
@Composable
internal fun RadioPreview() {
    AngkorShoppingTheme {

    }
}