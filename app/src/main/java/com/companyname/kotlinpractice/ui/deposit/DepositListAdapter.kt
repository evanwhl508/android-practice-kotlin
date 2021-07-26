package com.companyname.kotlinpractice.ui.deposit
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.companyname.kotlinpractice.R

class DepositListAdapter internal constructor(
    private var depositList: Array<String> = arrayOf()
) :
    BaseAdapter() {

    override fun getCount(): Int {
        depositList.let{return it.size} ?: run {return 0 }
    }

    override fun getItem(position: Int): Any {
        depositList.let{return it[position]} ?: run {return ""}
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    private lateinit var vh: ViewHolder

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var v: View? = view
        Log.e("1111111", "onCreate: " + depositList.toString())

//        v?.let {
//            vh = it.tag as ViewHolder
//        }?: run {
//            v = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item, parent, false)
//            vh = ViewHolder()
//            vh.content = v!!.findViewById(R.id.tv_content)
//            v!!.tag = vh
//        }

        if (v == null) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item, parent, false)
            vh = ViewHolder()
            vh.content = v.findViewById(R.id.tv_content)
            v.tag = vh
        } else {
            vh = v.tag as ViewHolder
        }
        val content = getItem(position) as String
        vh.content.text = content
        return v!!
    }

    class ViewHolder {
        lateinit var content: TextView
    }
}