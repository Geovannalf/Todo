package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.adapter.TarefaAdapter
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.model.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater,container,false)

        val listTarefas = listOf(
            Tarefa(
                "Lavar a louça",
                "Lavar a louça o dia todo",
                "Geovanna",
                "2022-06-11",
                false,
                "Dia a dia"
            ),Tarefa(
                "Entregar Atividade",
                "Entregar atividade na plataforma",
                "Geovanna",
                "2022-06-07",
                true,
                "Estudos"
            ),Tarefa(
                "Estudar Inglês",
                "Acessar a aula",
                "Geovanna",
                "2022-06-10",
                false,
                "Estudos"
            )

        )

        //Configuração RecyclerView
        val adapter = TarefaAdapter()
        binding.recyclerTarefa.layoutManager = LinearLayoutManager(context)
        binding.recyclerTarefa.adapter = adapter
        binding.recyclerTarefa.setHasFixedSize(true)

        adapter.setList(listTarefas)

        binding.floatingAdd.setOnClickListener(){
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }

        return binding.root
    }
}


