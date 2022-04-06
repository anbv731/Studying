package com.example.studying

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studying.databinding.FragmentStaffBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class StaffFragment : Fragment() {
    companion object {
        const val SCROLL = "SCROLL"
    }

    lateinit var binding: FragmentStaffBinding
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var db: DataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffBinding.inflate(layoutInflater, container, false)
        recyclerView = binding.recyclerView
        progressBar = binding.progressBar
        db = Room.databaseBuilder(
            requireContext(),
            DataBase::class.java, "staff.db"
        ).build()
        if (savedInstanceState == null) {
            val listStaff = mutableListOf(
                StaffEntity(1, "John", "32", "3"),
                StaffEntity(2, "Karl", "25", "2"),
                StaffEntity(3, "Luk", "25", "2"),
                StaffEntity(4, "Sir", "32", "3"),
                StaffEntity(5, "Clark", "25", "2"),
                StaffEntity(6, "Edmond", "32", "3"),
                StaffEntity(7, "Joe", "25", "2"),
                StaffEntity(8, "Peter", "32", "3"),
                StaffEntity(9, "Karl", "32", "3"),
                StaffEntity(10, "Luk", "25", "2"),
                StaffEntity(11, "Sir", "32", "3"),
                StaffEntity(12, "Clark", "32", "3"),
                StaffEntity(13, "Edmond", "44", "15"),
                StaffEntity(14, "Joe", "44", "15"),
                StaffEntity(15, "Peter", "44", "15")
            )

            db.positionStaffDao().insertAllStaffRx(listStaff)
                .andThen { println(Thread.currentThread().name + " Thread for Insert") }
                .subscribeOn(Schedulers.io())
                .subscribe()

        }
        db.positionStaffDao().getAllStaffRx()
            .doAfterSuccess { println(Thread.currentThread().name + " Thread for getAll") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { println(Thread.currentThread().name + " Thread for Ui") }
            .subscribe { emmiter ->
                progressBar.visibility = View.GONE
                setList(emmiter)
                if (savedInstanceState != null) {
                    recyclerView.layoutManager?.onRestoreInstanceState(
                        savedInstanceState.getParcelable(SCROLL)!!
                    )
                }
            }

        return binding.root
    }

    private fun setList(list: List<StaffEntity>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerAdapterStaff(list)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SCROLL, recyclerView.layoutManager!!.onSaveInstanceState()!!)
        super.onSaveInstanceState(outState)
    }

}