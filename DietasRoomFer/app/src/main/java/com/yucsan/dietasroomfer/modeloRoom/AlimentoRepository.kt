package com.yucsan.dietasroomfer.modeloRoom

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class AlimentoRepository(private val dao: AlimentoDao) {

   fun getAlimentos(): Flow<List<Alimento>> = dao.getAlimentos().flowOn(Dispatchers.IO)

   fun getAlimentoByName(nombre: String): Flow<Alimento> =
      dao.getAlimentoByName(nombre).flowOn(Dispatchers.IO)

   fun getAlimentoById(id: String): Flow<Alimento> =
      dao.getAlimentoByName(id).flowOn(Dispatchers.IO)

   suspend fun insertar(alimento: Alimento) = dao.insertarAlimento(alimento)
   suspend fun borrar(alimento: Alimento) = dao.borrarAlimento(alimento)


   // metodos INGREDIENTES

   suspend fun getIngredientes(): Flow<List<Ingrediente>> = dao.getTodosLosIngredientes().flowOn(Dispatchers.IO)

   suspend fun insertarIngrediente(ingrediente: Ingrediente) {

      val existente = dao.obtenerIngredientePorAlimentoId(ingrediente.alimentoId)
      if (existente == null) {
         dao.insertarIngrediente(ingrediente)
         println("Ingrediente agregado correctamente.")
      } else {
         println("❌ El alimento ya está registrado como ingrediente.")
      }
   }


}

