package com.yucsan.dietasroomfer.modeloRoom

import BD_Fichero_Android
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.Serializable

import androidx.room.*
import java.util.UUID


// ENUM para el tipo de componente
enum class TipoComponente {
    SIMPLE, PROCESADO, MENU, RECETA, DIETA
}

// ENTIDAD PRINCIPAL: ComponenteDieta
@Entity(tableName = "componente_dieta")
data class ComponenteDieta(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "tipo_componente") var tipo: TipoComponente = TipoComponente.SIMPLE,
    @ColumnInfo(name = "gr_hc_ini") var grHC_ini: Double = 0.0,
    @ColumnInfo(name = "gr_lip_ini") var grLip_ini: Double = 0.0,
    @ColumnInfo(name = "gr_pro_ini") var grPro_ini: Double = 0.0
) : Serializable {

    // Propiedades calculadas (NO SE GUARDAN EN ROOM)
    @get:Ignore
    val grHC: Double
        get() = if (tipo.esSimpleOProcesado()) grHC_ini else calculoGenerado { it.cd.grHC }

    @get:Ignore
    val grLip: Double
        get() = if (tipo.esSimpleOProcesado()) grLip_ini else calculoGenerado { it.cd.grLip }

    @get:Ignore
    val grPro: Double
        get() = if (tipo.esSimpleOProcesado()) grPro_ini else calculoGenerado { it.cd.grPro }

    @get:Ignore
    val cantidadTotal: Double
        get() = if (tipo.esSimpleOProcesado()) 100.0 else calculoGenerado { it.cd.cantidadTotal }

    @get:Ignore
    val Kcal: Double
        get() = (4 * grHC) + (4 * grPro) + (9 * grLip)

    fun calculoGenerado(selector: (Ingrediente) -> Double): Double {
        return try {
            if (ingredientes.isEmpty()) {
                Log.i("MUESTRAME", "No hay ingredientes, devuelve 0.0")
                0.0
            } else {
                ingredientes.sumOf { ingrediente ->
                    val cantidad = ingrediente.cantidad
                    if (cantidad > 0.0) selector(ingrediente) * cantidad / 100 else 0.0
                }
            }
        } catch (e: Exception) {
            Log.i("MUESTRAME", "Error durante el cálculo: ${e.message}")
            0.0
        }
    }

    fun TipoComponente.esSimpleOProcesado() = this == TipoComponente.SIMPLE

    @Ignore var ingredientes: MutableList<Ingrediente> = mutableListOf()
}

// ENTIDAD: Ingrediente (Relacionada con ComponenteDieta)
@Entity(
    tableName = "ingrediente",
    foreignKeys = [
        ForeignKey(
            entity = ComponenteDieta::class,
            parentColumns = ["id"],
            childColumns = ["componente_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["componente_id"])]
)
data class Ingrediente(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "componente_id") val componenteId: String, // Clave foránea con ComponenteDieta
    @ColumnInfo(name = "cantidad") var cantidad: Double

) : Serializable {
    @Ignore
    lateinit var cd: ComponenteDieta

    fun setComponenteDieta(componenteDieta: ComponenteDieta) {
        cd = componenteDieta
    }
}

// RELACIÓN: Un ComponenteDieta con sus Ingredientes
data class ComponenteDietaConIngredientes(
    @Embedded val componenteDieta: ComponenteDieta,
    @Relation(
        parentColumn = "id",
        entityColumn = "componente_id"
    )
    val ingredientes: List<Ingrediente>
)

// ------------------------------------------------------------------------------------------------------------------------------------










