package com.example.angkorchatproto.auth

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


fun PermissionSupport(context : Context) {

    //퍼미션 응답 처리 코드
    val multiplePermissionsCode = 100

    //필요한 퍼미션 리스트
    val requiredPermissions = arrayOf(
        Manifest.permission.READ_PHONE_NUMBERS,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET
    )

    //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
    var rejectedPermissionList = ArrayList<String>()

    //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
    for(permission in requiredPermissions){
        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            //만약 권한이 없다면 rejectedPermissionList에 추가
            rejectedPermissionList.add(permission)
        }
    }
    //거절된 퍼미션이 있다면...
    if(rejectedPermissionList.isNotEmpty()){
        //권한 요청!
        val array = arrayOfNulls<String>(rejectedPermissionList.size)
        ActivityCompat.requestPermissions(context as Activity, rejectedPermissionList.toArray(array), multiplePermissionsCode)
    }


    //권한 요청 결과 함수
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            multiplePermissionsCode -> {
                if(grantResults.isNotEmpty()) {
                    for((i, permission) in permissions.withIndex()) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //권한 획득 실패
                            Toast.makeText(context,"권환 획득 실패",Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }

}