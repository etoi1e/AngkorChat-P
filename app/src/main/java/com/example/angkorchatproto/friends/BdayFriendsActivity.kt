package com.example.angkorchatproto.friends

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.databinding.ActivityBdayFriendsBinding
import java.time.LocalDateTime

class BdayFriendsActivity : AppCompatActivity() {

    lateinit var binding : ActivityBdayFriendsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityBdayFriendsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgBtnBack.setOnClickListener {
            finish()
        }

        val todayList = ArrayList<BDayVO>()
        val upComingList = ArrayList<BDayVO>()
        val pastList = ArrayList<BDayVO>()

        val thisMonth = LocalDateTime.now().toLocalDate().month.toString()
        val thisDate = LocalDateTime.now().toLocalDate().dayOfMonth

        val upComingMonth1 = LocalDateTime.now().plusDays(1).month.toString()
        val upComingDay1 = LocalDateTime.now().plusDays(1).toLocalDate().dayOfMonth

        val upComingMonth2 = LocalDateTime.now().plusDays(3).month.toString()
        val upComingDay2 = LocalDateTime.now().plusDays(3).toLocalDate().dayOfMonth

        val upComingMonth3 = LocalDateTime.now().plusDays(6).month.toString()
        val upComingDay3 = LocalDateTime.now().plusDays(6).toLocalDate().dayOfMonth


        val pastMonth1 = LocalDateTime.now().minusDays(6).month.toString()
        val pastDate1 = LocalDateTime.now().minusDays(6).toLocalDate().dayOfMonth

        val pastMonth2 = LocalDateTime.now().minusDays(5).month.toString()
        val pastDate2 = LocalDateTime.now().minusDays(5).toLocalDate().dayOfMonth

        val pastMonth3 = LocalDateTime.now().minusDays(3).month.toString()
        val pastDate3 = LocalDateTime.now().minusDays(3).toLocalDate().dayOfMonth


        todayList.add(BDayVO("Emma",thisMonth,thisDate,"dummy_profile_03"))

        upComingList.add(BDayVO("Adam Smith",upComingMonth1,upComingDay1,"dummy_profile_04"))
        upComingList.add(BDayVO("Jessica",upComingMonth2,upComingDay2,"dummy_profile_02"))
        upComingList.add(BDayVO("John",upComingMonth3,upComingDay3,"dummy_profile_05"))

        pastList.add(BDayVO("Brother",pastMonth1,pastDate1,"dummy_profile_07"))
        pastList.add(BDayVO("Cindy",pastMonth2,pastDate2,"dummy_profile_01"))
        pastList.add(BDayVO("Mom",pastMonth3,pastDate3,"dummy_profile_06"))


        val adapterT = Bdayadapter(this@BdayFriendsActivity,todayList,"today")
        val adapterU = Bdayadapter(this@BdayFriendsActivity,upComingList,"upcoming")
        val adapterP = Bdayadapter(this@BdayFriendsActivity,pastList,"past")

        binding.rvTodayFriends.adapter = adapterT
        binding.rvTodayFriends.layoutManager = GridLayoutManager(this@BdayFriendsActivity,1)
        binding.tvCountFriendsFriends.text  = todayList.size.toString()

        binding.rvUpcomingFriends.adapter = adapterU
        binding.rvUpcomingFriends.layoutManager = GridLayoutManager(this@BdayFriendsActivity,1)
        binding.tvCountUpcomingFriends.text  = upComingList.size.toString()

        binding.rvPastFriends.adapter = adapterP
        binding.rvPastFriends.layoutManager = GridLayoutManager(this@BdayFriendsActivity,1)
        binding.tvCountPastFriends.text  = pastList.size.toString()









    }



}