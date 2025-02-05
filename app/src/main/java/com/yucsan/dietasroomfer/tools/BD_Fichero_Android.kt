import android.content.Context
import android.util.Log
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.Ingrediente
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class BD_Fichero_Android(private val context: Context, val nombre:String  ) {

   fun guardarI(listaIng:MutableList<Ingrediente>) {
      try {
         context.openFileOutput(nombre, Context.MODE_PRIVATE).use { fos ->
            ObjectOutputStream(fos).use { oos ->
               oos.writeObject(listaIng.toMutableList())
            }

         }
      } catch (e: IOException) {
         Log.e("Error", "Error al guardar el archivo", e)
      }
   }

  fun guardar(listaCD:MutableList<ComponenteDieta>) {
        try {
            context.openFileOutput(nombre, Context.MODE_PRIVATE).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(listaCD.toMutableList())
                }

            }
        } catch (e: IOException) {
            Log.e("Error", "Error al guardar el archivo", e)
        }
    }

    fun leer():MutableList<ComponenteDieta> {
        return if (context.getFileStreamPath(nombre).exists()) {
            try {
                context.openFileInput(nombre).use { fis ->
                    ObjectInputStream(fis).use { ois ->
                       ois.readObject() as MutableList<ComponenteDieta>
                     }
                }
            } catch (e: IOException) {
                Log.e("Error", "Error al leer el archivo", e)
                mutableListOf()
            } catch (e: ClassNotFoundException) {
                Log.e("Error", "Clase no encontrada", e)
                mutableListOf()
            }
        } else {
           mutableListOf() // Lista vacía si no hay archivo
        }
    }

    fun borrarArchivos() {
        val archivo = context.getFileStreamPath(nombre)
        if (archivo.exists() && !archivo.delete()) {
            Log.e("Error", "No se pudo eliminar el archivo")
        }
    }

   fun listaArchivos(){
      val archivos = context.fileList()
      archivos.forEach { Log.d("Archivo", it) }

   }

}

