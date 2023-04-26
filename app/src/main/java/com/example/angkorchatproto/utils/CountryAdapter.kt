package com.example.angkorchatproto.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.angkorchatproto.R

class CountryAdapter(context: Context, countries: List<Country>) :
    ArrayAdapter<Country>(context, R.layout.spinner_items, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.spinner_items, parent, false)

        val country = getItem(position)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(country!!.flag)

        val textView = view.findViewById<TextView>(R.id.textView)
        textView.text = country!!.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

}