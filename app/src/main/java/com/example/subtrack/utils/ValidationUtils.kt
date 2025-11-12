package com.example.subtrack.utils

object ValidationUtils {
    
    /**
     * Valida si una contraseña cumple con los requisitos mínimos.
     * 
     * @param password La contraseña a validar
     * @return true si la contraseña es válida, false en caso contrario
     */
    fun isValidPassword(password: String): Boolean {
        return password.length > 6
    }
    
    /**
     * Valida si un correo electrónico tiene un formato válido.
     * 
     * @param email El correo electrónico a validar
     * @return true si el correo es válido, false en caso contrario
     */
    fun isValidEmail(email: String): Boolean {
        val emailRegex = """
            [a-zA-Z0-9+._%\-]{1,256}
            @
            [a-zA-Z0-9][a-zA-Z0-9\-]{0,64}
            (\.
                [a-zA-Z0-9][a-zA-Z0-9\-]{0,25}
            )+
        """.trimIndent().replace("\n", "")
        
        return email.matches(emailRegex.toRegex())
    }
}
