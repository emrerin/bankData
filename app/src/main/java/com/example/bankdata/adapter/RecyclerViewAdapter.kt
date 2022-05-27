package com.example.bankdata.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bankdata.databinding.RowLayoutBinding
import com.example.bankdata.model.BDataModel

class RecyclerViewAdapter(private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(bDataModel: BDataModel)
    }

    var bdataList = mutableListOf<BDataModel>()

    fun setBankList(bDataList: List<BDataModel>) {
        this.bdataList = bDataList.toMutableList()
        notifyDataSetChanged()
    }

    private val colors: Array<String> = arrayOf("#a1fb93","#f6bd0c","#d3df13","#29c1e1","#13bd27","#29c1e1", "#0d9de3", "#ffe48f")

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bDataModel: BDataModel, colors: Array<String>, position: Int, listener: Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(bDataModel)
            }
            binding.cardView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            binding.cityName.text = bDataModel.dc_SEHIR
            binding.bankCode.text = bDataModel.dc_BANK_KODU
            binding.branchName.text = bDataModel.dc_BANKA_SUBE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(bdataList[position], colors, position, listener)
    }

    override fun getItemCount(): Int {
        return bdataList.size
    }

    fun filter(text: String) {
        var text = text
        bdataList.clear()
        if (text.isEmpty()) {
            bdataList.addAll(bdataList)
        } else {
            text = text.toLowerCase()
            for (item in bdataList) {
                if (item.dc_SEHIR!!.toLowerCase().contains(text)) {
                    bdataList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}