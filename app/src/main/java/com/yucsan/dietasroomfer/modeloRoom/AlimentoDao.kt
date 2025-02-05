package com.yucsan.dietasroomfer.modeloRoom

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ComponenteDietaDao {
    // Esta estrategia ahorra la necesidad de implementar un método @Update,
    // ya que reemplaza registros existentes si hay conflicto en la clave primaria
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarComponenteDieta(alimento: ComponenteDieta)

    @Delete
    suspend fun borrarComponenteDieta(alimento: ComponenteDieta)

    @Query("SELECT * FROM componente_dieta")
    fun getComponenteDietas(): Flow<List<ComponenteDieta>>

    @Query("SELECT * FROM componente_dieta WHERE nombre = :nombre")
    fun getComponenteDietaByName(nombre: String): Flow<ComponenteDieta>

    @Query("SELECT * FROM componente_dieta WHERE id = :id")
    fun getComponenteDietaById(id: String): Flow<ComponenteDieta?>

    // *** INGREDIENTES

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarIngrediente(ingrediente: Ingrediente)

    @Query("DELETE FROM ingrediente WHERE id = :ingredienteId")
    suspend fun borrarIngredientePorId(ingredienteId: String)

    @Query("SELECT * FROM ingrediente")
    fun getTodosLosIngredientes(): Flow<List<Ingrediente>>

    @Query("SELECT * FROM ingrediente WHERE componente_id = :componente_id")
    suspend fun obtenerIngredientePorComponenteDietaId(componente_id: String): Ingrediente?

    // VAMOS A REVIZAR
    /*
    @Query("SELECT * FROM componente_dieta WHERE id = :componente_id")
    fun obtenerComponenteDietaConIngredientes(componente_id: String): ComponenteDietaConIngredientes
    */
    @Transaction
    @Query("SELECT * FROM Ingrediente WHERE componente_id = :componenteId")
    fun obtenerIngredientesPorComponente(componenteId: String): List<Ingrediente>




    //BORRADO PELIGRO

    @Query("DELETE FROM componente_dieta")
    suspend fun borrarTodosLosComponenteDietas()

    @Query("DELETE FROM ingrediente")
    suspend fun borrarTodosLosIngredientes()


    @Query("SELECT * FROM Ingrediente WHERE componente_id = :componenteId")
    suspend fun getIngredientesPorComponenteDietaId(componenteId: String): List<Ingrediente>


}


