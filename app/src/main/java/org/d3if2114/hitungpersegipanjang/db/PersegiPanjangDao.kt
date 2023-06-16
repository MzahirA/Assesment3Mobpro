package org.d3if2114.hitungpersegipanjang.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersegiPanjangDao {
    @Insert
    fun insert(hasil: PersegiPanjangEntity)

    @Query("SELECT * FROM hasil ORDER BY id DESC")
    fun getLastPersegiPanjang(): LiveData<List<PersegiPanjangEntity>>

    @Query("DELETE FROM hasil")
    fun clearData()
}
