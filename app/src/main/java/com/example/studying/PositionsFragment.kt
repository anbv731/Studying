package com.example.studying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studying.databinding.FragmentPositionsBinding
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class PositionsFragment : Fragment() {
    lateinit var progressBar: ProgressBar
    lateinit var binding: FragmentPositionsBinding
    lateinit var recyclerView: RecyclerView
    lateinit var db: DataBase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPositionsBinding.inflate(inflater, container, false)
        progressBar = binding.progressBar

        recyclerView = binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Room.databaseBuilder(
            requireContext(),
            DataBase::class.java, "positions.db"
        ).build()
        if (savedInstanceState == null) {
            val listPositions = mutableListOf<PositionEntity>(
                PositionEntity(1, "Dir"),
                PositionEntity(2, "Buh"), PositionEntity(3, "Rab")
            )



            db.positionStaffDao().insertAllPositionRx(listPositions)
                .andThen { println(Thread.currentThread().name + " Thread for Insert") }
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
        db.positionStaffDao().getAllPosRx()
            .doAfterSuccess { println(Thread.currentThread().name + " Thread for getAll") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { println(Thread.currentThread().name + " Thread for Ui") }
            .subscribe { emmiter ->
                progressBar.visibility = View.GONE
                setList(emmiter)
            }
    }

    private fun setList(list: List<PositionEntity>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerAdapterPositions(list)
    }
}