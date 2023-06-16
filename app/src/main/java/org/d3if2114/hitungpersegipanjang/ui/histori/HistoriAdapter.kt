package org.d3if2114.hitungpersegipanjang.ui.histori

import android.graphics.drawable.GradientDrawable
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if2114.hitungpersegipanjang.R
import org.d3if2114.hitungpersegipanjang.databinding.ItemHistoriBinding
import org.d3if2114.hitungpersegipanjang.db.PersegiPanjangEntity
import org.d3if2114.hitungpersegipanjang.model.hitungPersegiPanjang
import java.text.SimpleDateFormat
import java.util.*


class HistoriAdapter :
    ListAdapter<PersegiPanjangEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<PersegiPanjangEntity>() {
                override fun areItemsTheSame(
                    oldData: PersegiPanjangEntity, newData: PersegiPanjangEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: PersegiPanjangEntity, newData: PersegiPanjangEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        private val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: PersegiPanjangEntity) = with(binding) {
            val hasilHitung = item.hitungPersegiPanjang()
            bgTextView.text = "H"
            val colorRes =
                if (hasilHitung.hasilLuas > 500) {
                    R.color.tinggi
                }else {
                    R.color.kecil
                }

            val circleBg = bgTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            hasilTextView.text = root.context.getString(
                R.string.hasil_x,
                hasilHitung.hasilLuas, hasilHitung.hasilKeliling.toString()
            )
            dataTextView.text = root.context.getString(
                R.string.data_x,
                item.panjang, item.lebar
            )
            root.setOnClickListener {
                binding.hapusButton.visibility = View.VISIBLE
            }

        }


    }

}