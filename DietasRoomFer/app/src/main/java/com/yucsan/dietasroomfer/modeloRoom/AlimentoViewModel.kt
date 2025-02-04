package com.example.pruebaroom2025.modelo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlimentoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.obtenerInstancia(application).alimentoDao()
    private val repository = AlimentoRepository(dao)
    private val _alimentos = MutableStateFlow<List<Alimento>>(emptyList())
    val alimentos = _alimentos.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAlimentos().collect { item ->
                if (item.isNullOrEmpty()){
                    _alimentos.value = emptyList()
                }else{
                    _alimentos.value = item
                }
            }
        }
    }
        fun insertarAlimento(alimento: Alimento) = viewModelScope.launch {
            repository.insertar(alimento)
        }

        fun borrarAlimento(alimento: Alimento) = viewModelScope.launch {
            repository.borrar(alimento)
        }


 }

/*

    private val _cronosList = MutableStateFlow<List<Cronos>>(emptyList())
    val cronosList = _cronosList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCronos().collect { item ->
                if (item.isNullOrEmpty()){
                    _cronosList.value = emptyList()
                }else{
                    _cronosList.value = item
                }
            }
        }
    }
 fun addCrono(crono: Cronos) = viewModelScope.launch { repository.addCrono(crono) }
    fun updateCrono(crono: Cronos) = viewModelScope.launch { repository.updateCrono(crono) }
    fun deleteCrono(crono: Cronos) = viewModelScope.launch { repository.deleteCrono(crono) }


 */