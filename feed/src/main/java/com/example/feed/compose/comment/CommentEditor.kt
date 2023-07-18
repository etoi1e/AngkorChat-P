package com.example.feed.compose.comment

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.sample.sampleUsers
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.User

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentEdit(
    modifier: Modifier = Modifier,
    text: String,
    mention: User?,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
    onTextChange: (String) -> Unit,
    onMentionRemove: () -> Unit,
    onSend: () -> Unit
) {
    val mentionColor = MaterialTheme.colorScheme.onBackground
    val textFieldScrollState = rememberScrollableState { 1f }
    val textFieldRightPadding by animateDpAsState(targetValue = if(text.isBlank()) 8.dp else 0.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xfff7f7fb))
            .padding(vertical = 10.dp)
            .padding(end = 8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
                .weight(1f)
                .heightIn(min = 40.dp, max = 100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 18.dp)
                .scrollable(textFieldScrollState, Orientation.Vertical)
        ) {
            BasicTextField(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .onKeyEvent {
                        if (it.key == Key.Backspace && mention != null) {
                            onMentionRemove()
                        }

                        true
                    }
                    .focusRequester(focusRequester),
                value = text,
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                visualTransformation = {
                    TransformedText(
                        buildCommentTextFieldString(text = it.text, mention, mentionColor),
                        object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + (mention?.name?.length ?: -2) + 2
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return offset - (mention?.name?.length ?: -2) - 2
                            }
                        }
                    )
                }
            )

            if (text.isBlank()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth(),
                    text = buildCommentTextFieldString("Write a reply", mention, mentionColor),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        }

        AnimatedVisibility(visible = text.isNotBlank()) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onSend),
                    painter = painterResource(id = R.drawable.ic_send_circle_fill_prime_36),
                    contentDescription = ""
                )
            }
        }

        AnimatedVisibility(visible = text.isBlank()) {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

private fun buildCommentTextFieldString(text: String, mention: User?, mentionColor: Color) =
    buildAnnotatedString {
        if (mention != null) {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = mentionColor)) {
                pushStringAnnotation("mention", "mention")
                append("@")
                append(mention.name)
                append(" ")
            }
        }

        append(text)
    }

@Preview
@Composable
internal fun CommentEditPreview() {
    AngKorChatProtoFeedTheme {
        var text by remember {
            mutableStateOf("")
        }
        CommentEdit(text = text, mention = sampleUsers[0], onTextChange = {
            text = it
        }, onMentionRemove = {}) {

        }
    }
}