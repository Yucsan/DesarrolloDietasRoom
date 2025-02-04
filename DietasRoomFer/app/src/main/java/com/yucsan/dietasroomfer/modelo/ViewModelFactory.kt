package com.yucsan.proyectodieta_ver2.modelo

import BD_Fichero_Android
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import modelo.CDModelView
import modelo.DaoCD
/*
class CDModelViewFactory(private val context: Context) : ViewModelProvider.Factory {
   override fun <T : ViewModel> create(modelClass: Class<T>): T {  // --> Este método verifica si el ViewModel que se está solicitando es un CDModelView y lo crea. Si no es el caso, lanza una excepción.
      if (modelClass.isAssignableFrom(CDModelView::class.java)) {
         val bdFichero = BD_Fichero_Android(context, "Componentes")  //Aquí inicializa BD_Fichero_Android con el context, luego crea una instancia de DaoCD pasándole el archivo.
         val dao = DaoCD(bdFichero)
         return CDModelView(dao) as T   // Finalmente, construye el CDModelView y lo devuelve.
      }
      throw IllegalArgumentException("Unknown ViewModel class")
   }
}*/

/*
 La clase CDModelViewFactory
La clase CDModelViewFactory actúa como una fábrica para crear instancias de tu CDModelView.
Esto es necesario porque el constructor de CDModelView necesita argumentos personalizados
(DaoCD, que a su vez necesita el BD_Fichero_Android y el Context).

¿Por qué es necesario esto?
El sistema de ViewModel de Android está diseñado para usar constructores sin parámetros.
Si tu ViewModel necesita dependencias (como el Dao), debes usar una Factory para construirlo
y proporcionarle esas dependencias.




* */
