package com.example.angkorchatproto.friends

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.chat.ChatActivity

class Bdayadapter(val context : Context, val itemList : ArrayList<BDayVO>,val state : String ) :
RecyclerView.Adapter<Bdayadapter.ViewHolder>(){


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val imgProfileBday : ImageView
        val imgBdayIcon : ImageView
        val tvNameBday : TextView
        val tvDateBday : TextView
        val btnChatBday : Button

        init {
            imgProfileBday = itemView.findViewById(R.id.imgProfileBday)
            imgBdayIcon = itemView.findViewById(R.id.imgBdayIcon)
            tvNameBday = itemView.findViewById(R.id.tvNameBday)
            tvDateBday = itemView.findViewById(R.id.tvDateBday)
            btnChatBday = itemView.findViewById(R.id.btnChatBday)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.b_day_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.tvNameBday.text = item.name

        val resourceID =
            context.resources.getIdentifier(item.profile, "drawable", "com.example.angkorchatproto")

        Glide.with(context)
            .load(resourceID)
            .into(holder.imgProfileBday)


        if(state == "today"){

            holder.imgBdayIcon.visibility = View.VISIBLE

            holder.btnChatBday.visibility = View.VISIBLE

            holder.btnChatBday.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("name", item.name)
                intent.putExtra("number", "01055555555")
                intent.putExtra("profileDummy", "dummy_profile_03")
                context.startActivity(intent)
            }


            holder.tvDateBday.text = "Today"


        }

        if(state == "upcoming"){


            val item = itemList[position]

            holder.tvDateBday.text = "${item.birthMonth} ${item.birthDay}"

        }

        if(state == "past"){

            val item = itemList[position]

            holder.tvDateBday.text = "${item.birthMonth} ${item.birthDay}"

        }




    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}