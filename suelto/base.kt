import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import modelo.TipoComponente
import java.util.UUID


@Entity(tableName = "alimentos")
data class Alimento(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "gr_prot") val grProt: Double = 0.0,
    @ColumnInfo(name = "gr_hc") val grHC: Double = 0.0,
    @ColumnInfo(name = "gr_lip") val grLip: Double = 0.0,
    @ColumnInfo(name = "cantidad") val cantidad: Double = 100.0
) {
    @Ignore
    private var caloriasTotales: Double = 0.0

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
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["alimentoId"])]
)
data class Ingrediente(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
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

@Entity(tableName = "componente_dieta")
data class ComponenteDietaEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "tipo") val tipo: TipoComponente,
    @ColumnInfo(name = "grHC_ini") val grHCIni: Double = 0.0,
    @ColumnInfo(name = "grLip_ini") val grLipIni: Double = 0.0,
    @ColumnInfo(name = "grPro_ini") val grProIni: Double = 0.0
)

@Entity(
    tableName = "componente_ingrediente",
    primaryKeys = ["componenteId", "ingredienteId"],
    foreignKeys = [
        ForeignKey(
            entity = ComponenteDietaEntity::class,
            parentColumns = ["id"],
            childColumns = ["componenteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ingrediente::class,
            parentColumns = ["id"],
            childColumns = ["ingredienteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["componenteId"]), Index(value = ["ingredienteId"])]
)
data class ComponenteIngredienteCrossRef(
    val componenteId: String,
    val ingredienteId: String,
    val cantidad: Double // Guardamos la cantidad específica del ingrediente en el componente
)

data class ComponenteConIngredientes(
    @Embedded val componente: ComponenteDietaEntity,
    @Relation(
        parentColumn = "id", // ID de ComponenteDietaEntity
        entityColumn = "id", // ID correcto de Ingrediente en la tabla intermedia
        associateBy = Junction(ComponenteIngredienteCrossRef::class)
    )

    val ingredientes: List<Ingrediente>
)