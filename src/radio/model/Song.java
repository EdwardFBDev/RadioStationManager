package radio.model;

import java.util.Objects;

/**
 *
 * class Song {
    - String title
    - int durationMinutes
    - String singer
    - String genre
 * 
 * @author funes
 */
public class Song {
    private String title;
    private int durationMinutes;
    private String singer;
    private String genre;

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
     * Crea una canción con todos sus datos requeridos.
     */
    public Song(String title, int durationMinutes, String singer, String genre) {
        this.title = req(title, "title");
        this.durationMinutes = pos(durationMinutes, "durationMinutes");
        this.singer = req(singer, "singer");
        this.genre = req(genre, "genre");
    }

    public String getTitle() { return title; }

    public int getDurationMinutes() { return durationMinutes; }

    public String getSinger() { return singer; }

    public String getGenre() { return genre; }

    public void setTitle(String title) { this.title = req(title, "title"); }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = pos(durationMinutes, "durationMinutes");
    }

    public void setSinger(String singer) { this.singer = req(singer, "singer"); }

    public void setGenre(String genre) { this.genre = req(genre, "genre"); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song s = (Song) o;
        // Igualdad por firma (título+cantante+duración) ignorando mayúsculas en cadenas
        return durationMinutes == s.durationMinutes
                && title.equalsIgnoreCase(s.title)
                && singer.equalsIgnoreCase(s.singer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), singer.toLowerCase(), durationMinutes);
    }

    @Override
    public String toString() {
        return title + " (" + durationMinutes + "m) - " + singer + " [" + genre + "]";
    }
}
