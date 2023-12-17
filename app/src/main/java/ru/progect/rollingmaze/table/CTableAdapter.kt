package ru.progect.rollingmaze.table

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.progect.rollingmaze.R

class CTableAdapter(private val tableManager: CTableManager) : RecyclerView.Adapter<CTableAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.table_row_template, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tableManager.getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return tableManager.dataRowList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNumber: TextView = itemView.findViewById(R.id.txtNumber)
        private val textViewName: TextView = itemView.findViewById(R.id.txtName)
        private val textViewResult: TextView = itemView.findViewById(R.id.txtResult)

        fun bind(item: DataRow) {
            textViewNumber.text = item.numb
            textViewName.text = item.name
            textViewResult.text = item.result
        }
    }
}
