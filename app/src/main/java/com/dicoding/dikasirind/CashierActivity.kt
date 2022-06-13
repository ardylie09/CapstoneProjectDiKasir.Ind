package com.dicoding.dikasirind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.dikasirind.databinding.ActivityCashierBinding
import com.dicoding.dikasirind.model.UserData
import com.dicoding.dikasirind.retrofit.ApiConfig
import com.dicoding.dikasirind.view.PostAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CashierActivity : AppCompatActivity() {
    private val list =ArrayList<UserData>()
    lateinit var binding: ActivityCashierBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMenu()

        binding.btnAdd.setOnClickListener {
            postMenu()
        }
        binding.btnDel.setOnClickListener {
            deleteMenu()
        }
    }

    private fun deleteMenu() {
        ApiConfig.instance.deletemenu("").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("response", response.code().toString())
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("response", t.message.toString())
            }
        })
        getMenu()
    }

    private fun postMenu() {
        val nama: TextView = findViewById(R.id.namaBrg)
        val stock: TextView = findViewById(R.id.jumlahBrg)

        ApiConfig.instance.postmenu(
            "Telur", "10")
            .enqueue(object : Callback<UserData> {
                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Log.d("response", t.message.toString())
                }
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    val responseText = "Response Code ${response.code()}\n"+"Nama: ${response.body()?.nama},"+"Sisa Stock: ${response.body()?.stock}"
                    Log.d("response", responseText)
                    nama.text = "Nama ${response.body()?.nama}"
                    stock.text = "Sisa Stock: ${response.body()?.stock}"
                }
            })
    }

    private fun getMenu(){
        val rvTv: RecyclerView = findViewById(R.id.rv_Show)
        rvTv.setHasFixedSize(true)
        rvTv.layoutManager = LinearLayoutManager(this)
        ApiConfig.instance.getmenu().enqueue(object : Callback<ArrayList<UserData>> {
            override fun onFailure(call: Call<ArrayList<UserData>>, t: Throwable) {
                Log.d("response", t.message.toString())
            }
            override fun onResponse(
                call: Call<ArrayList<UserData>>,
                response: Response<ArrayList<UserData>>
            ) {
                response.body()?.let { list.addAll(it) }
                val adapter = PostAdapter(list)
                rvTv.adapter = adapter
                Log.d("response", response.body().toString())
            }
        })
    }
}