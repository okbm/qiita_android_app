package ml.okbm.qiita_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ml.okbm.qiita_app.models.*
import ml.okbm.qiita_app.Interfaces.createService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    // interfaceという名前微妙だがサンプルなので我慢
    private val itemInterface by lazy { createService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchItems()
    }

    private fun fetchItems() {

        itemInterface.items().enqueue(object : Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")
            }

            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("fetchItems", "response success")
                        var items = mutableListOf<String>()

                        for (item in it) {
                            items.add(item.title)
                        }

                        val adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, items)
                        val list: ListView = findViewById(R.id.list_item);
                        list.adapter  = adapter
                    }
                }
                Log.d("fetchItems", "response code:" + response.code())
                Log.d("fetchItems", "response errorBody:" + response.errorBody())
           }
        })
    }
}
