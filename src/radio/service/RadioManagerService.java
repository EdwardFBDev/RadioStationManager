package radio.service;

import radio.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Fachada de casos de uso para gestionar la radioemisora en memoria.
 * <p>
 * Expone operaciones para:
 * <ul>
 *     <li>Crear la estación</li>
 *     <li>Registrar locutores, programas, discos y canciones</li>
 *     <li>Listar canciones y programas</li>
 *     <li>Generar y asignar playlists válidos</li>
 * </ul>
 */
public class RadioManagerService {

    private final GenreService genreService;
    private final PlaylistGenerator playlistGenerator;

    private RadioStation station;

    public RadioManagerService(GenreService genreService, PlaylistGenerator playlistGenerator) {
        this.genreService = Objects.requireNonNull(genreService, "genreService is required");
        this.playlistGenerator = Objects.requireNonNull(playlistGenerator, "playlistGenerator is required");
    }

    // -------- Estación --------

    /**
     * Crea la radioemisora en memoria (solo una instancia).
     */
    public void createStation(String name, String frequency, String url) {
        this.station = new RadioStation(req(name, "name"), req(frequency, "frequency"), req(url, "url"));
    }

    /**
     * Retorna la estación creada.
     */
    public RadioStation getStation() {
        ensureStation();
        return station;
    }

    // -------- Registro de entidades --------

    /**
     * Registra un locutor (todos los datos requeridos).
     */
    public Announcer registerAnnouncer(String id, String fullName, String email, String phoneNumber) {
        ensureStation();
        Announcer a = new Announcer(req(id, "id"), req(fullName, "fullName"),
                req(email, "email"), req(phoneNumber, "phoneNumber"));

        // Evitar duplicado por equals(id)
        if (!station.getAnnouncers().contains(a)) {
            station.addAnnouncer(a);
        } else {
            throw new IllegalArgumentException("Announcer with id " + id + " already exists");
        }
        return a;
    }

    /**
     * Registra un programa asociado a un locutor existente.
     */
    public Program registerProgram(String name, String schedule, int durationMinutes, String genre, Announcer announcer) {
        ensureStation();
        if (announcer == null) throw new IllegalArgumentException("announcer is required");

        String g = genreService.ensure(req(genre, "genre"));
        Program p = new Program(req(name, "name"), req(schedule, "schedule"),
                pos(durationMinutes, "durationMinutes"), g, announcer);

        // Evitar duplicado por nombre (equals de Program)
        if (!station.getPrograms().contains(p)) {
            station.addProgram(p);
        } else {
            throw new IllegalArgumentException("Program with name '" + name + "' already exists");
        }
        return p;
    }

    /**
     * Registra un disco.
     */
    public Disc registerDisc(String title, String singer, String genre, String coverImagePath) {
        ensureStation();
        String g = genreService.ensure(req(genre, "genre"));
        Disc d = new Disc(req(title, "title"), req(singer, "singer"), g, coverImagePath);
        if (!station.getDiscs().contains(d)) {
            station.addDisc(d);
        } else {
            throw new IllegalArgumentException("Disc '" + title + "' by '" + singer + "' already exists");
        }
        return d;
    }

    /**
     * Agrega una canción a un disco ya registrado.
     */
    public Song addSongToDisc(Disc disc, String title, int durationMinutes, String singer, String genre) {
        ensureStation();
        if (disc == null) throw new IllegalArgumentException("disc is required");

        String g = genreService.ensure(req(genre, "genre"));
        Song s = new Song(req(title, "title"), pos(durationMinutes, "durationMinutes"), req(singer, "singer"), g);
        disc.addSong(s);
        return s;
    }

    // -------- Consultas --------

    /**
     * Lista todas las canciones de todos los discos.
     */
    public List<Song> listAllSongs() {
        ensureStation();
        return station.allSongs();
    }

    /**
     * Lista canciones por género (normalizado).
     */
    public List<Song> listSongsByGenre(String genre) {
        ensureStation();
        String g = genreService.normalize(req(genre, "genre"));
        List<Song> out = new ArrayList<>();
        for (Song s : station.allSongs()) {
            if (s.getGenre().equalsIgnoreCase(g)) out.add(s);
        }
        return Collections.unmodifiableList(out);
    }

    /**
     * Lista todos los programas.
     */
    public List<Program> listPrograms() {
        ensureStation();
        return station.getPrograms();
    }

    /**
     * Lista programas que aún no tienen playlist asignado.
     */
    public List<Program> listProgramsWithoutPlaylist() {
        ensureStation();
        List<Program> out = new ArrayList<>();
        for (Program p : station.getPrograms()) {
            if (p.getPlaylist().isEmpty()) out.add(p);
        }
        return Collections.unmodifiableList(out);
    }

    // -------- Playlists --------

    /**
     * Genera un playlist válido para el programa y lo asigna.
     * <ul>
     *     <li>Filtra por género del programa</li>
     *     <li>La suma de minutos debe ser estrictamente menor a la duración del programa</li>
     * </ul>
     *
     * @throws IllegalStateException si no es posible generar un playlist válido
     */
    public Playlist generateAndAssignPlaylist(Program program) {
        ensureStation();
        if (program == null) throw new IllegalArgumentException("program is required");

        List<Song> pool = station.allSongs();
        List<Song> selection = playlistGenerator.generate(pool, program);

        if (selection.isEmpty()) {
            // No hay canciones de ese género o todas son demasiado largas
            throw new IllegalStateException("No songs available to build a valid playlist for program '" + program.getName() + "'");
        }

        Playlist pl = new Playlist(program, selection); // Validará género y duración < program
        program.setPlaylist(pl);
        return pl;
    }

    // -------- Helpers --------

    private void ensureStation() {
        if (station == null) throw new IllegalStateException("RadioStation has not been created");
    }

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
}

