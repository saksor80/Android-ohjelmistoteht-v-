package fi.sorja.sakari.ohjelmistotehtava

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

// Määritellään MyAdapter-luokka, mitä käytetään listViewin näyttämiseen
class MyAdapter(context: Context,arrayListDetails:ArrayList<Drink>) : BaseAdapter(){

    private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<Drink>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    private class ViewHolder(row: View) {
        val tvName: TextView
        val tvCategory: TextView
        val tvAlcoholic: TextView
        val constraintLayoutLayout: ConstraintLayout

        init {
            this.tvName = row?.findViewById<TextView>(R.id.drink_name) as TextView
            this.tvCategory = row?.findViewById<TextView>(R.id.drink_category) as TextView
            this.tvAlcoholic = row?.findViewById<TextView>(R.id.drink_alcoholic) as TextView
            this.constraintLayoutLayout = row?.findViewById<ConstraintLayout>(R.id.constraintLayout) as ConstraintLayout
        }
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Määritellään Viewholder listViewille
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (p1 == null) {
            view = this.layoutInflater.inflate(R.layout.drink_list_item, p2, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = p1
            holder = p1.tag as ViewHolder
        }

        holder.tvName.text = arrayListDetails.get(p0).name
        holder.tvCategory.text = arrayListDetails.get(p0).category
        holder.tvAlcoholic.text = arrayListDetails.get(p0).alcoholic
        return view
    }
}


