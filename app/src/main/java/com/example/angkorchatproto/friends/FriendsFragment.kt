package com.example.angkorchatproto.friends


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.profile.ProfileActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.databinding.FragmentFriendsBinding



class FriendsFragment : Fragment() {

    lateinit var binding: FragmentFriendsBinding
    lateinit var favoriteAdapter: FriendsAdapter
    lateinit var friendAdapter: FriendsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        var favoriteList = arrayListOf<UserVO>()
        var allList = arrayListOf<UserVO>()


        //포커스 컨트롤
        binding.friendLayout.setOnClickListener {
            binding.svSearchFriendFriends.clearFocus()
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.svSearchFriendFriends.windowToken, 0)

        }


        //친구추가 버튼 클릭시
        binding.imgAddFriendsFriends.setOnClickListener {
            val intent = Intent(requireContext(), AddFriendsActivity::class.java)
            startActivity(intent)

        }

//        //로그인한 계정 번호 불러오기
//        //SharedPreferences
//        val shared = requireContext().getSharedPreferences("loginNumber", 0)
//        val userNumber = shared.getString("userNumber", "").toString()


        //즐겨찾는 친구 더미 목록
        favoriteList.add(UserVO("Dad", "", "ic_profile_default_72", "010-4444-4444", "dummyDad"))
        favoriteList.add(UserVO("Mom", "Summer!", "dummy_profile_06", "010-8888-8888", "dummyMom"))


        //전체목록
        allList.add(UserVO("Adam Smith", "Working...", "dummy_profile_04", "010-1111-1111", "dummyAdam"))
        allList.add(UserVO("Brother", "Paw", "dummy_profile_07", "010-2222-2222", "dummyBro"))
        allList.add(UserVO("Cindy", "Hello, I'm Cindy", "dummy_profile_01", "010-3333-3333", "dummyMom"))
        allList.add(UserVO("Dad", "", "ic_profile_default_72", "010-4444-4444", "dummyDad"))
        allList.add(UserVO("Emma", "", "dummy_profile_03", "010-5555-5555", "dummyEmma"))
        allList.add(UserVO("Jessica", "❤", "dummy_profile_02", "010-6666-6666", "dummyJessica"))
        allList.add(UserVO("John Kim", "Hiking", "dummy_profile_05", "010-7777-7777", "dummyJohn"))
        allList.add(UserVO("Mom", "Summer!", "dummy_profile_06", "010-8888-8888", "dummyMom"))


        //즐겨찾는 친구 Adapter
        favoriteAdapter = FriendsAdapter(requireContext(), favoriteList)
        favoriteAdapter.setOnItemClickListener(object : FriendsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                val intent = Intent(requireContext(), ProfileActivity::class.java)

                intent.putExtra("name", favoriteList[position].name)
                intent.putExtra("number", favoriteList[position].phone)
                intent.putExtra("email", favoriteList[position].email)
                intent.putExtra("profile", favoriteList[position].profile)

                startActivity(intent)

            }

        })

        //즐겨찾는 친구 Count
        val countFriends = favoriteList.size.toString()
        binding.tvCountFavoriteFriends.text = countFriends

        //friendList = getFirebase()

        //friendList = ArrayList()


        binding.rvFavoriteFriends.adapter = favoriteAdapter
        binding.rvFavoriteFriends.layoutManager = GridLayoutManager(requireContext(), 1)


        //일반 친구 Adapter
        friendAdapter = FriendsAdapter(requireContext(), allList)
        friendAdapter.setOnItemClickListener(object : FriendsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                val intent = Intent(requireContext(), ProfileActivity::class.java)

                intent.putExtra("name", allList[position].name)
                intent.putExtra("number", allList[position].phone)
                intent.putExtra("email", allList[position].email)
                intent.putExtra("profile", allList[position].profile)

                startActivity(intent)

            }

        })

        //일반 친구 Count
        val countFriend = allList.size.toString()
        binding.tvCountFriendsFriends.text = countFriend

        //friendList = getFirebase()

        //friendList = ArrayList()


        binding.rvFriendsFriends.adapter = friendAdapter
        binding.rvFriendsFriends.layoutManager = GridLayoutManager(requireContext(), 1)


        //친구 목록 접기
        binding.imgFoldFavoriteFriends.setOnClickListener {
            if (binding.imgFoldFavoriteFriends.tag == true) { //목록 펼치기
                binding.imgFoldFavoriteFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFavoriteFriends.visibility = View.VISIBLE
                binding.imgFoldFavoriteFriends.tag = false

            } else { //목록 접기
                binding.imgFoldFavoriteFriends.setImageResource(R.drawable.ic_indecator_down_16)
                binding.rvFavoriteFriends.visibility = View.GONE
                binding.imgFoldFavoriteFriends.tag = true

            }
        }

        binding.imgFoldFriendsFriends.setOnClickListener {
            if (binding.imgFoldFriendsFriends.tag == true) { //목록 펼치기
                binding.imgFoldFriendsFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFriendsFriends.visibility = View.VISIBLE
                binding.imgFoldFriendsFriends.tag = false

            } else { //목록 접기
                binding.imgFoldFriendsFriends.setImageResource(R.drawable.ic_indecator_down_16)
                binding.rvFriendsFriends.visibility = View.GONE
                binding.imgFoldFriendsFriends.tag = true

            }
        }


        // 검색 기능
        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {
                    friendAdapter.getFilter().filter(s)
                    binding.tvFriendsFriends.text = "Result"
                    binding.tvFavoriteFriends.visibility = View.GONE
                    binding.rvFavoriteFriends.visibility = View.GONE
                    binding.imgFoldFavoriteFriends.visibility = View.GONE
                    binding.imgFoldFriendsFriends.visibility = View.GONE
                    binding.tvCountFavoriteFriends.visibility = View.GONE
                    binding.tvCountFriendsFriends.visibility = View.GONE

                    if (s == "" || s == null) {
                        binding.tvFriendsFriends.text = "Friends"
                        binding.tvFavoriteFriends.visibility = View.VISIBLE
                        binding.rvFavoriteFriends.visibility = View.VISIBLE
                        binding.imgFoldFriendsFriends.visibility = View.VISIBLE
                        binding.imgFoldFavoriteFriends.visibility = View.VISIBLE
                        binding.tvCountFavoriteFriends.visibility = View.VISIBLE
                        binding.tvCountFriendsFriends.text = allList.size.toString()

                    }
                    return false
                }

            }



        binding.svSearchFriendFriends.setOnQueryTextListener(searchViewTextListener)



        return binding.root

    }

//    fun getFirebase(): ArrayList<UserVO> {
//        friendList = ArrayList()
//
//        //SharedPreferences
//        val shared = requireContext().getSharedPreferences("loginNumber", 0)
//        val userNumber = shared.getString("userNumber", "").toString()
//
//        val friendRef = FBdataBase.getFriendRef()
//
//        friendRef.child(userNumber).addChildEventListener(object : ChildEventListener {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                //Log.d("TAG-전체친구목록",snapshot.toString())
//                val firendItem = snapshot.getValue<UserVO>() as UserVO
//                friendList.add(firendItem)
//
//                //전체 친구 Count
//                if (friendList.size == 0) {
//                    binding.tvCountFriendsFriends.text = "0"
//                } else {
//                    val testList = friendList.size.toString()
//                    binding.tvCountFriendsFriends.text = testList
//                }
//
//                friendAdapter.notifyDataSetChanged()
//
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                friendAdapter.notifyDataSetChanged()
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//
//
//
//        return friendList
//    }


}