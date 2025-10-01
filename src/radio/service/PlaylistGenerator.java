package radio.service;

import radio.model.Program;
import radio.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 *
 * @author funes
 */



/**
 * Genera una selección aleatoria de canciones para un programa,
 * filtrando por género y respetando que la duración total sea
 * estrictamente menor a {@code program.durationMinutes}.
 */
public class PlaylistGenerator {

    private final Random random = new Random();

    /**
     * Devuelve una lista de canciones aleatorias cuyo género coincide con el del programa
     * y cuya suma de minutos es < duracion del programa.
     *
     * @param poolSongs canciones disponibles (se tomarán solo las del género del programa)
     * @param program   programa objetivo
     * @return lista (posiblemente vacía) que cumple las restricciones
     */
    public List<Song> generate(List<Song> poolSongs, Program program) {
        if (poolSongs == null) throw new IllegalArgumentException("poolSongs is required");
        if (program == null) throw new IllegalArgumentException("program is required");

        String g = program.getGenre();
        int maxMinutes = program.getDurationMinutes();

        // Filtrar por género del programa
        List<Song> candidates = new ArrayList<>();
        for (Song s : poolSongs) {
            if (s.getGenre().equalsIgnoreCase(g) && s.getDurationMinutes() < maxMinutes) {
                candidates.add(s);
            }
        }

        // Si no hay candidatos útiles, devolver vacío (el servicio llamante mostrará el mensaje)
        if (candidates.isEmpty()) return Collections.emptyList();

        // Algoritmo simple: barajar y agregar greedily mientras no exceda
        Collections.shuffle(candidates, random);
        List<Song> result = new ArrayList<>();
        int sum = 0;

        for (Song s : candidates) {
            if (sum + s.getDurationMinutes() < maxMinutes) {
                result.add(s);
                sum += s.getDurationMinutes();
            }
        }

        // Si por azar quedó vacío pero existen candidatos, intentar una alternativa:
        if (result.isEmpty()) {
            // Buscar la canción más corta que sea < maxMinutes
            Song shortest = null;
            for (Song s : candidates) {
                if (s.getDurationMinutes() < maxMinutes) {
                    if (shortest == null || s.getDurationMinutes() < shortest.getDurationMinutes()) {
                        shortest = s;
                    }
                }
            }
            if (shortest != null) result.add(shortest);
        }

        return result;
    }
}

