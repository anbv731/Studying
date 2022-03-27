package com.example.studying

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.SearchBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.search) {
    lateinit var binding: SearchBinding
    lateinit var searchView: SearchView
    lateinit var textView: TextView
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        searchView = binding.searchView
        textView = binding.textView
        recyclerView = binding.recyclerView
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //setList(parseJson(getJson(requireContext(), "irlix.json")))


        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(text: String?): Boolean {
                    subscriber.onNext(text)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query)
                    return false
                }


            })
        }).subscribeOn(Schedulers.io())
            .map { text -> text.lowercase(Locale.getDefault()).trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                run {
                    if (text.isEmpty()) {
                        textView.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        println(Thread.currentThread().name)
                    } else {
                        textView.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        //setList(parseJson(getJson(requireContext(), "irlix.json")))
                        getList()
                            .subscribe({ emitter -> setList(emitter.filter { text in it.title })
                            println(Thread.currentThread().name + " Thread for setList")})

                        println(Thread.currentThread().name + " Thread")
                    }

                }

            }

    }

    private fun getList(): Single<List<Event>> {
        return Single.create<List<Event>> { emitter ->
            val list = parseJson(getJson(requireContext(), "irlix.json"))
            emitter.onSuccess(list)
        }
    }

    private fun setList(list: List<Event>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerAdapter(list)
    }
}

fun getJson(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun parseJson(json: String?): List<Event> {

    val gson = Gson()
    val listEventType = object : TypeToken<List<Event>>() {}.type
    return gson.fromJson(json, listEventType)
}