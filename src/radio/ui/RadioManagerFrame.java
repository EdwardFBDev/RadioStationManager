package radio.ui;

import javax.swing.JPanel;
import radio.service.GenreService;
import radio.service.PlaylistGenerator;
import radio.service.RadioManagerService;


/**
 *
 * @author funes
 */
public class RadioManagerFrame extends javax.swing.JFrame {
    
    private final GenreService genreSvc;
    private final PlaylistGenerator generator ;
    private final RadioManagerService svc;
 
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RadioManagerFrame.class.getName());
    
    
    // Cache de paneles para no recrearlos cada vez
    private RadioPanel radioPanel;
    private AnnouncersPanel announcersPanel;
    private ProgramsPanel programsPanel;
    private DiscsAndSongsPanel discsAndSongsPanel;
    private PlaylistPanel playlistPanel;
    private QueriesPanel queriesPanel;

    // Botón activo para resaltar navegación
    private javax.swing.JButton activeButton = null;
    
    public RadioManagerFrame(GenreService genreSvc, PlaylistGenerator generator, RadioManagerService svc) {
        
        this.genreSvc = genreSvc;
        this.generator = generator;
        this.svc = svc;

        initComponents();

        content.setLayout(new java.awt.BorderLayout());

        // Muestra Radio de entrada
        RadioPanel rp = new RadioPanel();
        rp.bindServices(genreSvc, generator, svc);
        ShowPanal(rp);

        setActiveButton(btnRadio);
        lblTitle.setText("Radio Station");

        // Texto inicial del label 
        lblRadioInfo.setText("No radio saved yet");

        // Enlaces de navegación
        btnRadio.addActionListener(e -> showPage("RADIO", btnRadio, "Radio Station"));
        btnAnnouncers.addActionListener(e -> showPage("ANN", btnAnnouncers, "Announcers"));
        btnPrograms.addActionListener(e -> showPage("PROG", btnPrograms, "Programs"));
        btnDiscsAndSongs.addActionListener(e -> showPage("DISC", btnDiscsAndSongs, "Discs & Songs"));
        btnPlaylist.addActionListener(e -> showPage("PLAY", btnPlaylist, "Playlist"));
        btnQueries.addActionListener(e -> showPage("QUERY", btnQueries, "Queries"));

    
  }
    
    
    
    // Crea/bindea el panel si aún no existe
    private javax.swing.JPanel getOrCreatePanel(String key) {
        switch (key) {
            case "RADIO":
                if (radioPanel == null) {
                    radioPanel = new RadioPanel();
                    radioPanel.bindServices(genreSvc, generator, svc);
                }
                return radioPanel;
            case "ANN":
                if (announcersPanel == null) {
                    announcersPanel = new AnnouncersPanel();
                    announcersPanel.bindServices(genreSvc, generator, svc);
                }
                return announcersPanel;
            case "PROG":
                if (programsPanel == null) {
                    programsPanel = new ProgramsPanel();
                    programsPanel.bindServices(genreSvc, generator, svc);
                }
                return programsPanel;
            case "DISC":
                if (discsAndSongsPanel == null) {
                    discsAndSongsPanel = new DiscsAndSongsPanel();
                    discsAndSongsPanel.bindServices(genreSvc, generator, svc);
                }
                return discsAndSongsPanel;
            case "PLAY":
                if (playlistPanel == null) {
                    playlistPanel = new PlaylistPanel();
                    playlistPanel.bindServices(genreSvc, generator, svc);
                }
                return playlistPanel;
            case "QUERY":
                if (queriesPanel == null) {
                    queriesPanel = new QueriesPanel();
                    queriesPanel.bindServices(genreSvc, generator, svc);
                }
                return queriesPanel;
            default:
                return new javax.swing.JPanel(); // fallback
        }
    }
    
    public void updateRadioInfo(String name, String freq) {
        if (lblRadioInfo != null) {
                lblRadioInfo.setText("Station: " + name + " — " + freq);
                
                jPanel1.revalidate();
                jPanel1.repaint();
            }
    }

    // Cambia el contenido y actualiza el título + botón activo
    private void showPage(String key, javax.swing.JButton sourceButton, String titleText) {
        
        javax.swing.JPanel panel = getOrCreatePanel(key);
        ShowPanal(panel);
        lblTitle.setText(titleText);
        setActiveButton(sourceButton);
    }

    private void setActiveButton(javax.swing.JButton btn) {
        if (activeButton != null) {
            // Restaurar estilo del anterior
            activeButton.setBackground(new java.awt.Color(71, 98, 91));
        }
        activeButton = btn;
        if (activeButton != null) {
            // Resaltar activo (mismo tono base pero más claro)
            activeButton.setBackground(new java.awt.Color(91, 118, 111));
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPanleBackground = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnRadio = new javax.swing.JButton();
        btnAnnouncers = new javax.swing.JButton();
        btnPrograms = new javax.swing.JButton();
        btnDiscsAndSongs = new javax.swing.JButton();
        btnPlaylist = new javax.swing.JButton();
        btnQueries = new javax.swing.JButton();
        lblTitle1 = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
        pnlHead = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblRadioInfo = new javax.swing.JLabel();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlPanleBackground.setBackground(new java.awt.Color(203, 222, 220));

        pnlMenu.setBackground(new java.awt.Color(71, 98, 91));
        pnlMenu.setPreferredSize(new java.awt.Dimension(160, 448));

        btnRadio.setBackground(new java.awt.Color(71, 98, 91));
        btnRadio.setForeground(new java.awt.Color(255, 255, 255));
        btnRadio.setText("Radio");
        btnRadio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRadio.setBorderPainted(false);
        btnRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRadioMouseClicked(evt);
            }
        });
        btnRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadioActionPerformed(evt);
            }
        });

        btnAnnouncers.setBackground(new java.awt.Color(71, 98, 91));
        btnAnnouncers.setForeground(new java.awt.Color(255, 255, 255));
        btnAnnouncers.setText("Announcers");
        btnAnnouncers.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnnouncers.setBorderPainted(false);
        btnAnnouncers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnnouncersMouseClicked(evt);
            }
        });

        btnPrograms.setBackground(new java.awt.Color(71, 98, 91));
        btnPrograms.setForeground(new java.awt.Color(255, 255, 255));
        btnPrograms.setText("Programs");
        btnPrograms.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrograms.setBorderPainted(false);
        btnPrograms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProgramsMouseClicked(evt);
            }
        });

        btnDiscsAndSongs.setBackground(new java.awt.Color(71, 98, 91));
        btnDiscsAndSongs.setForeground(new java.awt.Color(255, 255, 255));
        btnDiscsAndSongs.setText("Discs & Songs");
        btnDiscsAndSongs.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDiscsAndSongs.setBorderPainted(false);
        btnDiscsAndSongs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDiscsAndSongsMouseClicked(evt);
            }
        });

        btnPlaylist.setBackground(new java.awt.Color(71, 98, 91));
        btnPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        btnPlaylist.setText("Playlist");
        btnPlaylist.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPlaylist.setBorderPainted(false);
        btnPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlaylistMouseClicked(evt);
            }
        });

        btnQueries.setBackground(new java.awt.Color(71, 98, 91));
        btnQueries.setForeground(new java.awt.Color(255, 255, 255));
        btnQueries.setText("Queries");
        btnQueries.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnQueries.setBorderPainted(false);
        btnQueries.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQueriesMouseClicked(evt);
            }
        });
        btnQueries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueriesActionPerformed(evt);
            }
        });

        lblTitle1.setFont(new java.awt.Font("Lucida Handwriting", 1, 14)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(186, 178, 130));
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText("Radio Manage");

        lblTitle2.setFont(new java.awt.Font("Lucida Handwriting", 1, 14)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(186, 178, 130));
        lblTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle2.setText("Radio Station");

        lblTitle3.setFont(new java.awt.Font("Lucida Handwriting", 1, 14)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(186, 178, 130));
        lblTitle3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle3.setText("Radio Service");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrograms, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRadio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAnnouncers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDiscsAndSongs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPlaylist, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnQueries, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAnnouncers)
                .addGap(18, 18, 18)
                .addComponent(lblTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrograms)
                .addGap(18, 18, 18)
                .addComponent(btnDiscsAndSongs)
                .addGap(18, 18, 18)
                .addComponent(btnPlaylist)
                .addGap(18, 18, 18)
                .addComponent(lblTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQueries)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        pnlHead.setBackground(new java.awt.Color(61, 82, 80));
        pnlHead.setPreferredSize(new java.awt.Dimension(160, 140));

        lblTitle.setFont(new java.awt.Font("Lucida Handwriting", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(186, 178, 130));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Radio Station");

        javax.swing.GroupLayout pnlHeadLayout = new javax.swing.GroupLayout(pnlHead);
        pnlHead.setLayout(pnlHeadLayout);
        pnlHeadLayout.setHorizontalGroup(
            pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
        );
        pnlHeadLayout.setVerticalGroup(
            pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(35, 35, 35))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblRadioInfo.setFont(new java.awt.Font("Lucida Handwriting", 1, 36)); // NOI18N
        lblRadioInfo.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblRadioInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRadioInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlPanleBackgroundLayout = new javax.swing.GroupLayout(pnlPanleBackground);
        pnlPanleBackground.setLayout(pnlPanleBackgroundLayout);
        pnlPanleBackgroundLayout.setHorizontalGroup(
            pnlPanleBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPanleBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPanleBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(pnlHead, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPanleBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlPanleBackgroundLayout.setVerticalGroup(
            pnlPanleBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPanleBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPanleBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlPanleBackgroundLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 42, Short.MAX_VALUE))
                    .addGroup(pnlPanleBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPanleBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPanleBackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ShowPanal(JPanel p){
        content.removeAll();
        content.setLayout(new java.awt.BorderLayout()); 
        content.add(p, java.awt.BorderLayout.CENTER);  
        content.revalidate();
        content.repaint();
    }
    
    private void btnRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRadioMouseClicked
        showPage("RADIO", btnRadio, "Radio Station");
    }//GEN-LAST:event_btnRadioMouseClicked
    // copiar para todos los demas
    private void btnAnnouncersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnnouncersMouseClicked
        showPage("ANN", btnAnnouncers, "Announcers");
    }//GEN-LAST:event_btnAnnouncersMouseClicked

    private void btnProgramsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProgramsMouseClicked
        showPage("PROG", btnPrograms, "Programs");
    }//GEN-LAST:event_btnProgramsMouseClicked

    private void btnDiscsAndSongsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDiscsAndSongsMouseClicked
        showPage("DISC", btnDiscsAndSongs, "Discs & Songs");
    }//GEN-LAST:event_btnDiscsAndSongsMouseClicked

    private void btnRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadioActionPerformed
        
    }//GEN-LAST:event_btnRadioActionPerformed

    private void btnQueriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnQueriesActionPerformed

    private void btnPlaylistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlaylistMouseClicked
        showPage("PLAY", btnPlaylist, "Playlist");
    }//GEN-LAST:event_btnPlaylistMouseClicked

    private void btnQueriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQueriesMouseClicked
        showPage("QUERY", btnQueries, "Queries");
    }//GEN-LAST:event_btnQueriesMouseClicked
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnnouncers;
    private javax.swing.JButton btnDiscsAndSongs;
    private javax.swing.JButton btnPlaylist;
    private javax.swing.JButton btnPrograms;
    private javax.swing.JButton btnQueries;
    private javax.swing.JButton btnRadio;
    private javax.swing.JPanel content;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblRadioInfo;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JPanel pnlHead;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlPanleBackground;
    // End of variables declaration//GEN-END:variables
}
