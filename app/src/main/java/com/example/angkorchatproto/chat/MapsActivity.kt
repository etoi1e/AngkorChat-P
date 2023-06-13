package com.example.angkorchatproto.chat


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //지도 닫기
        binding.ivCloseMap.setOnClickListener {
            finish()
        }

        //구글맵 뷰에 적용
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    //onCreate 바깥


    //지도 띄우기
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        val check = intent.getStringExtra("check")

        if (check == "false") {
            //상대방이 보낸 지도 클릭 시

            //서치바 제거
            binding.svSearchFriendFriends.visibility = View.GONE
            binding.imageView16.visibility = View.GONE


            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            Log.d("TAG-location", LatLng(latitude, longitude).toString())
            Log.d("TAG-check", check)

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15.0f))

        } else {


            //내 위치버튼 생성(권한체크)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MapsActivity, arrayOf(
                        ACCESS_COARSE_LOCATION,
                        ACCESS_FINE_LOCATION
                    ), 0
                )
                return
            }
            mMap.isMyLocationEnabled = true

            val manager = getSystemService(LOCATION_SERVICE) as LocationManager
            val location: Location? = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            val geocoder = Geocoder(this)
            var add: String

            location?.let {

                //현재 위치로 시작
                var latitude: Double = it.latitude
                var longitude: Double = it.longitude
                var mapScreenshot: String? = ""

                val currLocation = LatLng(latitude, longitude)

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 15.0f))

                try {
                    add =
                        geocoder.getFromLocation(latitude, longitude, 1)!!.first().getAddressLine(0)
                } catch (e: Exception) {
                    e.printStackTrace()
                }


                //카메라 이동 감지
                mMap.setOnCameraIdleListener {

                    //카메라 중심 위치 값
                    var point = mMap.cameraPosition

                    latitude = point.target.latitude
                    longitude = point.target.longitude

                    mMap.snapshot {

                        val storage: File = this.cacheDir // 이 부분이 임시파일 저장 경로

                        val fileName = "$it.jpg" // 파일이름

                        val tempFile = File(storage, fileName)

                        try {
                            tempFile.createNewFile() // 파일을 생성해주고

                            val out = FileOutputStream(tempFile)

                            it.compress(
                                Bitmap.CompressFormat.JPEG,
                                90,
                                out
                            ) // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌

                            out.close() // 마무리로 닫기

                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        mapScreenshot = tempFile.absolutePath

                    }

                    try {
                        add =
                            geocoder.getFromLocation(latitude, longitude, 1)!!.first()
                                .getAddressLine(0)
                        binding.layOutSentLocation.visibility = View.VISIBLE
                        binding.tvLocationAdd.text = add.substringBefore(",")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("TAG-add", e.toString())
                    }


                }



                binding.layOutSentLocation.setOnClickListener {
                    val intent = Intent(this@MapsActivity, ChatActivity::class.java)


                    val storageRef = Firebase.storage.reference

                    val file = File(mapScreenshot)

                    val fileRef = storageRef.child(file.name)

                    val inputStream = FileInputStream(file)

                    val uploadTask = fileRef.putStream(inputStream)

                    uploadTask.addOnSuccessListener {
                        intent.putExtra("latitude", latitude)
                        intent.putExtra("longitude", longitude)
                        intent.putExtra("mapScreenshot", fileRef.toString())

                        setResult(1994, intent)
                        finish()
                    }.addOnFailureListener { exception ->

                        exception.printStackTrace()
                    }


                }


            }

        }
    }

}


