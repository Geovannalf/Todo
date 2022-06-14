package com.example.todo

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.api.Repository
import com.example.todo.model.Categoria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject
@HiltViewModel

class MainViewModel @Inject constructor(
    private val repository:Repository
    ): ViewModel (){


    private val _myCategoriaResponse =
        MutableLiveData<Response<List<Categoria>>>()

    val myCategoriaResponse: LiveData<Response<List<Categoria>>> =
            _myCategoriaResponse

    val dataSelecionada = MutableLiveData<LocalDate>()

    init {
        //listCategoria()
    }

    fun listCategoria (){
        viewModelScope.launch(){
           try {

               val response = repository.listCategoria()
               _myCategoriaResponse.value = response

           }catch (e: Exception){
               Log.d("Erro", e.message.toString())
           }
        }
    }

}