package radio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  class Playlist {
    - Program program
    - ListSong> tracks
    - int totalMinutes
 * 
 * @author funes
 */
public class Playlist {
    private final Program program;
    private final List<Song> songs;
    private final int totalMinutes;

    /**
     * Construye un playlist válido para el programa.
     * @param program programa al que pertenece el playlist (requerido)
     * @param songs   canciones del playlist (no vacío)
     * @throws IllegalArgumentException si las reglas de negocio no se cumplen
     */
    public Playlist(Program program, List<Song> songs) {
        if (program == null) throw new IllegalArgumentException("program is required");
        if (songs == null || songs.isEmpty()) throw new IllegalArgumentException("songs are required");

        this.program = program;

        String g = program.getGenre();
        int sum = 0;
        for (Song s : songs) {
            if (!s.getGenre().equalsIgnoreCase(g)) {
                throw new IllegalArgumentException("song genre must match program genre");
            }
            sum += s.getDurationMinutes();
        }
        if (sum >= program.getDurationMinutes()) {
            throw new IllegalArgumentException("playlist duration must be < program duration");
        }

        this.totalMinutes = sum;
        this.songs = Collections.unmodifiableList(new ArrayList<>(songs));
    }

    public Program getProgram() { return program; }
    public List<Song> getSongs() { return songs; }
    public int getTotalMinutes() { return totalMinutes; }

    @Override
    public String toString() {
        return "Playlist{" + program.getName() + " - " + totalMinutes + "m, songs=" + songs.size() + "}";
    }
}
