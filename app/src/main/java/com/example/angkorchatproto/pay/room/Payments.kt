package com.example.angkorchatproto.pay.room

import androidx.room.*

@Entity(tableName = "tb_account")
data class AccountInfo (

    /**계좌번호(임의로 등록)*/
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "account_number")
    val accountNumber:String,

    /**계좌 ID(번호로 등록)*/
    @ColumnInfo(name = "user_id") val userId:String,

    /**계좌 PW*/
    val pin:String,

    /**은행 분류*/
    val bank: String,

    /**계좌의 잔고*/
    val amount: Int,

    /**계좌의 포인트*/
    val point: Int
        )




@Entity(foreignKeys = [ForeignKey(
    entity = AccountInfo::class,
    parentColumns = arrayOf("account_number"),
    childColumns = arrayOf("pay_from"),
    onDelete = ForeignKey.CASCADE
)], indices = [Index(value = ["pay_from"])],
    tableName =  "tb_transfer")

data class TransferInfo(

    /**거래번호(자동생성)*/
    @PrimaryKey(autoGenerate = true) val transferNum: Int,

    /**결제한 계좌(외래키)*/
    @ColumnInfo(name = "pay_from") val payFrom: String,

    /**결제받은 계좌*/
    @ColumnInfo(name = "pay_to") val payTo: String,

    /**결제한 금액*/
    val amount: Int,

    /**결제 비밀번호*/
    val pin: String,

    /**결제 분류*/
    val type: String,

    /**결제 시간*/
    val date: String
)