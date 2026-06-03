package com.bgmi.serverglitch.activities

import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bgmi.serverglitch.models.response.ServerAccessResponse

class ServerAccessAdapter(
    private val accesses: List<ServerAccessResponse>,
    private val onStop: (Long) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = accesses.size

    override fun getItem(position: Int): Any = accesses[position]

    override fun getItemId(position: Int): Long = accesses[position].id

    override fun getView(position: Int, convertView: android.view.View?, parent: ViewGroup?): android.view.View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)

        val access = accesses[position]
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = access.serverName
        text2.text = "Status: ${access.status}"

        return view
    }
}
