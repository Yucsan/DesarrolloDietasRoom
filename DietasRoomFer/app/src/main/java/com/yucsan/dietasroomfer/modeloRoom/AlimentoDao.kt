package com.yucsan.dietasroomfer.modeloRoom

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

    // *** INGREDIENTES

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarIngrediente(ingrediente: Ingrediente)

    @Query("SELECT * FROM ingredientes")
    fun getTodosLosIngredientes(): Flow<List<Ingrediente>>

    @Query("SELECT * FROM ingredientes WHERE alimentoId = :alimentoId")
    suspend fun obtenerIngredientePorAlimentoId(alimentoId: String): Ingrediente?

    @Query("SELECT * FROM alimentos WHERE id = :alimentoId")
    fun obtenerAlimentoConIngredientes(alimentoId: String): AlimentoConIngredientes

    // *** COMPONENTE DIETA
    // Insertar un ComponenteDieta
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarComponente(componente: ComponenteDietaEntity)

    // Insertar relación Componente - Ingrediente
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarComponenteIngredienteCrossRef(relacion: ComponenteIngredienteCrossRef)

    // Obtener todos los Componentes con sus Ingredientes
    @Transaction
    @Query("SELECT * FROM componente_dieta")
    fun obtenerComponentesConIngredientes(): Flow<List<ComponenteConIngredientes>>

    // Eliminar un ComponenteDieta
    @Query("DELETE FROM componente_dieta WHERE id = :componenteId")
    suspend fun eliminarComponente(componenteId: String)


}


