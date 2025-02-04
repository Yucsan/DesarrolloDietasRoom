package modelo

import BD_Fichero_Android

class DaoIngrediente(private val bdFichero: BD_Fichero_Android): IDaoIngrediente {

    private var listaIng: MutableList<Ingrediente> = mutableListOf()

    fun añadirIngrediente(
        padre: ComponenteDieta,
        hijo: ComponenteDieta,
        cantidad: Double
    ): Boolean {
        val ingrediente = Ingrediente(cd = hijo, cantidad = cantidad)
        return padre.addIngrediente(ingrediente)
    }

    override fun createIngrediente(padre: ComponenteDieta, hijo: ComponenteDieta, cantidad: Double):Boolean {

        val ing= Ingrediente(hijo, cantidad)
        return if (!padre.ingredientes.contains(ing)) {
            padre.ingredientes.add(ing)
            persistirDatosIng() //persiste
            true
        } else false
    }

    fun persistirDatosIng() {
        bdFichero.guardarI(listaIng)
    }

    override fun readIngredientesByComponente(componente: ComponenteDieta):MutableList<Ingrediente> {
       return componente.ingredientes
    }

    override fun readIngredienteByComponente(componente: ComponenteDieta, ing:Ingrediente):Ingrediente?{
       return componente.ingredientes.find { it==ing }
    }


    override fun updateIngrediente(componente: ComponenteDieta, ingrediente: Ingrediente, cantidad: Double) {
        val index = componente.ingredientes.indexOf(ingrediente)
        if (index != -1) {
            componente.ingredientes[index].cantidad = cantidad
        }
    }

    override fun deleteIngredientebyComponente(componente: ComponenteDieta, ing:Ingrediente ):Boolean {

        return componente.ingredientes.remove(ing)

    }
}