package modelo

import BD_Fichero_Android
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import java.io.Serializable


/*--- CONTENIDOS

1 enum class TipoComponente
2 data class ComponenteDieta ( incluye lógica SIMPLE / PROCESADO )
3 data class Ingrediente
4 CDModelView: ViewModel()   */

enum class TipoComponente {
    SIMPLE,PROCESADO,MENU,RECETA,DIETA
}

data class ComponenteDieta(
    val id: String = java.util.UUID.randomUUID().toString(), // Genera un ID únic
    var nombre: String,
    var tipo:TipoComponente=TipoComponente.SIMPLE,
    var grHC_ini: Double=0.0,
    var grLip_ini:Double=0.0,
    var grPro_ini:Double=0.0,

): Serializable {

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
//    var ingredientes = mutableStateListOf<Ingrediente>()

    constructor() : this("","",TipoComponente.SIMPLE,0.0, 0.0, 0.0)

    //fun TipoComponente.esSimpleOProcesado() = this == TipoComponente.SIMPLE || this == TipoComponente.PROCESADO
    fun TipoComponente.esSimpleOProcesado() = this == TipoComponente.SIMPLE

    fun addIngrediente(ing: Ingrediente) : Boolean {
        return if (!ingredientes.contains(ing)) {
            ingredientes.add(ing)
            true
        } else false
    }
    fun removeIngrediente(ing: Ingrediente): Boolean  {
        return ingredientes.remove(ing)
    }

    fun actualizarIngredientes( listaIngredientes:MutableList<Ingrediente>){
        ingredientes = listaIngredientes
    }

    fun leerIngredientes(): MutableList<Ingrediente>{
        return ingredientes
    }

    fun addIngredientes(ings: List<Ingrediente>): Boolean {
        var respuesta = false
        for (ing in ings) {
            if (!ingredientes.contains(ing)) {
                ingredientes.add(ing)
                respuesta = true
            } else {
                respuesta = false
            }
        }
        return respuesta
    }
}

// ------------------------------------------------------------------------------------------------- CLASE Ingrediente -------------------
//Cantidad de un ingrediente es un % sobre la cantidadTotal de un componenteDieta
//si no se especifica nada se supone que es 100%
//Si un alimento no especifica su cantidadTotal, se supone 100 gr o 100 ml
data class Ingrediente(
    var cd: ComponenteDieta,
    var cantidad:Double ): Serializable
{

}
// ------------------------------------------------------------------------------------------------------------------------------------


class CDModelView() : ViewModel() {

    //---- listas Ingredientes
    private var _componentes = MutableLiveData<List<ComponenteDieta>>(emptyList())
    var componentes: LiveData<List<ComponenteDieta>> = _componentes

    fun añadirComponente(nuevoCD: ComponenteDieta) {
       val listaActualizada = _componentes.value?.toMutableList() ?: mutableListOf()
        listaActualizada.add(nuevoCD)
        _componentes.value = listaActualizada
    }

    fun actualizarComponente(id: String, nuevaCD: ComponenteDieta) {
        val listaActualizada = _componentes.value?.toMutableList() ?: mutableListOf()
        val index = listaActualizada.indexOfFirst { it.id == id }
        if (index != -1) {
            listaActualizada[index] = nuevaCD
            _componentes.value = listaActualizada
        }
    }
    fun eliminarComponente(id: String) {
        val listaActualizada = _componentes.value?.toMutableList() ?: mutableListOf()
        listaActualizada.removeAll { it.id == id }
        _componentes.value = listaActualizada
    }

    fun leerComponentes(): MutableList<ComponenteDieta>{
        return _componentes.value?.toMutableList()?: mutableListOf()
    }

    fun borrarTodo(){
        _componentes = MutableLiveData<List<ComponenteDieta>>(emptyList())
        componentes = _componentes
    }

    fun cargarDatos(context: Context) {
        val bdFichero = BD_Fichero_Android(context, "personas")
        val listaCargada = bdFichero.leer()
        val listaActualizada = (_componentes.value.orEmpty() + listaCargada).distinctBy { it.id }
        _componentes.value = listaActualizada
    }

    fun guardarDatos(context: Context){
        val bdFichero = BD_Fichero_Android(context, "personas")
        val lista= leerComponentes()
        val listaActualizada = (lista + (_componentes.value ?: emptyList())).distinctBy { it.id }
        bdFichero.guardar(listaActualizada.toMutableList())
    }

    fun  borrarDatos(context: Context){
        val bdFichero = BD_Fichero_Android(context, "personas")
        bdFichero.borrarArchivos()
    }

}

//------------- PRUEBA DE INGREDIENTES

fun pruebaIngredientes(viewModel: CDModelView){

    // Crear dos ingredientes
    val ingrediente1 = Ingrediente(
        cd = ComponenteDieta(
            nombre = "Harina",
            tipo = TipoComponente.SIMPLE,
            grHC_ini = 75.0,
            grLip_ini = 1.5,
            grPro_ini = 10.0
        ),
        cantidad = 50.0 // 50% del total
    )

    val ingrediente2 = Ingrediente(
        cd = ComponenteDieta(
            nombre = "Azúcar",
            tipo = TipoComponente.SIMPLE,
            grHC_ini = 99.0,
            grLip_ini = 0.0,
            grPro_ini = 0.0
        ),
        cantidad = 30.0 // 30% del total
    )

    Log.i("DATA PRUEBA", "Ingrediente1: ${ingrediente1.cd.nombre}, grHC: ${ingrediente1.cd.grHC_ini}")
    Log.i("DATA PRUEBA", "Ingrediente2: ${ingrediente2.cd.nombre}, grHC: ${ingrediente2.cd.grHC_ini}")



    // Crear un ComponenteDieta PROCESADO
    val nuevoCompoProcesado = ComponenteDieta(
        nombre = "Pan de PRUEBA",
        tipo = TipoComponente.PROCESADO
    )
    Log.i("DATA PRUEBA", "Ingredientes antes de cálculo: ${nuevoCompoProcesado.ingredientes}")
    Log.i("DATA PRUEBA", "Calorías: ${nuevoCompoProcesado.Kcal}")

    nuevoCompoProcesado.addIngrediente(ingrediente1)
    nuevoCompoProcesado.addIngrediente(ingrediente2)

    Log.i("DATA PRUEBA","${nuevoCompoProcesado.Kcal}" )
    nuevoCompoProcesado.ingredientes.forEach{ compo->
        Log.i("DATA PRUEBA","${compo.cd.nombre} ${compo.cd.Kcal}" )
    }

    viewModel.añadirComponente(nuevoCompoProcesado)
}


