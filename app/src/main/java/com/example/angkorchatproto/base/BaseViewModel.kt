package com.example.angkorchatproto.base

import androidx.lifecycle.ViewModel

/**
 * Package Name : com.example.angkorchatproto.base
 * Class Name : BaseViewModel
 * Description :
 * Created by de5ember on 2023/05/08.
 */
abstract class BaseViewModel: ViewModel() {
    abstract fun getViewModelTag(): String
}