package radio.model;

import java.util.Objects;
import java.util.Optional;

/**
 *class Program 
    - String name
    - String schedule
    - int durationMinutes
    - String genre
    - Announcer announcer
    - Playlist playlist
    + setPlaylistpl: Playlist)
 * 
 * @author funes
 */
public class Program {
    private String name;
    private String schedule;
    private int durationMinutes;
    private String genre;
    private Announcer announcer;

    private Playlist playlist;

    private static String req(String v, String field) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return v.trim();
    }

    private static int pos(int m, String field) {
        if (m <= 0) throw new IllegalArgumentException(field + " must be > 0");
        return m;
    }

    /**
     * Crea un programa con su locutor asignado.
     */
    public Program(String name, String schedule, int durationMinutes, String genre, Announcer announcer) {
        this.name = req(name, "name");
        this.schedule = req(schedule, "schedule");
        this.durationMinutes = pos(durationMinutes, "durationMinutes");
        this.genre = req(genre, "genre");
        if (announcer == null) throw new IllegalArgumentException("announcer is required");
        this.announcer = announcer;
    }

    public String getName() { return name; }
    public String getSchedule() { return schedule; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getGenre() { return genre; }
    public Announcer getAnnouncer() { return announcer; }

    public void setName(String name) { this.name = req(name, "name"); }
    public void setSchedule(String schedule) { this.schedule = req(schedule, "schedule"); }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = pos(durationMinutes, "durationMinutes"); }
    public void setGenre(String genre) { this.genre = req(genre, "genre"); }
    public void setAnnouncer(Announcer announcer) {
        if (announcer == null) throw new IllegalArgumentException("announcer is required");
        this.announcer = announcer;
    }

    /**
     * Asigna una playlist válida a este programa (no permite sobrescritura).
     * <ul>
     *   <li>La playlist debe pertenecer a este programa.</li>
     *   <li>La duración total de la playlist debe ser estrictamente menor a la del programa.</li>
     * </ul>
     */
    public void setPlaylist(Playlist playlist) {
        if (playlist == null) throw new IllegalArgumentException("playlist is required");
        if (this.playlist != null) throw new IllegalStateException("playlist already set");
        if (playlist.getProgram() != this) throw new IllegalArgumentException("playlist program mismatch");
        if (playlist.getTotalMinutes() >= this.durationMinutes) {
            throw new IllegalArgumentException("playlist too long for program");
        }
        this.playlist = playlist;
    }

    /**
     * Devuelve la playlist si existe.
     */
    public Optional<Playlist> getPlaylist() {
        return Optional.ofNullable(playlist);
    }

    /** Permite limpiar la playlist (si lo necesitas desde servicios/UI). */
    public void clearPlaylist() { this.playlist = null; }

    @Override
    public String toString() {
        return name + " (" + schedule + ", " + durationMinutes + "m, " + genre + ") - " + announcer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Program)) return false;
        Program program = (Program) o;
        // Igualdad por nombre (case-insensitive)
        return name.equalsIgnoreCase(program.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
    
