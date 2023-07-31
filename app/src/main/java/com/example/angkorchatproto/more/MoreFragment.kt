package com.example.angkorchatproto.more

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentMoreBinding
import com.example.angkorchatproto.more.adapter.ServiceAdapter
import com.example.angkorchatproto.pay.PayActivity
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import com.example.angkorchatproto.settings.SettingsActivity
import java.text.DecimalFormat
import java.time.LocalDateTime

class MoreFragment : Fragment() {
    lateinit var binding: FragmentMoreBinding
    private lateinit var serviceAdapter: ServiceAdapter
    private var serviceImg = arrayListOf<Int>(
        R.drawable.icon_line_24_ic_nunu_line_bk_28,
        R.drawable.ic_delivery_line_bk_24,
        R.drawable.ic_webtoon_line_bk_24,
        R.drawable.ic_game_line_bk_24,
        R.drawable.ic_play_line_bk_24,
        R.drawable.ic_reservation_line_bk_24,
        R.drawable.ic_note_line_bk_24,
        R.drawable.ic_money_line_bk_24,
        R.drawable.ic_location_line_bk_24
    )
    private var serviceTitle = arrayListOf(
        "Friends", "Eats", "Webtoon", "Games", "Play", "Check", "Echoes", "Bank","Move"
    )

    var checkAccount = false

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onResume() {
        super.onResume()

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        //Local DB 연결
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        //동일 계좌 생성


        if (db != null) {
            val accountNumber = db.paymentDao().getAccountNumber(myNumber)
            if (accountNumber != "" && accountNumber != null) {
                checkAccount = true

                val numberFormatter = DecimalFormat("###,###")
                //계좌 있는 경우 정보 가져와서 삽입
                val accountPoint = numberFormatter.format(db.paymentDao().getPoint(accountNumber))
                if (binding.tvPayPoint.text != "$accountPoint P") {
                    binding.tvPayPoint.text = "$accountPoint P"
                }
            } else {
                val newAccountNumber = "1111-2222-3333-4444"
                val time = LocalDateTime.now().toString()

                val newAccount =
                    AccountInfo(
                        0,
                        "0",
                        newAccountNumber,
                        myNumber,
                        3000,
                        10000,
                        "new",
                        "",
                        "",
                        "",
                        "",
                        time
                    )

                db.paymentDao().insertAccount(newAccount)

                checkAccount = true

                val numberFormatter = DecimalFormat("###,###")
                //계좌 있는 경우 정보 가져와서 삽입
                val accountPoint = numberFormatter.format(db.paymentDao().getPoint(accountNumber))
                if (binding.tvPayPoint.text != "$accountPoint P") {
                    binding.tvPayPoint.text = "10,000 P"
                }


            }

            val point = binding.tvPayPoint.text.toString()
            val oldPoint = point.replace("P", "")


            if (oldPoint == "0") {
                //프래그먼트 새로고침
                val ft = requireFragmentManager().beginTransaction()
                ft.detach(this).attach(this).commit()

            }
        }


    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoreBinding.inflate(inflater, container, false)
        binding.tvPayPoint.text = "10,000P"

        binding.ivSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.ivLogo.setOnClickListener {
            val intent = Intent(requireContext(), PayActivity::class.java)
            intent.putExtra("type", "payment")
            intent.putExtra("checkAccount", checkAccount)
            startActivity(intent)
        }

        binding.tvMyQr.setOnClickListener {
            val intent = Intent(requireContext(), PayActivity::class.java)
            intent.putExtra("type", "myQr")
            intent.putExtra("checkAccount", checkAccount)
            startActivity(intent)
        }

        binding.tvPayment.setOnClickListener {
            val intent = Intent(requireContext(), PayActivity::class.java)
            intent.putExtra("type", "payment")
            intent.putExtra("checkAccount", checkAccount)
            startActivity(intent)
        }

        binding.tvTransfer.setOnClickListener {
            val intent = Intent(requireContext(), PayActivity::class.java)
            intent.putExtra("type", "transfer")
            intent.putExtra("checkAccount", checkAccount)
            startActivity(intent)
        }

        binding.tvPayPoint.setOnClickListener {
            val intent = Intent(requireContext(), PayActivity::class.java)
            intent.putExtra("type", "point")
            intent.putExtra("checkAccount", checkAccount)
            startActivity(intent)
        }

        setServiceGridRecyclerView()
        return binding.root
    }

    private fun setServiceGridRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.rvServicesMenu.layoutManager = gridLayoutManager
        serviceAdapter = ServiceAdapter(
            requireContext(),
            serviceTitle,
            serviceImg,
            object : ServiceAdapter.OnServiceAdapterListener {
                override fun onItemClicked(itemIdx: Int?, characterName: String?) {
                }
            })
        binding.rvServicesMenu.adapter = serviceAdapter
    }


}