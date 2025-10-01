package radio.service;

import java.util.*;
/**
 *
 * @author funes
 */

/* Maneja géneros híbridos: base + personalizados. */

/**
 * Servicio simple para gestionar géneros musicales.
 * <p>
 * Permite normalizar y asegurar que un género exista en el catálogo.
 * No impone una lista cerrada: puede crecer dinámicamente.
 */
public class GenreService {

    private final Set<String> genres = new LinkedHashSet<>();

    /**
     * Crea el servicio con algunos géneros base (opcional).
     */
    public GenreService() {
        // Puedes ajustar la semilla inicial
        Collections.addAll(genres,
                "ROCK", "POP", "JAZZ", "SALSA", "REGGAETON", "ELECTRONICA", "CLASICA", "OTRO");
    }

    /**
     * Normaliza un género (trim + upperCase).
     */
    public String normalize(String genre) {
        if (genre == null) throw new IllegalArgumentException("genre is required");
        return genre.trim().toUpperCase();
    }

    /**
     * Asegura que el género exista en el catálogo y retorna su forma normalizada.
     */
    public String ensure(String genre) {
        String g = normalize(genre);
        genres.add(g);
        return g;
    }

    /**
     * Lista todos los géneros conocidos (vista inmutable).
     */
    public Set<String> listAll() {
        return Collections.unmodifiableSet(genres);
    }
}

