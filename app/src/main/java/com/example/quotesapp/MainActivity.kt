package com.example.quotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.quotesapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var quotesObj : Quotes
    var quotesList = mutableListOf<Quotes>()
    lateinit var mainViewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        getQuotes()
        binding.nextBtn.setOnClickListener {
            mainViewModel.increment()
            setText()
        }
        binding.backBtn.setOnClickListener {
            if(mainViewModel.count==0){
                Toast.makeText(this@MainActivity,"First Quotes",Toast.LENGTH_SHORT).show()
            }else{
                mainViewModel.decrement()
                setText()
            }
        }
        binding.shareBtn.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${quotesList[mainViewModel.count].text} \nby ${quotesList[mainViewModel.count].author}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        Log.i("Size","${quotesList.size}")
    }
    fun getQuotes(){
        var quotes = QuotesService.quotesInstance.getQuotes()
        quotes.enqueue(object : Callback<List<Quotes>>{
            override fun onResponse(call: Call<List<Quotes>>, response: Response<List<Quotes>>) {
                Log.i("SUCCESS","Workinggggggg!!!!")
                val quotes = response.body()
                if(quotes!=null){
                    quotesList.addAll(quotes)
                    Log.i("SIZEF","${quotesList.size}")
                    if(quotesList.size != 0){
                        setText()
                        quotesObj = Quotes(quotesList[mainViewModel.count].text,quotesList[mainViewModel.count].author)
                        binding.quotes = quotesObj
                    }else{
                        Log.i("QUOTESLISTS","EMPTYYYYYY")
                    }
                }
            }

            override fun onFailure(call: Call<List<Quotes>>, t: Throwable) {
                Log.e("ERROR","Error occurred while fetching the data-${t}",t)
            }

        })
    }

    private fun setText() {
        binding.quoteTxt.text = quotesList[mainViewModel.count].text
        binding.authorTxt.text = quotesList[mainViewModel.count].author
    }
}