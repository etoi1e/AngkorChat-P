package com.example.feed.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import com.example.feed.sample.sampleUsers
import com.example.feed.screen.TagPeople
import com.example.model.User
import java.util.ArrayList

class OpenTagPeopleContract : ActivityResultContract<Unit, List<User>>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, TagPeopleActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<User> {
        if (resultCode != Activity.RESULT_OK) return emptyList()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //API 33
            return intent?.extras?.getParcelableArrayList(INTENT_USERS, User::class.java)
                ?: emptyList()
        }

        return intent?.extras?.getParcelableArrayList(INTENT_USERS) ?: emptyList()
    }

    companion object {
        const val INTENT_USERS = "INTENT_USERS"
    }
}

class TagPeopleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TagPeople(
                peoples = sampleUsers,
                onTagButtonClicked = {
                    setResult(
                        RESULT_OK,
                        Intent().putParcelableArrayListExtra(
                            OpenTagPeopleContract.INTENT_USERS,
                            ArrayList(it)
                        )
                    )
                    finish()
                },
                onBack = { finish() })
        }
    }
}