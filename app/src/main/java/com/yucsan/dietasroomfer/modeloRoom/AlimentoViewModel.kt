package com.yucsan.dietasroomfer.modeloRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CDModelView(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.obtenerInstancia(application).alimentoDao()
    private val repository = AlimentoRepository(dao)


    //---- listas Ingredientes
    private var _componentes = MutableStateFlow<List<ComponenteDieta>>(emptyList())
    val componentes  = _componentes.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAlimentos().collect { item ->
                if (item.isNullOrEmpty()){
                    _componentes.value = emptyList()
                }else{
                    _componentes.value = item
                }
            }
        }
    }
/*
    fun obtenerIngredientes(componenteId: String) = flow<List<Ingrediente>> {
        val ingredientes = repository.getIngredientesPorComponenteDietaId(componenteId)
        emit(ingredientes)
    }.flowOn(Dispatchers.IO)*/

    fun obtenerIngredientes(componenteId: String): Flow<List<Ingrediente>> = flow {
        emit(repository.getIngredientesPorComponenteDietaId(componenteId))
    }.flowOn(Dispatchers.IO)


    fun insertarAlimento(alimento: ComponenteDieta) = viewModelScope.launch {
        repository.insertar(alimento)
    }

    fun borrarAlimento(alimento: ComponenteDieta) = viewModelScope.launch {
        repository.borrar(alimento)
    }

    fun insertarIngrediente(ingrediente: Ingrediente) = viewModelScope.launch {
        repository.insertarIngrediente(ingrediente)
    }

    fun borrarIngrediente(ingredienteId: String) = viewModelScope.launch {
        repository.borrarIngrediente(ingredienteId)
    }


}