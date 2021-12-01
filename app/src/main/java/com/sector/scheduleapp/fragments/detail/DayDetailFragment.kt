package com.sector.scheduleapp.fragments.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.sector.scheduleapp.databinding.FragmentDayDetailBinding
import com.sector.scheduleapp.objects.Day
import kotlin.collections.ArrayList

class DayDetailFragment : Fragment() {
    private var _binding: FragmentDayDetailBinding? = null
    private val binding get() = _binding!!

    private var ref: DatabaseReference? = null
    private lateinit var adapter: DayDetailAdapter

    private val args by navArgs<DayDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayDetailBinding.inflate(inflater, container, false)

        setupRecyclerView()
        getArgs()
        initDatabase()
        setDate()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readFromDatabase()
        showHomeFragment()
    }

    private fun setDate() {
        binding.apply {
            tvDate.text = getArgs()
        }
    }

    private fun showHomeFragment() {
        binding.apply {
            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = DayDetailAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun getArgs(): String {
        return args.currentDay!!.day
    }

    private fun loadSettings(): Triple<String, String?, String?> {
        val prefs = requireActivity().getSharedPreferences("my_settings", Context.MODE_PRIVATE)

        val institute = "ИМИТ"
        val group = prefs.getString("group", "Группа")
        val mode = prefs.getString("mode", "Мод")

        return Triple(institute, group, mode)
    }

    private fun initDatabase() {
        FirebaseApp.initializeApp(requireContext())

        val (institute, group, mode) = loadSettings()
        val dayOfWeek = getArgs()

        ref = FirebaseDatabase.getInstance()
            .getReference(institute)
            .child(group!!)
            .child(mode!!)
            .child(dayOfWeek)
    }

    private fun readFromDatabase() {
        ref?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val list = ArrayList<Day>()

                    for (daySnapshot in snapshot.children) {
                        val day = daySnapshot.getValue(Day::class.java)

                        list.add(day!!)
                    }

                    adapter.submitList(list)

                    binding.apply {
                        shimmer.stopShimmer()
                        shimmer.visibility = View.GONE
                    }

                } else {
                    binding.apply {
                        shimmer.stopShimmer()
                        shimmer.visibility = View.GONE

                        lrDbEmpty.visibility = View.VISIBLE
                        recyclerView.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ErrorFirebase", error.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            shimmer.startShimmer()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.apply {
            shimmer.startShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}