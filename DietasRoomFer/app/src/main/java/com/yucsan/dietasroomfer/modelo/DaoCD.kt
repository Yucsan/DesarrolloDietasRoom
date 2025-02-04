package modelo

import BD_Fichero_Android

class DaoCD(private val bdFichero: BD_Fichero_Android) : IDaoCD {

    private var lista: MutableList<ComponenteDieta> = mutableListOf()

    override fun addListaCD(lista: MutableList<ComponenteDieta>) {
        this.lista = lista
    }

    override fun CreateComponente(componente: ComponenteDieta) {
        lista.add(componente)
        persistirDatos()
    }

    override fun readComponentes(): MutableList<ComponenteDieta> {
        return lista
    }

    override fun readComponentesByTipo(tipo: TipoComponente): MutableList<ComponenteDieta> {
        return lista.filter { it.tipo == tipo }.toMutableList()
    }

    override fun readComponente(id: Int): ComponenteDieta? {
        TODO("Not yet implemented")
    }

    override fun readComponente(nombre: String): ComponenteDieta? {
        return lista.find { it.nombre == nombre }
    }

    override fun readComponenteByIngrediente() {
        TODO("Not yet implemented")
    }

    override fun updateComponente(componenteOld: ComponenteDieta, componenteNew: ComponenteDieta) {
        val index = lista.indexOf(componenteOld)
        if (index != -1) {
            lista[index] = componenteNew
            persistirDatos()
        }
    }

    override fun deleteComponente(componente: ComponenteDieta): Boolean {
        val result = lista.remove(componente)
        if (result) persistirDatos()
        return result
    }

    fun persistirDatos() {
        bdFichero.guardar(lista)
    }

    fun cargarDatos() {
        lista = bdFichero.leer().toMutableList()
    }

    fun borrarDatos(){
        lista.clear() // Limpia la lista en memoria
        bdFichero.borrarArchivos()
    }
}
