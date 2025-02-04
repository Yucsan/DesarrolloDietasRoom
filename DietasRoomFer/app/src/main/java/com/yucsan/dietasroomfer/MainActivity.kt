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
import com.example.examenjpc_1.screens.Formulario
import com.example.examenjpc_1.screens.Inicio
import com.example.examenjpc_1.screens.ListadoDetalle
import com.example.pruebaroom2025.modelo.Alimento
import com.example.pruebaroom2025.modelo.AlimentoRepository
import com.example.pruebaroom2025.modelo.AppDatabase
import com.example.pruebaroom2025.modelo.Ingrediente

import com.yucsan.dietasroomfer.ui.theme.DietasRoomFerTheme
import com.yucsan.proyectodieta_ver2.CompoNav.NavigationBar
import com.yucsan.proyectodieta_ver2.componentes.DrawerContent
import com.yucsan.proyectodieta_ver2.componentes.MiTopAppBar
import com.yucsan.proyectodieta_ver2.screens.Ruta

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import modelo.CDModelView
import modelo.TipoComponente


class MainActivity : ComponentActivity() {

  val viewModelCD: CDModelView by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    prueba()

    setContent {
      val context = LocalContext.current
      val bdFichero: BD_Fichero_Android=BD_Fichero_Android(context, "Componente")

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

            topBar = {  MiTopAppBar(onMenuClick = { scope.launch { drawerState.open() } })  },
            bottomBar = { NavigationBar(navigationController)}

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
                Formulario(navigationController, opciones, viewModelCD, context )
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

  private fun prueba() {

    Log.d(
      "Room FERNANDO",
      "***********************************PRUEBA DE FERNANDO ****************************************"
    )

    // Inicializar la base de datos y DAO
    val db = AppDatabase.obtenerInstancia(this)
    val dao = db.alimentoDao()
    val repo = AlimentoRepository(dao)

    // Operaciones ejecutables con Room
    CoroutineScope(Dispatchers.IO).launch {
      // Insertar datos en la base de datos
      repo.insertar(
        Alimento(
          nombre = "Manzana",
          grProt = 0.3,
          grHC = 14.0,
          grLip = 0.2,
          cantidad = 100.0
        )
      )
      repo.insertar(
        Alimento(
          nombre = "Pera",
          grProt = 0.4,
          grHC = 12.0,
          grLip = 0.1,
          cantidad = 150.0
        )
      )
      repo.insertar(
        Alimento(
          nombre = "Plátano",
          grProt = 1.3,
          grHC = 27.0,
          grLip = 0.3,
          cantidad = 120.0
        )
      )
      repo.insertar(
        Alimento(
          nombre = "Fresas",
          grProt = 0.8,
          grHC = 7.7,
          grLip = 0.3,
          cantidad = 150.0
        )
      )
      repo.insertar(
        Alimento(
          nombre = "Pollo",
          grProt = 27.0,
          grHC = 0.0,
          grLip = 3.6,
          cantidad = 100.0
        )
      )

      // Leer datos de la base de datos
      val alimentos = repo.getAlimentos().first() // Toma el primer valor emitido por el Flow
      alimentos.forEach {
        Log.d(
          "Room Fernando",
          "${it.nombre}: ${it.calcularCalorias()} KCal (${it.grProt}g proteínas, ${it.grHC}g HC, ${it.grLip}g lípidos)"
        )
      }


      // Verificar datos después del borrado
      val alimentosActualizados = repo.getAlimentos().first()
      alimentosActualizados.forEach {
        Log.d(
          "Room Fernando",
          "${it.nombre}: ${it.calcularCalorias()} KCal (${it.grProt}g proteínas, ${it.grHC}g HC, ${it.grLip}g lípidos)"
        )
      }

      // ***************** en DESARROLLO *********************************

      val alimento = repo.getAlimentoByName("Plátano").firstOrNull()

      if (alimento != null) {
        val ingrediente = Ingrediente(alimentoId = alimento.id, cantidad = 50f)
        repo.insertarIngrediente(ingrediente)
      } else {
        Log.i("Room Fernando",
          "❌ No se encontró el alimento.")
      }

      val ingredientes= repo.getIngredientes().first()
      ingredientes.forEach{
        Log.i("Room Fernando",
          "INGREDIENTE ID: ${it.id} CANTIDAD: ${it.cantidad} "
        )
      }

    }
  }
}








