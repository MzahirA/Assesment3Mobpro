package org.d3if2114.hitungpersegipanjang.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2114.hitungpersegipanjang.db.PersegiPanjangDao

class HistoriViewModel(private val db: PersegiPanjangDao) : ViewModel() {
    val data = db.getLastPersegiPanjang()
    fun hapusSemuaData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }

}
