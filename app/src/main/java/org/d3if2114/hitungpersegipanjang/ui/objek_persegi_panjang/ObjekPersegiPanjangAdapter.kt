package org.d3if2114.hitungpersegipanjang.ui.objek_persegi_panjang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if2114.hitungpersegipanjang.R
import org.d3if2114.hitungpersegipanjang.databinding.ItemObjekPersegiPanjangBinding
import org.d3if2114.hitungpersegipanjang.model.ObjekPersegiPanjang
import org.d3if2114.hitungpersegipanjang.network.ObjekPersegiPanjangApiService

class ObjekPersegiPanjangAdapter : RecyclerView.Adapter<ObjekPersegiPanjangAdapter.ViewHolder>() {
    private val data = mutableListOf<ObjekPersegiPanjang>()
    fun updateData(newData: List<ObjekPersegiPanjang>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(
        private val binding: ItemObjekPersegiPanjangBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(objekPersegiPanjang: ObjekPersegiPanjang) = with(binding) {
            nama.text = objekPersegiPanjang.nama
            Glide.with(imageView.context)
                .load(ObjekPersegiPanjangApiService.getObjekPersegiPanjangUrl(objekPersegiPanjang.gambar))
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemObjekPersegiPanjangBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}