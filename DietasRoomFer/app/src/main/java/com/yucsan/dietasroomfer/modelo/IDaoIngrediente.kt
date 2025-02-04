package modelo

interface IDaoIngrediente {

    fun createIngrediente(padre: ComponenteDieta, ing: ComponenteDieta, cantidad: Double):Boolean
    fun readIngredienteByComponente(componente: ComponenteDieta, ing:Ingrediente):Ingrediente?
    fun readIngredientesByComponente(Componente:ComponenteDieta):MutableList<Ingrediente>
    fun updateIngrediente (componente: ComponenteDieta, ingrediente:Ingrediente, cantidad:Double)
    fun deleteIngredientebyComponente(cd: ComponenteDieta, ing:Ingrediente):Boolean
}