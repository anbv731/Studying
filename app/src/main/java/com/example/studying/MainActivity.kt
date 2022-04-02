package com.example.studying

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.studying.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.zip.Inflater


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var button1: Button
    lateinit var button2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        button1 = binding.button1
        button2 = binding.button2
        button1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PositionsFragment(), null)
                .commit()
        }
        button2.setOnClickListener {

            binding.fragmentContainer.visibility= View.VISIBLE
            button1.visibility=View.GONE
            button2.visibility=View.GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PositionsFragment(), null)
                .commit()
        }





    }

}