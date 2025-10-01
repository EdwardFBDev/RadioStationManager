package radio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 *
 *
  class RadioStation 
    - String name
    - String frequency
    - String url
    - ListAnnouncer> announcers
    - ListProgram> programs
    - ListDisc> discs
    + addAnnouncer(a: Announcer)
    + addProgram(p: Program)
    + addDisc(d: Disc)
    + getAllSongs(): ListSong>
  
 * @author funes
 */
public class RadioStation {
    private String name;
    private String frequency;
    private String url;

    private final List<Announcer> announcers = new ArrayList<>();
    private final List<Program> programs = new ArrayList<>();
    private final List<Disc> discs = new ArrayList<>();

    private static String req(String v, String field) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return v.trim();
    }

    /**
     * Crea la radioemisora.
     */
    public RadioStation(String name, String frequency, String url) {
        this.name = req(name, "name");
        this.frequency = req(frequency, "frequency");
        this.url = req(url, "url");
    }

    public String getName() { return name; }
    public String getFrequency() { return frequency; }
    public String getUrl() { return url; }

    public void setName(String name) { this.name = req(name, "name"); }
    public void setFrequency(String frequency) { this.frequency = req(frequency, "frequency"); }
    public void setUrl(String url) { this.url = req(url, "url"); }

    /**
     * Agrega un locutor evitando duplicados.
     */
    public void addAnnouncer(Announcer a) {
        if (a == null) throw new IllegalArgumentException("announcer is required");
        if (!announcers.contains(a)) announcers.add(a);
    }

    /**
     * Agrega un programa evitando duplicados por nombre.
     */
    public void addProgram(Program p) {
        if (p == null) throw new IllegalArgumentException("program is required");
        boolean dup = programs.stream()
                .anyMatch(x -> x.getName().equalsIgnoreCase(p.getName()));
        if (!dup) programs.add(p);
    }

    /**
     * Agrega un disco evitando duplicados por igualdad de Disc.
     */
    public void addDisc(Disc d) {
        if (d == null) throw new IllegalArgumentException("disc is required");
        if (!discs.contains(d)) discs.add(d);
    }

    public List<Announcer> getAnnouncers() { return Collections.unmodifiableList(announcers); }
    public List<Program>   getPrograms()   { return Collections.unmodifiableList(programs); }
    public List<Disc>      getDiscs()      { return Collections.unmodifiableList(discs); }

    /**
     * Retorna todas las canciones de todos los discos registrados.
     */
    public List<Song> allSongs() {
        List<Song> all = new ArrayList<>();
        for (Disc d : discs) {
            all.addAll(d.getSongs());
        }
        return all;
    }

    @Override
    public String toString() {
        return name + " (" + frequency + ") - " + url;
    }
}
