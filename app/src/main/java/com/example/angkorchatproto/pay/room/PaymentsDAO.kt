package com.example.angkorchatproto.pay.room

import androidx.room.*


@Dao
interface PaymentsDAO {



    //Create
    /** 새로운 계좌 생성 */
    @Insert(entity = AccountInfo::class)
    fun insertAccount(vararg accountInfo: AccountInfo)

    /** 새로운 거래 생성 */
    @Insert(entity = TransferInfo::class)
    fun insertTransfer(vararg transferInfo: TransferInfo)



    //Read

    /** 해당 유저의 계좌번호 */
    @Query("SELECT account_number FROM tb_account WHERE user_id = :userId")
    fun getAccountNumber(userId : String):String

    /** 해당 계좌의 남은 금액 */
    @Query("SELECT amount FROM tb_account WHERE account_number = :account")
    fun getAmount(account : String):Int

    /** 해당 계좌의 남은 포인트 */
    @Query("SELECT point FROM tb_account WHERE account_number = :account")
    fun getPoint(account : String):Int

    /** 해당 계좌의 모든 거래내역 */
    @Query("SELECT * FROM tb_transfer WHERE pay_from = :account OR pay_to = :account")
    fun getAllHistory(account : String):List<TransferInfo>

    /** 해당 계좌에서 이체 보낸 거래내역 */
    @Query("SELECT * FROM tb_transfer WHERE pay_from = :account")
    fun getPaidHistory(account : String):List<TransferInfo>

    /** 해당 계좌로 이체 받은 거래내역 */
    @Query("SELECT * FROM tb_transfer WHERE pay_to = :account")
    fun getReceivedHistory(account : String):List<TransferInfo>

    /** 해당 계좌에서 이체 보낸 거래내역 중 카테고리 선택 */
    @Query("SELECT * FROM tb_transfer WHERE pay_from = :account and type = :type")
    fun getPaidHistoryByType(account : String,type : String):List<TransferInfo>

    /** 해당 계좌에서 이체 보낸 거래내역 날짜별 오래된 순 조회 */
    @Query("SELECT * FROM tb_transfer WHERE pay_from = :account ORDER BY date ASC")
    fun getPaidHistoryByTimeOld(account : String):List<TransferInfo>

    /** 해당 계좌에서 이체 보낸 거래내역 날짜별 최신순 조회 */
    @Query("SELECT * FROM tb_transfer WHERE pay_from = :account ORDER BY date DESC")
    fun getPaidHistoryByTimeNew(account : String):List<TransferInfo>

    /** 해당 계좌로 이체 받은 거래내역 중 카테고리 선택 */
    @Query("SELECT * FROM tb_transfer WHERE pay_to = :account and type = :type")
    fun getReceivedHistoryByType(account : String, type : String):List<TransferInfo>

    /** 해당 계좌에서 이체 받은 거래내역 날짜별 오래된 순 조회 */
    @Query("SELECT * FROM tb_transfer WHERE pay_to = :account ORDER BY date ASC")
    fun getReceivedHistoryByTimeOld(account : String):List<TransferInfo>

    /** 해당 계좌에서 이체 받은 거래내역 날짜별 최신순 조회 */
    @Query("SELECT * FROM tb_transfer WHERE pay_to = :account ORDER BY date DESC")
    fun getReceivedHistoryByTimeNew(account : String):List<TransferInfo>



    //Update
    /** 해당 계좌 금액 변경 */
    @Query("UPDATE tb_account SET amount = :amount WHERE account_number = :accountNumber")
    fun topUpAmount(amount : Int,accountNumber: String)

    /** 해당 계좌 포인트 변경 */
    @Query("UPDATE tb_account SET point = :point WHERE account_number = :accountNumber")
    fun topUpPoint(point : Int,accountNumber: String)



    //Delete
    /** 특정 유저 계좌 삭제하기*/
    @Query("DELETE FROM tb_account WHERE user_id = :userId")
    fun delUserAccount(userId : String)

    /** 특정 계좌 거래내역 삭제하기*/
    @Query("DELETE FROM tb_transfer WHERE pay_to = :account OR pay_from = :account")
    fun delUserTransfer(account : String)



}

