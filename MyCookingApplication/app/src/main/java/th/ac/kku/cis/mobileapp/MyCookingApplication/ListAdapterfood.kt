package th.ac.kku.cis.mobileapp.MyCookingApplication

import android.content.Context as Context1
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.database.core.view.View

class ListAdapterfood(context: android.content.Context, toDoItemList: MutableList<food>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoItemList


    override fun getView(position: Int, convertView: android.view.View?, parent: ViewGroup?): android.view.View {
        // create object from view

        val name: String = itemList.get(position).name_f as String

        val view: android.view.View
        val vh: ListRowHolder

        // get list view
        if (convertView == null) {
            view = mInflater.inflate(R.layout.listview_foogshow, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        // add text to view
        vh.label2.text = name


        return view
    }


    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: android.view.View?) {
        val label2: TextView = row!!.findViewById<TextView>(R.id.TV_foodShow) as TextView

    }
}