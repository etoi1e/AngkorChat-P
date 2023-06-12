package com.example.angkorchatproto.more

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentMoreBinding
import com.example.angkorchatproto.more.adapter.ServiceAdapter
import com.example.angkorchatproto.settings.SettingsActivity

class MoreFragment : Fragment() {
    lateinit var binding: FragmentMoreBinding
    private lateinit var serviceAdapter: ServiceAdapter
    private var serviceImg = arrayListOf<Int>(
        R.drawable.ic_location_line_bk_24,
        R.drawable.ic_delivery_line_bk_24,
        R.drawable.ic_webtoon_line_bk_24,
        R.drawable.ic_game_line_bk_24,
        R.drawable.ic_play_line_bk_24,
        R.drawable.ic_reservation_line_bk_24,
        R.drawable.ic_beauty_line_bk_24,
        R.drawable.ic_money_line_bk_24
    )
    private var serviceTitle = arrayListOf(
        "Mobility","Delivery","Webtoon","Game","OTT","Reservation","Beauty","Bank"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        binding.ivSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
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
                    Log.d("MoreFragment","${itemIdx}번 인덱스 $characterName 선택")
                }
            })
        binding.rvServicesMenu.adapter = serviceAdapter
    }

}