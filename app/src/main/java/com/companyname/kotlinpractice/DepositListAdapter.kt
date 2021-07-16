package com.companyname.kotlinpractice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DepositListAdapter internal constructor(
    private val context: Context,
    var depositList: Array<String>?
) :
    BaseAdapter() {

    override fun getCount(): Int {
        depositList?.let{return it.size}?: run {return 0 }
    }

    override fun getItem(position: Int): Any {
        depositList?.let{return it[position]}?: run {return ""}
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        var vh: ViewHolder
        var view: View = view

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false)
            vh = ViewHolder()
            vh.content = view.findViewById(R.id.tv_content)
            view.tag = vh
        } else {
            vh = view.tag as ViewHolder
        }
        val content = getItem(position) as String
        vh.content.text = content
        return view
    }

    class ViewHolder {
        lateinit var content: TextView
    }
}