package com.yucsan.dietasroomfer.modeloRoom

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class AlimentoRepository(private val dao: ComponenteDietaDao) {

   fun getAlimentos(): Flow<List<ComponenteDieta>> = dao.getComponenteDietas().flowOn(Dispatchers.IO)

   fun getAlimentoByName(nombre: String): Flow<ComponenteDieta> = dao.getComponenteDietaByName(nombre).flowOn(Dispatchers.IO)

   fun getAlimentoById(id: String): Flow<ComponenteDieta?> = dao.getComponenteDietaById(id).flowOn(Dispatchers.IO)

   suspend fun insertar(alimento: ComponenteDieta) = dao.insertarComponenteDieta(alimento)
   suspend fun borrar(alimento: ComponenteDieta) = dao.borrarComponenteDieta(alimento)


   // metodos INGREDIENTES

   fun getIngredientes(): Flow<List<Ingrediente>> = dao.getTodosLosIngredientes().flowOn(Dispatchers.IO)

   //--------------------------------------------------------------------------------
   suspend fun insertarIngrediente(ingrediente: Ingrediente) {

      val existente = dao.obtenerIngredientePorComponenteDietaId(ingrediente.componenteId)
      if (existente == null) {
         dao.insertarIngrediente(ingrediente)
         println("Ingrediente agregado correctamente.")
      } else {
         //println("❌ El alimento ya está registrado como ingrediente.")
         // Si ya existe, actualizamos la cantidad
         val nuevoIngrediente = existente.copy(cantidad = existente.cantidad + ingrediente.cantidad)
         dao.insertarIngrediente(nuevoIngrediente)  // REEMPLAZA el registro
         println("Cantidad de ingrediente actualizada.")


      }
   }
   //---------------------------------------------------


   suspend fun getIngredientesPorComponenteDietaId(componenteId: String): List<Ingrediente> {
         return dao.getIngredientesPorComponenteDietaId(componenteId)
   }

   //BORRAR UN INGREDIENTE DENTRO DE UN COMPONENTE DIETA
   suspend fun borrarIngrediente(ingredienteId: String) {  dao.borrarIngredientePorId(ingredienteId) }


   //------------------- BORRADO --------------------------

   suspend fun borrarTodaLaInformacion() {
      dao.borrarTodosLosIngredientes()
      dao.borrarTodosLosComponenteDietas()
   }

}

