package org.d3if2114.hitungpersegipanjang.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hasil")
data class PersegiPanjangEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var panjang: Float,
    var lebar: Float,
)
