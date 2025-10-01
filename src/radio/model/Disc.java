package radio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * class Disc 
    - String title
    - String singer
    - String genre
    - String coverImagePath
    - ListSong> songs
    + addSong(s: Song)
 * 
 * @author funes
 */
public class Disc {
    private String title;
    private String singer;
    private String genre;
    private String coverImagePath; // opcional
    private final List<Song> songs = new ArrayList<>();

    private static String req(String v, String field) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return v.trim();
    }

    /**
     * Crea un disco con sus datos principales.
     * @param coverImagePath puede ser null o vacío.
     */
    public Disc(String title, String singer, String genre, String coverImagePath) {
        this.title = req(title, "title");
        this.singer = req(singer, "singer");
        this.genre = req(genre, "genre");
        this.coverImagePath = (coverImagePath == null || coverImagePath.isBlank())
                ? null : coverImagePath.trim();
    }

    public String getTitle() { return title; }
    public String getSinger() { return singer; }
    public String getGenre()  { return genre; }
    public String getCoverImagePath() { return coverImagePath; }

    public void setTitle(String title) { this.title = req(title, "title"); }
    public void setSinger(String singer) { this.singer = req(singer, "singer"); }
    public void setGenre(String genre) { this.genre = req(genre, "genre"); }
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = (coverImagePath == null || coverImagePath.isBlank())
                ? null : coverImagePath.trim();
    }

    /**
     * Agrega una canción al disco. Evita duplicados según equals de Song.
     */
    public void addSong(Song s) {
        if (s == null) throw new IllegalArgumentException("song is required");
        if (!songs.contains(s)) songs.add(s);
    }

    /**
     * Retorna una vista inmutable del listado de canciones.
     */
    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Disc)) return false;
        Disc disc = (Disc) o;
        // Igualdad por título + cantante (ignorando mayúsculas)
        return title.equalsIgnoreCase(disc.title)
                && singer.equalsIgnoreCase(disc.singer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), singer.toLowerCase());
    }

    @Override
    public String toString() {
        return "Disc{" + title + " - " + singer + " [" + genre + "]}";
    }
}
