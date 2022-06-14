package com.example.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.FragmentFormBinding
import com.example.todo.fragment.DatePickerFragment
import com.example.todo.fragment.TimerPickerListenner
import com.example.todo.model.Categoria
import java.time.LocalDate

class FormFragment : Fragment(),TimerPickerListenner {
    private lateinit var binding: FragmentFormBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(layoutInflater,container,false)

        mainViewModel.listCategoria()
        mainViewModel.dataSelecionada.value = LocalDate.now()

        mainViewModel.myCategoriaResponse.observe(viewLifecycleOwner){
            Response -> Log.d("Requisicao", Response.body().toString())
            spinnerCategoria(Response.body())
        }

        mainViewModel.dataSelecionada.observe(viewLifecycleOwner){
            selectDate -> binding.editData.setText(selectDate.toString())
        }

        binding.buttonSalvar.setOnClickListener(){
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }

        binding.editData.setOnClickListener(){
            DatePickerFragment(this)
                .show(parentFragmentManager,"DatePicker")
        }

        return binding.root
    }
    fun spinnerCategoria(listCategoria: List<Categoria>?){
        if(listCategoria != null){
            binding.spinnerCategoria.adapter=
                ArrayAdapter(
                    requireContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    listCategoria
                )

        }

    }

    override fun onDataSelect(date: LocalDate) {
        mainViewModel.dataSelecionada.value = date
    }
}

