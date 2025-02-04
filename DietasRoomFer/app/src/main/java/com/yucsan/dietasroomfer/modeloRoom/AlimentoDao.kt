package com.example.pruebaroom2025.modelo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlimentoDao {
    // Esta estrategia ahorra la necesidad de implementar un método @Update,
    // ya que reemplaza registros existentes si hay conflicto en la clave primaria
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAlimento(alimento: Alimento)

    @Delete
    suspend fun borrarAlimento(alimento: Alimento)

    @Query("SELECT * FROM alimentos")
    fun getAlimentos(): Flow<List<Alimento>>

    @Query("SELECT * FROM alimentos WHERE nombre = :nombre")
    fun getAlimentoByName(nombre: String): Flow<Alimento>

    @Query("SELECT * FROM alimentos WHERE id = :id")
    fun getAlimentoById(id: String): Flow<Alimento?>

    // esto es Nuevo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarIngrediente(ingrediente: Ingrediente)

    @Query("SELECT * FROM ingredientes")
    fun getTodosLosIngredientes(): Flow<List<Ingrediente>>

    @Query("SELECT * FROM ingredientes WHERE alimentoId = :alimentoId")
    suspend fun obtenerIngredientePorAlimentoId(alimentoId: String): Ingrediente?

    @Query("SELECT * FROM alimentos WHERE id = :alimentoId")
    fun obtenerAlimentoConIngredientes(alimentoId: String): AlimentoConIngredientes

}


