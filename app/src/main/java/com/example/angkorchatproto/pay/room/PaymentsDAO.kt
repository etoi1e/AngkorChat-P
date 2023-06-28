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

    /** 거래 번호로 거래 조회 */
    @Query("SELECT * FROM tb_account WHERE transfer_number = :transferNumber")
    fun getTransfer(transferNumber : String):AccountInfo

    /** 해당 계좌의 남은 금액 */
    @Query("SELECT SUM(amount) FROM tb_account WHERE account_number = :account")
    fun getAmount(account : String):Int

    /** 해당 계좌의 남은 포인트 */
    @Query("SELECT SUM(point) FROM tb_account WHERE account_number = :account")
    fun getPoint(account : String):Int

    /** 거래 분류별 호출 */
    @Query("SELECT * FROM tb_account WHERE content = :content AND account_number = :account")
    fun getAllByContent(account : String,content : String):List<AccountInfo>

    /** Point 분류별 호출 */
    @Query("SELECT * FROM tb_account WHERE content = :content AND account_number = :account AND type = :type AND amount = 0 ")
    fun getPointHistoryByType(account : String,content : String, type : String):List<AccountInfo>

    /** Amount 거래 전체분류별 호출*/
    @Query("SELECT * FROM tb_account WHERE content = :content AND account_number = :account AND point = 0 ")
    fun getAllAmountHistory(account : String,content : String):List<AccountInfo>

    /** Amount 분류별 호출*/
    @Query("SELECT * FROM tb_account WHERE content = :content AND account_number = :account AND type = :type AND point = 0 ")
    fun getAmountHistoryByType(account : String,content : String, type : String):List<AccountInfo>

    /** 시간순 호출 time = ASC or DESC*/
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY :time")
    fun getAllByTime(account : String,time : Long):List<AccountInfo>

    /** 높은 Amount 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY amount DESC")
    fun getAllByAmountHigh(account : String):List<AccountInfo>

    /** 낮은 Amount 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY amount ASC")
    fun getAllByAmountLow(account : String):List<AccountInfo>

    /** 높은 Point 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY point DESC")
    fun getAllByPointHigh(account : String):List<AccountInfo>

    /** 낮은 Point 순 호출 */
    @Query("SELECT * FROM tb_account  WHERE account_number = :account ORDER BY point ASC")
    fun getAllByPointLow(account : String):List<AccountInfo>


}

