package com.example.pruebaroom2025.modelo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alimento::class, Ingrediente::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alimentoDao(): AlimentoDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "alimentos2_db"
                )
                .fallbackToDestructiveMigration() // Permite actualizar la base sin problemas si hay cambios en la estructura
                .build().also { instancia = it }
            }
        }
    }
}