package com.example.angkorchatproto.pay.room

import androidx.room.*


@Dao
interface PaymentsDAO {

    //Create
    /** 새로운 거래 생성 */
    @Insert(entity = AccountInfo::class)
    fun insertAccount(vararg accountInfo: AccountInfo)

    //Read
    /** 해당 유저의 계좌번호 */
    @Query("SELECT account_number FROM tb_account WHERE user_id = :userId")
    fun getAccountNumber(userId : String):String

    /** 해당 계좌의 남은 금액 */
    @Query("SELECT SUM(amount) FROM tb_account WHERE account_number = :account")
    fun getAmount(account : String):Int

    /** 해당 계좌의 남은 포인트 */
    @Query("SELECT SUM(point) FROM tb_account WHERE account_number = :account")
    fun getPoint(account : String):Int

    /** 거래 분류별 호출 */
    @Query("SELECT * FROM tb_account WHERE content = :content AND account_number = :account")
    fun getAllByContent(account : String,content : String):AccountInfo

    /** 시간순 호출 time = ASC or DESC*/
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY :time")
    fun getAllByTime(account : String,time : Long):AccountInfo

    /** 높은 Amount 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY amount DESC")
    fun getAllByAmountHigh(account : String):AccountInfo

    /** 낮은 Amount 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY amount ASC")
    fun getAllByAmountLow(account : String):AccountInfo

    /** 높은 Point 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY point DESC")
    fun getAllByPointHigh(account : String):AccountInfo

    /** 낮은 Point 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY point ASC")
    fun getAllByPointLow(account : String):AccountInfo


}

