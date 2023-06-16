package org.d3if2114.hitungpersegipanjang.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if2114.hitungpersegipanjang.model.ObjekPersegiPanjang
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/AlHadidDefryRinandi/assesment1_6706202099/master/"
private const val BASE_IMG_URL = "https://raw.githubusercontent.com/AlHadidDefryRinandi/assesment1_6706202099/master/img/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface ObjekPersegiPanjangApi {
    @GET("objek-persegi.json")
    suspend fun getResult(): List<ObjekPersegiPanjang>
}
object ObjekPersegiPanjangApiService {
    val service: ObjekPersegiPanjangApi by lazy {
        retrofit.create(ObjekPersegiPanjangApi::class.java)
    }
    fun getObjekPersegiPanjangUrl(gambar: String): String {
        return "$BASE_IMG_URL$gambar"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }