package com.emamagic.common_ui.widget.searchable_spinner

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.emamagic.common_ui.R
import com.emamagic.common_ui.widget.CircleImageView
import com.emamagic.common_ui.widget.FontTextView
import java.util.*

class MySpinnerAdapter(
    context: Context,
    val resId: Int,
    var list: List<SpinnerItemWorkspace>
    ): ArrayAdapter<SpinnerItemWorkspace>(context, resId, list), Filterable {


    private val tp: Typeface = Typeface.createFromAsset(
        getContext().assets,
        "fonts/IRANYekanMobileRegular.ttf"
    )
    private var filter: CustomFilter? = null
    val filterList: ArrayList<SpinnerItemWorkspace> = list as ArrayList<SpinnerItemWorkspace>


    override fun getItem(position: Int): SpinnerItemWorkspace {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list.indexOf(getItem(position)).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(resId, parent, false)
        }

        val item = getItem(position)

        if (v != null) {
            val title: FontTextView = v.findViewById(R.id.txt_spinner_workspace)
            val image: CircleImageView = v.findViewById(R.id.img_spinner_workspace)
            if (item.title == "همه فضاهای کاری") {
                image.visibility = View.GONE
            } else {
                image.visibility = View.VISIBLE
//                ImageLoader.loadAvatar(image, item.image, item.title)
            }
            title.setTextColor(Color.parseColor("#808080"))
            title.typeface = tp
            title.text = item.title
        }

        return v ?: super.getView(position, convertView, parent)
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = CustomFilter()
        }
        return filter as CustomFilter
    }

    inner class CustomFilter : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val results = FilterResults()
            if (p0 != null && p0.isNotEmpty()) {
                val a = p0.toString().toLowerCase()
                val filters = arrayListOf<SpinnerItemWorkspace>()
                filterList.forEachIndexed { index, _ ->

                    if (filterList[index].title.toLowerCase().contains(a)) {
                        filters.add(SpinnerItemWorkspace(filterList[index].title, filterList[index].image))
                    }

                }
                results.count = filters.size
                results.values = filters
            } else {
                results.count = filterList.size
                results.values = filterList
            }
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            list = p1?.values as List<SpinnerItemWorkspace>
            notifyDataSetChanged()
        }

    }

}