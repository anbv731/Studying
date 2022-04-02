package com.example.studying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studying.databinding.FragmentBinding
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class PositionsFragment:Fragment() {
    lateinit var binding:FragmentBinding
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {binding= FragmentBinding.inflate(inflater,container,false)
        recyclerView=binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listPositions = mutableListOf<PositionEntity>(
            PositionEntity(1, "Dir"),
            PositionEntity(2, "Buh"), PositionEntity(3, "Rab")
        )

        val db = Room.databaseBuilder(
            requireContext(),
            DataBase::class.java, "positions.db"
        ).build()

        db.positionDao().insertAllPositionRx(listPositions).subscribeOn(Schedulers.newThread())
            .subscribe()
        db.positionDao().getAllRx().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { emmiter -> setList(emmiter) }
    }
    private fun setList(list: List<PositionEntity>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerAdapter(list)
    }
}