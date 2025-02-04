package com.example.pruebaroom2025.modelo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "alimentos")
data class Alimento(
    @PrimaryKey val id: String= java.util.UUID.randomUUID().toString(),
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "gr_prot") val grProt: Double = 0.0,
    @ColumnInfo(name = "gr_hc") val grHC: Double = 0.0,
    @ColumnInfo(name = "gr_lip") val grLip: Double = 0.0,
    @ColumnInfo(name = "cantidad") val cantidad: Double = 100.0

) {

    @Ignore
    private var caloriasTotales: Double=0.0
    fun calcularCalorias() {
        caloriasTotales = 4 * grProt * cantidad / 100 + 4 * grHC * cantidad / 100 + 9 * grLip * cantidad / 100
    }
}

@Entity(
    tableName = "ingredientes",
    foreignKeys = [
        ForeignKey(
            entity = Alimento::class,
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
    @Embedded val alimento: Alimento,
    @Relation(
        parentColumn = "id",
        entityColumn = "alimentoId"
    )
    val ingredientes: List<Ingrediente>
)



