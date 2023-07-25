package com.example.pruebags.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebags.R
import com.example.pruebags.databinding.FragmentCharacterDetailBinding
import com.example.pruebags.utils.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import com.example.pruebags.data.models.Character

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(), OnClickButtons {

    companion object {
        fun newInstance() = CharacterDetailFragment()
    }

    private var _binding : FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel : CharacterDetailViewModel by viewModels()
    private lateinit var adapter : ItemAdapter
    private lateinit var mCharacter: List<Character>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ItemAdapter(requireContext(), this)
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
        viewModel.getAll()
        initViews()
        initOBservers()

        binding.svCharacter.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (!p0.isNullOrEmpty()){
                    adapter.filter.filter(p0)
                }else{
                    adapter.addCharacters(mCharacter)
                }

                return true
            }

        })
    }

    fun initViews(){
        binding.rvCharacter.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacter.itemAnimator = DefaultItemAnimator()
        binding.rvCharacter.adapter = adapter
    }

    fun initOBservers(){
        viewModel.res.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    binding.tvInfoError.visibility = View.GONE
                    binding.rvCharacter.visibility = View.VISIBLE
                    binding.svCharacter.visibility = View.VISIBLE
                    if (it.data != null){
                        adapter.addCharacters(it.data)
                        mCharacter = it.data
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.tvInfoError.visibility = View.VISIBLE
                    binding.rvCharacter.visibility = View.GONE
                    binding.svCharacter.visibility = View.GONE
                }
                Resource.Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.tvInfoError.visibility = View.GONE
                    binding.rvCharacter.visibility = View.GONE
                    binding.svCharacter.visibility = View.GONE
                }
            }
        }
    }

    override fun onClickCharacter(item: Character) {
        val bottomsheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(requireContext()).
        inflate(R.layout.layout_bottomsheet_info_character, requireActivity().findViewById<LinearLayout>(R.id.bottomsheetInfo))

        bottomSheetView.findViewById<TextView>(R.id.name_character).text = item.name
        bottomSheetView.findViewById<TextView>(R.id.status_character).text = item.status
        bottomSheetView.findViewById<TextView>(R.id.gender_character).text = item.gender
        bottomSheetView.findViewById<TextView>(R.id.species_character).text = item.species
        bottomSheetView.findViewById<TextView>(R.id.origin_character).text = item.origin.name
        bottomSheetView.findViewById<TextView>(R.id.location_character).text = item.location.name

        bottomsheetDialog.setContentView(bottomSheetView)
        bottomsheetDialog.show()
    }
}