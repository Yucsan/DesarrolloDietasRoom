package modelo

interface IDaoCD {

    fun addListaCD(lista: MutableList<ComponenteDieta>)
    fun CreateComponente(componente:ComponenteDieta)
    fun readComponentes(): MutableList<ComponenteDieta>
    fun readComponentesByTipo(tipo: TipoComponente): MutableList<ComponenteDieta>
    fun readComponente(id:Int):ComponenteDieta?
    fun readComponente(nombre:String):ComponenteDieta?
    fun readComponenteByIngrediente()
    fun updateComponente(componenteOld:ComponenteDieta,componenteNew:ComponenteDieta)
    fun deleteComponente(componente:ComponenteDieta):Boolean
}