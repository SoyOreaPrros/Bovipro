package com.example.demo;

/**
 * Almacena el estado global de la sesión: contraseña activa,
 * pregunta de seguridad y respuesta.  Todo en memoria (simulado).
 */
public class SesionManager {

    private static String password        = "1234";
    private static String pregunta        = "¿Cuál es el nombre de tu primera mascota?";
    private static String respuesta       = "firulais";   // comparar en minúsculas

    // ── Contraseña ────────────────────────────────────────────────────────────
    public static String getPassword()               { return password; }
    public static void   setPassword(String p)       { password = p;    }

    // ── Pregunta de seguridad ─────────────────────────────────────────────────
    public static String getPregunta()               { return pregunta;  }
    public static String getRespuesta()              { return respuesta; }

    public static void setPreguntaYRespuesta(String preg, String resp) {
        pregunta  = preg;
        respuesta = resp.trim().toLowerCase();
    }

    public static boolean verificarRespuesta(String intento) {
        return respuesta.equals(intento.trim().toLowerCase());
    }
}
