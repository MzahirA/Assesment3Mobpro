package org.d3if2114.hitungpersegipanjang.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2114.hitungpersegipanjang.db.PersegiPanjangDao
import org.d3if2114.hitungpersegipanjang.db.PersegiPanjangEntity
import org.d3if2114.hitungpersegipanjang.model.HasilHitung
import org.d3if2114.hitungpersegipanjang.model.hitungPersegiPanjang

class HitungViewModel(private val db: PersegiPanjangDao) : ViewModel() {
    private val hasilHitung = MutableLiveData<HasilHitung?>()

    fun hitungPersegiPanjang(panjang: Float, lebar: Float) {
        val dataPersegiPanjang = PersegiPanjangEntity(
            panjang = panjang,
            lebar = lebar,
        )
        hasilHitung.value = dataPersegiPanjang.hitungPersegiPanjang()


        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataPersegiPanjang)
            }
        }

    }
    fun getHasilHitung(): LiveData<HasilHitung?> = hasilHitung

}
