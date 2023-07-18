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
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
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

        //검색

        Places.initialize(applicationContext, "AIzaSyBtpGTyyCocpC1JvwYC5c-vynX5e268Mhw")

        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val searchPlace = place.name

                val geocoder = Geocoder(this@MapsActivity)
                var add = geocoder.getFromLocationName(searchPlace, 1)


                val latitude = add?.get(0)?.latitude
                val longitude = add?.get(0)?.longitude

                if (latitude != null && longitude != null) {
                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                latitude!!,
                                longitude!!
                            ), 15.0f
                        )
                    )
                }


            }

            override fun onError(status: Status) {
                // TODO: Handle the error.

            }
        })


    }

    //onCreate 바깥


    //지도 띄우기
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        val check = intent.getStringExtra("check")

        var latitude: Double = 0.0
        var longitude: Double = 0.0

        if (check == "false") {
            //상대방이 보낸 지도 클릭 시

            binding.imageView18.visibility = View.GONE
            binding.autocompleteLayout.visibility = View.GONE

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


            latitude = intent.getDoubleExtra("latitude", 0.0)
            longitude = intent.getDoubleExtra("longitude", 0.0)
            Log.d("TAG-location", LatLng(latitude, longitude).toString())
            Log.d("TAG-check", check)

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15.0f))

            var markerOptions = MarkerOptions()

            mMap.addMarker(markerOptions.position(LatLng(latitude,longitude)))

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
                latitude = it.latitude
                longitude = it.longitude


                val currLocation = LatLng(latitude, longitude)

            Log.d("TAG-add", currLocation.toString())
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

            }


            // 위치 전송
            binding.layOutSentLocation.setOnClickListener {

                var mapScreenshot: String? = ""

                val intent = Intent(this@MapsActivity, ChatActivity::class.java)

                mMap.snapshot {

                    val storage: File = this.cacheDir // 이 부분이 임시파일 저장 경로

                    val fileName = "$it.jpg" // 파일이름

                    val tempFile = File(storage, fileName)

                    try {
                        tempFile.createNewFile() // 파일을 생성해주고

                        val out = FileOutputStream(tempFile)

                        it!!.compress(
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


