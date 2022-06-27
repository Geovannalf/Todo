package com.example.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.FragmentFormBinding
import com.example.todo.fragment.DatePickerFragment
import com.example.todo.fragment.TimerPickerListenner
import com.example.todo.model.Categoria
import com.example.todo.model.Tarefa
import java.time.LocalDate

class FormFragment : Fragment(),TimerPickerListenner {
    private lateinit var binding: FragmentFormBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private var categoriaSelecionada = 0L
    private var tarefaSelecionada: Tarefa? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(layoutInflater,container,false)

        carregarDados()

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
            inserirNoBanco()
        }

        binding.editData.setOnClickListener(){
            DatePickerFragment(this)
                .show(parentFragmentManager,"DatePicker")
        }

        return binding.root
    }
    private fun spinnerCategoria(listCategoria: List<Categoria>?) {
        if(listCategoria != null) {
            binding.spinnerCategoria.adapter=
                ArrayAdapter(
                    requireContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    listCategoria
                )

            binding.spinnerCategoria.onItemSelectedListener=
                object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>,p1: View,p2: Int,p3: Long){
                        val selected = binding.spinnerCategoria.selectedItem as Categoria

                        categoriaSelecionada = selected.id
                    }


                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

        }

    }

    private fun validarCampos(
        nome: String,descricao: String,responsavel: String,
    ): Boolean{
        return !(
                (nome == "" || nome.length < 3 || nome.length > 20) ||
                (descricao == "" || descricao.length < 5 || descricao.length > 200) ||
                (responsavel == "" || responsavel.length < 3 || responsavel.length > 20)
                )
    }

    private fun inserirNoBanco() {

        val nome = binding.editNome.text.toString()
        val desc = binding.editDescricao.text.toString()
        val resp = binding.editResponsavel.text.toString()
        val data = binding.editData.text.toString()
        val status = binding.switchAtivoCard.isChecked
        val categoria = Categoria(categoriaSelecionada, null, null)
        Log.d("categoria", categoria.id.toString())

        if (validarCampos(nome, desc, resp)) {

            var salvar: String

            if(tarefaSelecionada!= null){
                salvar = "Tarefa atualizada!"
                val tarefa = Tarefa(tarefaSelecionada?.id!!, nome, desc, resp, data, status, categoria)
                mainViewModel.addTarefa(tarefa)
            }else{
                salvar = "Tarefa criada!"
                val tarefa = Tarefa(0, nome, desc, resp, data, status, categoria)
                mainViewModel.addTarefa(tarefa)
            }

            Toast.makeText(context, "Tarefa Salva", Toast.LENGTH_SHORT).show()
            val tarefa = Tarefa(0, nome, desc, resp, data, status, categoria)
            mainViewModel.addTarefa(tarefa)
            Toast.makeText(context, "Tarefa Criada!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_formFragment_to_listFragment)

        } else {
            Toast.makeText(context, "Verifique os campos!", Toast.LENGTH_SHORT).show()
        }

    }
    private fun carregarDados(){
        tarefaSelecionada = mainViewModel.tarefaSeleciona
        if (tarefaSelecionada != null){
            binding.editNome.setText(tarefaSelecionada?.nome)
            binding.editDescricao.setText(tarefaSelecionada?.descricao)
            binding.editResponsavel.setText(tarefaSelecionada?.responsavel)
            binding.editData.setText(tarefaSelecionada?.data)
            binding.switchAtivoCard.isChecked= tarefaSelecionada?.status!!


        }
    }


    override fun onDataSelect(date: LocalDate) {
        mainViewModel.dataSelecionada.value = date
    }
}

