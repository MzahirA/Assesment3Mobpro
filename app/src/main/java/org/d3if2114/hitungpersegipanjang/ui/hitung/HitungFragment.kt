package org.d3if2114.hitungpersegipanjang.ui.hitung

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if2114.hitungpersegipanjang.R
import org.d3if2114.hitungpersegipanjang.databinding.FragmentHitungBinding
import org.d3if2114.hitungpersegipanjang.db.PersegiPanjangDb
import org.d3if2114.hitungpersegipanjang.model.HasilHitung
import org.d3if2114.hitungpersegipanjang.ui.HitungViewModel


class HitungFragment : Fragment() {

    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = PersegiPanjangDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { hitungPersegiPanjang() }
        viewModel.getHasilHitung().observe(requireActivity(), { showResult(it) })
        binding.shareButton.setOnClickListener { shareData() }
    }

    private fun shareData() {
        val message = getString(R.string.bagikan_template,
            binding.panjangInp.text,
            binding.lebarInp.text,
            binding.hasil1TextView.text,
            binding.hasil2TextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                R.id.action_hitungFragment_to_historiFragment
                )
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment
                )
                return true
            }
            R.id.menu_objek_persegi_panjang -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_objekPersegiPanjangFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("StringFormatMatches")
    private fun hitungPersegiPanjang() {
        val panjang = binding.panjangInp.text.toString()
        if (TextUtils.isEmpty(panjang)) {
            Toast.makeText(context, R.string.panjang_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val lebar = binding.lebarInp.text.toString()
        if (TextUtils.isEmpty(lebar)) {
            Toast.makeText(context, R.string.lebar_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungPersegiPanjang(
            panjang.toFloat(),
            lebar.toFloat(),
        )
    }

    @SuppressLint("StringFormatMatches")
    private fun showResult(result: HasilHitung?) {
        if (result == null) return
        binding.hasil1TextView.text = getString(R.string.hasil_luas, result.hasilLuas)
        binding.hasil2TextView.text = getString(R.string.hasil_keliling, result.hasilKeliling)
        binding.shareButton.visibility = View.VISIBLE
    }
}