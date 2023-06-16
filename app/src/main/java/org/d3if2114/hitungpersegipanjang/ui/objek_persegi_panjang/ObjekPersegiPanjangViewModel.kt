package org.d3if2114.hitungpersegipanjang.ui.objek_persegi_panjang

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if2114.hitungpersegipanjang.MainActivity
import org.d3if2114.hitungpersegipanjang.model.ObjekPersegiPanjang
import org.d3if2114.hitungpersegipanjang.network.ApiStatus
import org.d3if2114.hitungpersegipanjang.network.ObjekPersegiPanjangApiService
import org.d3if2114.hitungpersegipanjang.network.UpdateWorker
import java.util.concurrent.TimeUnit

class ObjekPersegiPanjangViewModel: ViewModel() {
    private val data = MutableLiveData<List<ObjekPersegiPanjang>>()
    private val status = MutableLiveData<ApiStatus>()
    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(ObjekPersegiPanjangApiService.service.getResult())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                status.postValue(ApiStatus.FAILED)
            }
        }
    }
    fun getData(): LiveData<List<ObjekPersegiPanjang>> = data

    fun getStatus(): LiveData<ApiStatus> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}