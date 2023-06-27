package com.example.angkorchatproto.pay.room

import androidx.room.*

@Entity(tableName = "tb_account")
data class AccountInfo(

    /**순번*/
    @PrimaryKey(autoGenerate = true)
    val index: Int,

    /**거래번호*/
    @ColumnInfo(name = "transfer_number")
    val transferNumber: String?,


    /**계좌번호(임의로 등록)*/
    @ColumnInfo(name = "account_number")
    val accountNumber: String,

    /**계좌 ID(번호로 등록)*/
    @ColumnInfo(name = "user_id")
    val userId: String,

    /**계좌의 현금*/
    val amount: Int?,

    /**계좌의 포인트*/
    val point: Int?,

    /**결제 분류*/
    val content: String,

    /**입금자명*/
    val depositor: String?,

    /**거래처*/
    @ColumnInfo(name = "pay_to")
    val payTo: String?,

    /**은형명*/
    @ColumnInfo(name = "bank_name")
    val bankName: String?,

    /**거래 시간*/
    val time: String

)





