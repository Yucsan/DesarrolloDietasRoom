package com.yucsan.dietasroomfer

import BD_Fichero_Android
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examenjpc_1.screens.ListadoDetalle
import com.yucsan.dietasroomfer.CompoNav.NavigationBar
import com.yucsan.dietasroomfer.componentes.DrawerContent
import com.yucsan.dietasroomfer.componentes.MiTopAppBar
import com.yucsan.dietasroomfer.modeloRoom.AlimentoRepository
import com.yucsan.dietasroomfer.modeloRoom.AppDatabase
import com.yucsan.dietasroomfer.screens.Formulario
import com.yucsan.dietasroomfer.screens.Inicio
import com.yucsan.dietasroomfer.screens.Ruta


import com.yucsan.dietasroomfer.ui.theme.DietasRoomFerTheme


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import com.yucsan.dietasroomfer.modeloRoom.CDModelView
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.Ingrediente
import com.yucsan.dietasroomfer.modeloRoom.TipoComponente
import java.util.UUID

class MainActivity : ComponentActivity() {

   val viewModelCD: CDModelView by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      //prueba2()
      //prueba()

      setContent {
         val context = LocalContext.current
         val bdFichero: BD_Fichero_Android = BD_Fichero_Android(context, "Componente")

         // variables Navegación
         val navigationController = rememberNavController()
         val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
         val scope = rememberCoroutineScope()

         // Opciones Formulario
         val opciones = listOf(TipoComponente.SIMPLE, TipoComponente.PROCESADO)

         ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
               DrawerContent { route ->
                  scope.launch { drawerState.close() }
                  navigationController.navigate(route)
               }
            }
         ) {
            DietasRoomFerTheme {
               Scaffold(

                  topBar = { MiTopAppBar(onMenuClick = { scope.launch { drawerState.open() } }) },
                  bottomBar = { NavigationBar(navigationController) }

               ) { paddingValues ->
                  NavHost(
                     navController = navigationController,
                     startDestination = Ruta.Pantalla1.ruta,
                     modifier = Modifier.padding(paddingValues)
                  ) {
                     composable(Ruta.Pantalla1.ruta) {
                        Inicio(viewModelCD, context)
                     }
                     composable(Ruta.Pantalla2.ruta) {
                        Formulario(navigationController, opciones, viewModelCD, context)
                     }
                     composable(Ruta.Pantalla3.ruta) {
                        ListadoDetalle(navigationController, opciones, viewModelCD, context)
                     }
                  }
               }
            }
         }
      }
   }

   fun prueba() {
      // Inicializar la base de datos y DAO
      val db = AppDatabase.obtenerInstancia(this)
      val dao = db.alimentoDao()
      val repo = AlimentoRepository(dao)

      CoroutineScope(Dispatchers.IO).launch {
         // 🧹 1️⃣ Limpiar la base de datos antes de la prueba
         //repo.borrarTodaLaInformacion()

         // 🥗 2️⃣ Insertar un ComponenteDieta (Ensalada César) con ID específico
         val ensaladaCesar = ComponenteDieta(
            id = UUID.fromString("88547a1c-bd8e-46cc-b0f7-2b0ef8bef0f9")
               .toString(), // Usamos el ID proporcionado
            nombre = "Ensalada César",
            tipo = TipoComponente.PROCESADO,
            grHC_ini = 5.0,
            grLip_ini = 10.0,
            grPro_ini = 15.0
         )
         repo.insertar(ensaladaCesar)

         // 🥗 3️⃣ Insertar dos ingredientes para la Ensalada César
         val ingrediente1 = Ingrediente(
            componenteId = ensaladaCesar.id,
            cantidad = 50.0
         )
         val ingrediente2 = Ingrediente(
            componenteId = ensaladaCesar.id,
            cantidad = 30.0
         )
         repo.insertarIngrediente(ingrediente1)
         repo.insertarIngrediente(ingrediente2)

         // 📜 4️⃣ Obtener y mostrar los ingredientes insertados
         val ingredientes = repo.getIngredientes().first()
         ingredientes.forEach {
            Log.i(
               "ROOM FERNANDO MIRA",
               "Ingrediente - ID: ${it.id}, Cantidad: ${it.cantidad}, ComponenteDieta ID: ${it.componenteId}"
            )
         }

         // 📜 5️⃣ Obtener el ComponenteDieta con sus ingredientes
         val ingredientesCesar = dao.obtenerIngredientesPorComponente(ensaladaCesar.id)
         Log.i("ROOM DEBUG FERNANDO", "🥗 Ingredientes de Ensalada César: ${ingredientesCesar.size}")

      }
   }


   fun prueba2() {
      // Inicializar la base de datos y DAO
      val db = AppDatabase.obtenerInstancia(this)
      val dao = db.alimentoDao()
      val repo = AlimentoRepository(dao)

      CoroutineScope(Dispatchers.IO).launch {
         // 🧹 1️⃣ Limpiar la base de datos antes de la prueba
         repo.borrarTodaLaInformacion()

         // 🥗 2️⃣ Insertar un ComponenteDieta (Ensalada César)
         val ensaladaCesar = ComponenteDieta(
            nombre = "Ensalada César",
            tipo = TipoComponente.PROCESADO,
            grHC_ini = 5.0,
            grLip_ini = 10.0,
            grPro_ini = 15.0
         )
         repo.insertar(ensaladaCesar)

         // 🥗 3️⃣ Insertar dos ingredientes para la Ensalada César
         val ingrediente1 = Ingrediente(
            componenteId = ensaladaCesar.id,
            cantidad = 50.0
         )
         val ingrediente2 = Ingrediente(
            componenteId = ensaladaCesar.id,
            cantidad = 30.0
         )
         repo.insertarIngrediente(ingrediente1)
         repo.insertarIngrediente(ingrediente2)

         // 🍞 4️⃣ Insertar tres Componentes Simples (Pan, Leche y Trigo)
         val pan = ComponenteDieta(
            nombre = "Pan",
            tipo = TipoComponente.SIMPLE,
            grHC_ini = 50.0,
            grLip_ini = 3.0,
            grPro_ini = 9.0
         )
         val leche = ComponenteDieta(
            nombre = "Leche",
            tipo = TipoComponente.SIMPLE,
            grHC_ini = 5.0,
            grLip_ini = 3.5,
            grPro_ini = 3.3
         )
         val trigo = ComponenteDieta(
            nombre = "Trigo",
            tipo = TipoComponente.SIMPLE,
            grHC_ini = 60.0,
            grLip_ini = 2.0,
            grPro_ini = 12.0
         )

         repo.insertar(pan)
         repo.insertar(leche)
         repo.insertar(trigo)

         // 📜 5️⃣ Obtener y mostrar los Componentes Dieta insertados
         val componentes = repo.getAlimentos().first()
         componentes.forEach {
            Log.i(
               "ROOM FERNANDO MIRA",
               "ComponenteDieta: ${it.nombre} (HC: ${it.grHC_ini}, Lip: ${it.grLip_ini}, Prot: ${it.grPro_ini}) ID: ${it.id}"
            )
         }

         // 📜 6️⃣ Obtener y mostrar los Ingredientes insertados
         val ingredientes = repo.getIngredientes().first()
         ingredientes.forEach {
            Log.i(
               "ROOM FERNANDO MIRA",
               "Ingrediente - ID: ${it.id}, Cantidad: ${it.cantidad}, ComponenteDieta ID: ${it.componenteId}"
            )
         }

         // 📜 7️⃣ Obtener un ComponenteDieta con sus ingredientes
         val dietaConIngredientes = dao.obtenerIngredientePorComponenteDietaId(ensaladaCesar.id)
         Log.i(
            "ROOM FERNANDO MIRA",
            "ComponenteDieta: ${dietaConIngredientes}.-------- "
         )
      }

   }


}







