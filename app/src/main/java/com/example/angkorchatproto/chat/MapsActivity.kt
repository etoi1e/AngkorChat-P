package com.example.angkorchatproto.chat

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.angkorchatproto.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.angkorchatproto.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.CameraPosition

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivCloseMap.setOnClickListener {
            finish()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    //onCreate 바깥

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

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
        val location: Location? = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

//        var latitude: Double = 0.0
//        var longitude: Double = 0.0
        val geocoder = Geocoder(this)
        var add: String


        location?.let {
            var latitude = location.latitude
            var longitude = location.longitude
            val accuracy = location.accuracy

            Log.d("gps 받아오기", "{$latitude}, {$longitude}")

            // Add a marker in Sydney and move the camera
            val currLocation = LatLng(latitude, longitude)
            mMap.addMarker(
                MarkerOptions()
                    .position(currLocation)
            )

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation,15.0f))


            //GRPC 오류? try catch 문으로 오류 대처
            try {
                add = geocoder.getFromLocation(latitude, longitude, 1)!!.first().getAddressLine(0)
                Log.d("TAG-add", add)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("TAG-add", e.toString())
            }


        }


    }
}