package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.adapter.TarefaAdapter
import com.example.todo.adapter.TaskClickListener
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.model.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment(), TaskClickListener {

    private lateinit var binding: FragmentListBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater,container,false)

        mainViewModel.listTarefa()

        //Configuração RecyclerView
        val adapter = TarefaAdapter(this,mainViewModel,requireContext())
        binding.recyclerTarefa.layoutManager = LinearLayoutManager(context)
        binding.recyclerTarefa.adapter = adapter
        binding.recyclerTarefa.setHasFixedSize(true)


        binding.floatingAdd.setOnClickListener(){
            mainViewModel.tarefaSeleciona= null
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }

        mainViewModel.myTarefaResponse.observe(viewLifecycleOwner){
            response -> if(response != null){
                adapter.setList(response.body()!!)
        }
        }

        return binding.root
    }

    override fun ontaskClickListener(tarefa: Tarefa) {
        mainViewModel.tarefaSeleciona = tarefa
        findNavController().navigate(R.id.action_listFragment_to_formFragment)
    }
}


