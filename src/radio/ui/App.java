package radio.ui;

import radio.service.GenreService;
import radio.service.PlaylistGenerator;
import radio.service.RadioManagerService;

/**
 *
 * @author funes
 */
public class App {
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
      GenreService genreSvc = new GenreService();
      PlaylistGenerator gen = new PlaylistGenerator();
      RadioManagerService svc = new RadioManagerService(genreSvc, gen);
      new RadioManagerFrame(genreSvc, gen, svc).setVisible(true);
    });
  }
}
