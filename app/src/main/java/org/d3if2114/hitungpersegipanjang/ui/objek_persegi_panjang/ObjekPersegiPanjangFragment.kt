package org.d3if2114.hitungpersegipanjang.ui.objek_persegi_panjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if2114.hitungpersegipanjang.databinding.FragmentObjekPersegiPanjangBinding
import org.d3if2114.hitungpersegipanjang.network.ApiStatus

class ObjekPersegiPanjangFragment: Fragment() {

    private val viewModel: ObjekPersegiPanjangViewModel by lazy {
        ViewModelProvider(this).get(ObjekPersegiPanjangViewModel::class.java)
    }
    private lateinit var binding: FragmentObjekPersegiPanjangBinding
    private lateinit var myAdapter: ObjekPersegiPanjangAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentObjekPersegiPanjangBinding.inflate(layoutInflater, container, false)
        myAdapter = ObjekPersegiPanjangAdapter()
        with(binding.recyclerView) {
            addItemDecoration(
                DividerItemDecoration(context,
                    RecyclerView.VERTICAL)
            )
            adapter = myAdapter
            setHasFixedSize(true)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, {
            myAdapter.updateData(it)
        })
        viewModel.getStatus().observe(viewLifecycleOwner, {
            updateProgress(it)
        })
        viewModel.scheduleUpdater(requireActivity().application)
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }ApiStatus.SUCCESS -> {
            binding.progressBar.visibility = View.GONE
        }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}