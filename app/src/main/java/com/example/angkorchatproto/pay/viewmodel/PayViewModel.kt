package com.example.angkorchatproto.emojistore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.angkorchatproto.R
import com.example.angkorchatproto.base.BaseViewModel
import com.example.angkorchatproto.emojistore.data.Data

/**
 * Package Name : com.example.angkorchatproto.emojistore.viewmodel
 * Class Name : EmojiStoreViewModel
 * Description :
 * Created by de5ember on 2023/05/08.
 */
class PayViewModel: BaseViewModel() {
    var payType: String? = ""
    var checkAccount: Boolean = false
    var testLiveData = MutableLiveData<Boolean>()
    var testEmojiList = arrayListOf(
        Data.EmojiInfo(R.drawable.nunu1, "NUNU Emoticon", "Union", 100),
        Data.EmojiInfo(R.drawable.gana1, "GANA Emoticon", "Union", 200),
        Data.EmojiInfo(R.drawable.haha1, "HAHA Emoticon", "Union", 300),
    )

    override fun getViewModelTag(): String {
        return "PayViewModel"
    }
}