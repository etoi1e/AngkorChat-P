package com.example.feed.compose

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.example.feed.R
import com.example.feed.compose.feed.FeedPopup
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.EditorVisibility

@Composable
internal fun EditorVisibility(
    modifier: Modifier = Modifier,
    editorVisibility: EditorVisibility,
    onEditorVisibilityChange: (EditorVisibility) -> Unit
) {
    var showPopup by remember {
        mutableStateOf(false)
    }

    val popupButtonAngle by animateFloatAsState(targetValue = if (showPopup) 0f else 180f)

    Box(
        modifier = modifier
            .width(120.dp)
            .height(36.dp)
            .shadow(3.dp, RoundedCornerShape(8.dp), ambientColor = Color(0x29000000))
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xffdadde3), RoundedCornerShape(8.dp))
    ) {
        EditorVisibilityDropdownMenuItem(
            icon = editorVisibility.icon,
            text = editorVisibility.text
        ) {
            showPopup = !showPopup
        }

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .rotate(popupButtonAngle),
            painter = painterResource(id = R.drawable.ic_arrow_up_16),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
        )

        AnimatedVisibility(
            visible = showPopup
        ) {
            Popup(
                onDismissRequest = {
                    showPopup = false
                }
            ) {
                EditorVisibilityDropdownMenu(onEditorVisibilityChange = {
                    onEditorVisibilityChange(it)
                    showPopup = false
                })
            }
        }
    }
}

@Composable
private fun EditorVisibilityDropdownMenu(
    onEditorVisibilityChange: (EditorVisibility) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 40.dp)
            .width(120.dp)
            .shadow(3.dp, RoundedCornerShape(8.dp), ambientColor = Color(0x29000000))
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xffdadde3), RoundedCornerShape(8.dp))
    ) {
        listOf(
            EditorVisibility.Public,
            EditorVisibility.Friends,
            EditorVisibility.Onlyme
        ).map {
            EditorVisibilityDropdownMenuItem(
                icon = it.icon,
                text = it.text
            ) {
                onEditorVisibilityChange(it)
            }
        }
    }
}

@Composable
private fun EditorVisibilityDropdownMenuItem(
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(id = icon),
            contentDescription = text
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
internal fun EditorVisibilityPreview() {
    var editorVisibility: EditorVisibility by remember {
        mutableStateOf(EditorVisibility.Public)
    }

    AngKorChatProtoFeedTheme {
        EditorVisibility(
            editorVisibility = editorVisibility,
            onEditorVisibilityChange = {
                editorVisibility = it
            }
        )
    }
}