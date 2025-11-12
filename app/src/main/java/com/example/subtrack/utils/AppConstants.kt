package com.example.subtrack.utils

/**
 * Objeto que contiene constantes utilizadas en toda la aplicación.
 * 
 * @author [Tu Nombre]
 */
object AppConstants {
    // Nombres de preferencias compartidas
    const val PREF_NAME = "subtrack_prefs"
    const val PREF_USER_LOGGED_IN = "user_logged_in"
    
    // Constantes de la base de datos
    const val DATABASE_NAME = "subtrack_database"
    const val DATABASE_VERSION = 1
    
    // Constantes de la API (si aplica)
    const val API_BASE_URL = "https://api.subtrack.example.com/"
    const val API_TIMEOUT = 30L // segundos
    
    // Códigos de solicitud
    const val REQUEST_CODE_PERMISSIONS = 1001
    const val REQUEST_CODE_IMAGE_PICK = 1002
    
    // Otros
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_SUBSCRIPTION_NAME_LENGTH = 50
    
    // Formato de fechas
    object DateFormats {
        const val DISPLAY_DATE = "dd/MM/yyyy"
        const val DISPLAY_DATETIME = "dd/MM/yyyy HH:mm"
        const val API_DATE = "yyyy-MM-dd"
        const val API_DATETIME = "yyyy-MM-dd'T'HH:mm:ss"
    }
}
