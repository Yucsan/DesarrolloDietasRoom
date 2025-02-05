package com.yucsan.dietasroomfer.modeloRoom

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
/*
enum class TipoComponente {
    SIMPLE,PROCESADO,MENU,RECETA,DIETA
}


@Entity(tableName = "ComponenteDieta")
data class ComponenteDieta(
    @PrimaryKey val id: String= java.util.UUID.randomUUID().toString(),
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "tipoComponente") val TipoComponente = TipoComponente.SIMPLE,
    @ColumnInfo(name = "gr_prot") val grProt: Double = 0.0,
    @ColumnInfo(name = "gr_hc") val grHC: Double = 0.0,
    @ColumnInfo(name = "gr_lip") val grLip: Double = 0.0,
    @ColumnInfo(name = "cantidad") val cantidad: Double = 100.0

) {

    @Ignore
    val grHC: Double
        get() = if(tipo.esSimpleOProcesado()) grHC_ini else calculoGenerado { it.cd.grHC }

    val grLip: Double
        get() = if (tipo.esSimpleOProcesado()) grLip_ini else calculoGenerado { it.cd.grLip }

    val grPro: Double
        get() = if (tipo.esSimpleOProcesado()) grPro_ini else calculoGenerado { it.cd.grPro }

    val cantidadTotal: Double
        get() =if(tipo.esSimpleOProcesado())  100.0 else calculoGenerado {  it.cd.cantidadTotal }

    fun calculoGenerado(selector: (Ingrediente) -> Double): Double {
        return try {
            if (ingredientes.isEmpty()) {
                Log.i("MUESTRAME", "No hay ingredientes, devuelve 0.0")
                0.0
            } else {
                val suma = ingredientes.sumOf { ingrediente ->
                    val cantidad = ingrediente.cantidad
                    if (cantidad <= 0.0) {
                        Log.i("MUESTRAME", "Ingrediente con cantidad inválida: ${ingrediente.cd.nombre}, cantidad=$cantidad")
                        0.0
                    } else {
                        selector(ingrediente) * cantidad / 100
                    }
                }
                suma
            }
        } catch (e: Exception) {
            Log.i("MUESTRAME", "Error durante el cálculo: ${e.message}")
            0.0
        }
    }

    val Kcal: Double
        get() = (4 * grHC) + (4 * grPro) + (9 * grLip)

    var ingredientes: MutableList<Ingrediente> = mutableListOf()

@Entity(
    tableName = "ingredientes",
    foreignKeys = [
        ForeignKey(
            entity = ComponenteDieta::class,
            parentColumns = ["id"],
            childColumns = ["alimentoId"],
            onDelete = ForeignKey.CASCADE // Si se elimina el alimento, también se eliminan los ingredientes
        )
    ],
    indices = [Index(value = ["alimentoId"])] // Mejora la búsqueda por clave foránea
)
data class Ingrediente(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    @ColumnInfo(name = "alimentoId") val alimentoId: String, // Clave foránea que hace referencia a Alimento
    @ColumnInfo(name = "cantidad") val cantidad: Float = 8f
)

data class AlimentoConIngredientes(
    @Embedded val alimento: ComponenteDieta,
    @Relation(
        parentColumn = "id",
        entityColumn = "alimentoId"
    )
    val ingredientes: List<Ingrediente>
)


*/








