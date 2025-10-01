package radio.ui;

import radio.service.GenreService;
import radio.service.PlaylistGenerator;
import radio.service.RadioManagerService;

import radio.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author funes
 */
public class DiscsAndSongsPanel extends javax.swing.JPanel {

    private GenreService genreSvc;
    private PlaylistGenerator generator;
    private RadioManagerService svc;

    private DefaultTableModel discsModel;
    private DefaultTableModel songsModel;
    private javax.swing.JLabel lblCoverPreview;
 
    private static String req(String v, String field) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException(field + " is required");
        return v.trim();
    }
    private static int pos(int m, String field) {
        if (m <= 0) throw new IllegalArgumentException(field + " must be > 0");
        return m;
    }
    private static int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { throw new IllegalArgumentException("minutes must be a positive integer"); }
    }
    
    public void bindServices(GenreService genreSvc, PlaylistGenerator generator, RadioManagerService svc) {
        this.genreSvc = genreSvc;
        this.generator = generator;
        this.svc = svc;

        if (lblCoverPreview == null) {
            lblCoverPreview = new javax.swing.JLabel("", javax.swing.SwingConstants.CENTER);
            pnlPreviewCoverDnS.setLayout(new java.awt.BorderLayout());
            pnlPreviewCoverDnS.add(lblCoverPreview, java.awt.BorderLayout.CENTER);
        }
        
        setupTables();  
        refreshDiscsTable();
        
    }
    
    
    public DiscsAndSongsPanel() {
        initComponents();
    }
    
    private void setupTables() {
        discsModel = new DefaultTableModel(new Object[]{"Title","Singer","Genre","#Songs"}, 0){ @Override public boolean isCellEditable(int r,int c){ return false; } };
        songsModel = new DefaultTableModel(new Object[]{"Title","Min","Singer","Genre"}, 0){ @Override public boolean isCellEditable(int r,int c){ return false; } };
        TableBoxDiscsnS.setModel(discsModel);
        TableBoxDnSongs.setModel(songsModel);
    }
        

    
    private radio.model.Disc getSelectedDisc() {
        int row = TableBoxDiscsnS.getSelectedRow();
        if (row < 0) return null;

        String title  = TableBoxDiscsnS.getValueAt(row, 0).toString();
        String singer = TableBoxDiscsnS.getValueAt(row, 1).toString();

        for (radio.model.Disc d : svc.getStation().getDiscs()) {
            if (d.getTitle().equalsIgnoreCase(title) && d.getSinger().equalsIgnoreCase(singer)) {
                return d;
            }
        }
        return null;
    }
    
    private void refreshDiscsTable() {
        String[] cols = {"Title", "Singer", "Genre", "Cover"};
        javax.swing.table.DefaultTableModel m = new javax.swing.table.DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (radio.model.Disc d : svc.getStation().getDiscs()) {
            m.addRow(new Object[]{ d.getTitle(), d.getSinger(), d.getGenre(), d.getCoverImagePath() });
        }
        TableBoxDiscsnS.setModel(m);
}
    
    private void refreshSongsTable(radio.model.Disc disc) {
        String[] cols = {"Title", "Minutes", "Singer", "Genre"};
        javax.swing.table.DefaultTableModel m = new javax.swing.table.DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        if (disc != null) {
            for (radio.model.Song s : disc.getSongs()) {
                m.addRow(new Object[]{ s.getTitle(), s.getDurationMinutes(), s.getSinger(), s.getGenre() });
            }
        }
        TableBoxDnSongs.setModel(m); 
    }

    private void refreshGenresComboSimple() {
        javax.swing.DefaultComboBoxModel<String> modelDisc = new javax.swing.DefaultComboBoxModel<>();
        javax.swing.DefaultComboBoxModel<String> modelSong = new javax.swing.DefaultComboBoxModel<>();

        for (String g : genreSvc.listAll()) {
            modelDisc.addElement(g);
            modelSong.addElement(g);
        }
        modelDisc.addElement("OTHER");
        modelSong.addElement("OTHER");

        cbxDiscsnS.setModel(modelDisc);
        cbxDnSongs.setModel(modelSong);
    }

    private void attachDiscsSelectionListener() {
        TableBoxDiscsnS.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            radio.model.Disc d = getSelectedDisc();
            refreshSongsTable(d);
            String cover = (d != null) ? d.getCoverImagePath() : null;
            if (cover == null || cover.isBlank()) {
                lblCoverPreview.setIcon(null);
                lblCoverPreview.setText("No cover");
            } else {
                loadCoverPreview(cover);
            }
        }
        });
    }
    
    private void attachNumericFilterToMinutes() {
        txfBoxMinutesDnSongs.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE
                     && c != java.awt.event.KeyEvent.VK_DELETE) {
                    evt.consume();
                }
            }
        });
    }

    private void loadCoverPreview(String path) {
        if (lblCoverPreview == null) return;
        if (path == null || path.isBlank()) {
            lblCoverPreview.setIcon(null);
            lblCoverPreview.setText("No cover");
            return;
        }
        java.awt.Image img = new javax.swing.ImageIcon(path).getImage();
        java.awt.Image scaled = img.getScaledInstance(
            pnlPreviewCoverDnS.getWidth(), pnlPreviewCoverDnS.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
        lblCoverPreview.setText("");
        lblCoverPreview.setIcon(new javax.swing.ImageIcon(scaled));
    }

    private void clearDiscForm() {
        txfBoxTitleDiscsnS.setText("");
        txfBoxSingerDiscsnS.setText("");
        txfBoxCoverDiscsnS.setText("");
        if (lblCoverPreview != null) {        
            lblCoverPreview.setIcon(null);
            lblCoverPreview.setText("No cover");
        }
        if (cbxDiscsnS.getItemCount() > 0) cbxDiscsnS.setSelectedIndex(0);
        if (btnOthreDiscsnS != null) btnOthreDiscsnS.setText("");
    }

    private void clearSongForm() {
        txfBoxTitleDnSongs.setText("");
        txfBoxSingerDnSongs.setText("");
        txfBoxMinutesDnSongs.setText("");
        cbxDnSongs.setSelectedIndex(0);
        if (btnOthreDnSongs != null) btnOthreDnSongs.setText("");
    }
    
    private void selectDiscInTable(radio.model.Disc d) {
        if (d == null) return;
        javax.swing.table.TableModel m = TableBoxDiscsnS.getModel();
        for (int i = 0; i < m.getRowCount(); i++) {
            String tt = m.getValueAt(i, 0).toString();
            String ss = m.getValueAt(i, 1).toString();
            if (tt.equalsIgnoreCase(d.getTitle()) && ss.equalsIgnoreCase(d.getSinger())) {
                TableBoxDiscsnS.setRowSelectionInterval(i, i);
                TableBoxDiscsnS.scrollRectToVisible(TableBoxDiscsnS.getCellRect(i, 0, true));
                break;
            }
        }
    }
    
    
    public DiscsAndSongsPanel(radio.service.RadioManagerService service, radio.service.GenreService genreService) {
        this.svc = service;
        this.genreSvc = genreService;
        initComponents();
        postInit();
    }
    
    private void postInit() {
        // 0) Crear y montar el label del preview ANTES de usarlo
        lblCoverPreview = new javax.swing.JLabel("", javax.swing.SwingConstants.CENTER);
        pnlPreviewCoverDnS.setLayout(new java.awt.BorderLayout());
        pnlPreviewCoverDnS.add(lblCoverPreview, java.awt.BorderLayout.CENTER);

        // 1) Poblar combos
        refreshGenresComboSimple();

        // 2) Cargar tabla de Discos y limpiar formulario
        refreshDiscsTable();
        clearDiscForm();

        // 3) Tabla canciones vacía y limpiar form
        refreshSongsTable(null);
        clearSongForm();

        // 4) Filtro numérico
        attachNumericFilterToMinutes();

        // 5) Listener de selección
        attachDiscsSelectionListener();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDnSPanel = new javax.swing.JPanel();
        lblTitleDnS = new javax.swing.JLabel();
        pnlSettingDiscsnS = new javax.swing.JPanel();
        lblTitleDiscsnS = new javax.swing.JLabel();
        lblSingerDiscsnS = new javax.swing.JLabel();
        txfBoxTitleDiscsnS = new javax.swing.JTextField();
        txfBoxSingerDiscsnS = new javax.swing.JTextField();
        lblGenreDiscsnS = new javax.swing.JLabel();
        lblCoverDiscsnS = new javax.swing.JLabel();
        cbxDiscsnS = new javax.swing.JComboBox<>();
        btnOthreDiscsnS = new javax.swing.JButton();
        txfBoxCoverDiscsnS = new javax.swing.JTextField();
        btnBrowseDiscsnS = new javax.swing.JButton();
        btnCrearDiscsnS = new javax.swing.JButton();
        btnAddDiscsnS1 = new javax.swing.JButton();
        pnlTableDiscsnS = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBoxDiscsnS = new javax.swing.JTable();
        pnlSettingDnSongs = new javax.swing.JPanel();
        lblTitleDnSongs = new javax.swing.JLabel();
        txfBoxTitleDnSongs = new javax.swing.JTextField();
        lblMinutesDnSongs = new javax.swing.JLabel();
        lblsingerDnSongs = new javax.swing.JLabel();
        txfBoxMinutesDnSongs = new javax.swing.JTextField();
        lblGenreDnSongs = new javax.swing.JLabel();
        cbxDnSongs = new javax.swing.JComboBox<>();
        btnOthreDnSongs = new javax.swing.JButton();
        txfBoxSingerDnSongs = new javax.swing.JTextField();
        btnAddDnSongs = new javax.swing.JButton();
        btnRemoveSongDnSongs = new javax.swing.JButton();
        pnlPreviewCoverDnS = new javax.swing.JPanel();
        pnlTableDnSongs = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableBoxDnSongs = new javax.swing.JTable();

        pnlDnSPanel.setBackground(new java.awt.Color(77, 98, 91));

        lblTitleDnS.setBackground(new java.awt.Color(168, 178, 130));
        lblTitleDnS.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTitleDnS.setForeground(new java.awt.Color(168, 178, 130));
        lblTitleDnS.setText("Discs & Songs");

        pnlSettingDiscsnS.setBackground(new java.awt.Color(245, 247, 246));
        pnlSettingDiscsnS.setBorder(new javax.swing.border.MatteBorder(null));

        lblTitleDiscsnS.setForeground(new java.awt.Color(60, 63, 65));
        lblTitleDiscsnS.setText("Title Disc:");

        lblSingerDiscsnS.setForeground(new java.awt.Color(60, 63, 65));
        lblSingerDiscsnS.setText("Singer:");

        txfBoxTitleDiscsnS.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxTitleDiscsnS.setText("Title...");
        txfBoxTitleDiscsnS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxTitleDiscsnS.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxTitleDiscsnS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxTitleDiscsnSActionPerformed(evt);
            }
        });

        txfBoxSingerDiscsnS.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxSingerDiscsnS.setText("Singer...");
        txfBoxSingerDiscsnS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxSingerDiscsnS.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxSingerDiscsnS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxSingerDiscsnSActionPerformed(evt);
            }
        });

        lblGenreDiscsnS.setForeground(new java.awt.Color(60, 63, 65));
        lblGenreDiscsnS.setText("Genre:");

        lblCoverDiscsnS.setForeground(new java.awt.Color(60, 63, 65));
        lblCoverDiscsnS.setText("Cover (path):");

        cbxDiscsnS.setBackground(new java.awt.Color(245, 247, 246));
        cbxDiscsnS.setEditable(true);
        cbxDiscsnS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Música Clásica", "Blues", "Jazz", "Soul", "R&B", "Rock", "Pop", "Hip Hop", "Country", "Música Electrónica", "Reggae", "Disco", "Salsa", "Cumbia", "Bachata", "Reguetón" }));

        btnOthreDiscsnS.setBackground(new java.awt.Color(168, 178, 130));
        btnOthreDiscsnS.setForeground(new java.awt.Color(255, 255, 255));
        btnOthreDiscsnS.setText("Other..");
        btnOthreDiscsnS.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOthreDiscsnS.setBorderPainted(false);
        btnOthreDiscsnS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOthreDiscsnSActionPerformed(evt);
            }
        });

        txfBoxCoverDiscsnS.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxCoverDiscsnS.setText("Path...");
        txfBoxCoverDiscsnS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxCoverDiscsnS.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxCoverDiscsnS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxCoverDiscsnSActionPerformed(evt);
            }
        });

        btnBrowseDiscsnS.setBackground(new java.awt.Color(168, 178, 130));
        btnBrowseDiscsnS.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowseDiscsnS.setText("Browse");
        btnBrowseDiscsnS.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBrowseDiscsnS.setBorderPainted(false);
        btnBrowseDiscsnS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseDiscsnSActionPerformed(evt);
            }
        });

        btnCrearDiscsnS.setBackground(new java.awt.Color(168, 178, 130));
        btnCrearDiscsnS.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearDiscsnS.setText("Clear");
        btnCrearDiscsnS.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCrearDiscsnS.setBorderPainted(false);
        btnCrearDiscsnS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearDiscsnSActionPerformed(evt);
            }
        });

        btnAddDiscsnS1.setBackground(new java.awt.Color(168, 178, 130));
        btnAddDiscsnS1.setForeground(new java.awt.Color(255, 255, 255));
        btnAddDiscsnS1.setText("Add Discs");
        btnAddDiscsnS1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddDiscsnS1.setBorderPainted(false);
        btnAddDiscsnS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDiscsnS1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSettingDiscsnSLayout = new javax.swing.GroupLayout(pnlSettingDiscsnS);
        pnlSettingDiscsnS.setLayout(pnlSettingDiscsnSLayout);
        pnlSettingDiscsnSLayout.setHorizontalGroup(
            pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingDiscsnSLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSettingDiscsnSLayout.createSequentialGroup()
                        .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitleDiscsnS)
                            .addComponent(lblSingerDiscsnS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfBoxTitleDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfBoxSingerDiscsnS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlSettingDiscsnSLayout.createSequentialGroup()
                        .addComponent(btnAddDiscsnS1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrearDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCoverDiscsnS)
                    .addComponent(lblGenreDiscsnS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxDiscsnS, 0, 149, Short.MAX_VALUE)
                    .addComponent(txfBoxCoverDiscsnS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOthreDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseDiscsnS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlSettingDiscsnSLayout.setVerticalGroup(
            pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingDiscsnSLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitleDiscsnS)
                    .addComponent(txfBoxTitleDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenreDiscsnS)
                    .addComponent(cbxDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOthreDiscsnS))
                .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSettingDiscsnSLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCoverDiscsnS)
                            .addComponent(txfBoxCoverDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBrowseDiscsnS)))
                    .addGroup(pnlSettingDiscsnSLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSingerDiscsnS)
                            .addComponent(txfBoxSingerDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlSettingDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddDiscsnS1)
                            .addComponent(btnCrearDiscsnS))))
                .addContainerGap())
        );

        pnlTableDiscsnS.setBackground(new java.awt.Color(245, 247, 246));
        pnlTableDiscsnS.setBorder(new javax.swing.border.MatteBorder(null));

        TableBoxDiscsnS.setBackground(new java.awt.Color(245, 247, 246));
        TableBoxDiscsnS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title", "Singer", "Genre", "#Songs"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableBoxDiscsnS.setFocusable(false);
        TableBoxDiscsnS.setIntercellSpacing(new java.awt.Dimension(0, 0));
        TableBoxDiscsnS.setSelectionBackground(new java.awt.Color(245, 247, 246));
        TableBoxDiscsnS.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(TableBoxDiscsnS);

        javax.swing.GroupLayout pnlTableDiscsnSLayout = new javax.swing.GroupLayout(pnlTableDiscsnS);
        pnlTableDiscsnS.setLayout(pnlTableDiscsnSLayout);
        pnlTableDiscsnSLayout.setHorizontalGroup(
            pnlTableDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );
        pnlTableDiscsnSLayout.setVerticalGroup(
            pnlTableDiscsnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        pnlSettingDnSongs.setBackground(new java.awt.Color(245, 247, 246));
        pnlSettingDnSongs.setBorder(new javax.swing.border.MatteBorder(null));

        lblTitleDnSongs.setForeground(new java.awt.Color(60, 63, 65));
        lblTitleDnSongs.setText("Title Song:");

        txfBoxTitleDnSongs.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxTitleDnSongs.setText("Title...");
        txfBoxTitleDnSongs.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxTitleDnSongs.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxTitleDnSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxTitleDnSongsActionPerformed(evt);
            }
        });

        lblMinutesDnSongs.setForeground(new java.awt.Color(60, 63, 65));
        lblMinutesDnSongs.setText("Minutes:");

        lblsingerDnSongs.setForeground(new java.awt.Color(60, 63, 65));
        lblsingerDnSongs.setText("Singer: ");

        txfBoxMinutesDnSongs.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxMinutesDnSongs.setText("min");
        txfBoxMinutesDnSongs.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxMinutesDnSongs.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxMinutesDnSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxMinutesDnSongsActionPerformed(evt);
            }
        });

        lblGenreDnSongs.setForeground(new java.awt.Color(60, 63, 65));
        lblGenreDnSongs.setText("Genre:");

        cbxDnSongs.setBackground(new java.awt.Color(245, 247, 246));
        cbxDnSongs.setEditable(true);
        cbxDnSongs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Música Clásica", "Blues", "Jazz", "Soul", "R&B", "Rock", "Pop", "Hip Hop", "Country", "Música Electrónica", "Reggae", "Disco", "Salsa", "Cumbia", "Bachata", "Reguetón" }));

        btnOthreDnSongs.setBackground(new java.awt.Color(168, 178, 130));
        btnOthreDnSongs.setForeground(new java.awt.Color(255, 255, 255));
        btnOthreDnSongs.setText("Other..");
        btnOthreDnSongs.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOthreDnSongs.setBorderPainted(false);
        btnOthreDnSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOthreDnSongsActionPerformed(evt);
            }
        });

        txfBoxSingerDnSongs.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxSingerDnSongs.setText("Singer...");
        txfBoxSingerDnSongs.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxSingerDnSongs.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxSingerDnSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxSingerDnSongsActionPerformed(evt);
            }
        });

        btnAddDnSongs.setBackground(new java.awt.Color(168, 178, 130));
        btnAddDnSongs.setForeground(new java.awt.Color(255, 255, 255));
        btnAddDnSongs.setText("Add Song");
        btnAddDnSongs.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddDnSongs.setBorderPainted(false);
        btnAddDnSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDnSongsActionPerformed(evt);
            }
        });

        btnRemoveSongDnSongs.setBackground(new java.awt.Color(168, 178, 130));
        btnRemoveSongDnSongs.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveSongDnSongs.setText("Remove Song");
        btnRemoveSongDnSongs.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRemoveSongDnSongs.setBorderPainted(false);
        btnRemoveSongDnSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSongDnSongsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSettingDnSongsLayout = new javax.swing.GroupLayout(pnlSettingDnSongs);
        pnlSettingDnSongs.setLayout(pnlSettingDnSongsLayout);
        pnlSettingDnSongsLayout.setHorizontalGroup(
            pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                        .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                                .addComponent(lblGenreDnSongs)
                                .addGap(27, 27, 27)
                                .addComponent(cbxDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                                .addComponent(lblsingerDnSongs)
                                .addGap(22, 22, 22)
                                .addComponent(txfBoxSingerDnSongs)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOthreDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                        .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                                .addComponent(lblMinutesDnSongs)
                                .addGap(18, 18, 18)
                                .addComponent(txfBoxMinutesDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                                .addComponent(lblTitleDnSongs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txfBoxTitleDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoveSongDnSongs))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlSettingDnSongsLayout.setVerticalGroup(
            pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                        .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTitleDnSongs)
                            .addComponent(txfBoxTitleDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfBoxMinutesDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMinutesDnSongs)))
                    .addGroup(pnlSettingDnSongsLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnAddDnSongs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveSongDnSongs)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsingerDnSongs)
                    .addComponent(txfBoxSingerDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSettingDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOthreDnSongs)
                    .addComponent(cbxDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenreDnSongs))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPreviewCoverDnS.setBackground(new java.awt.Color(245, 247, 246));
        pnlPreviewCoverDnS.setBorder(new javax.swing.border.MatteBorder(null));
        pnlPreviewCoverDnS.setPreferredSize(new java.awt.Dimension(260, 140));

        javax.swing.GroupLayout pnlPreviewCoverDnSLayout = new javax.swing.GroupLayout(pnlPreviewCoverDnS);
        pnlPreviewCoverDnS.setLayout(pnlPreviewCoverDnSLayout);
        pnlPreviewCoverDnSLayout.setHorizontalGroup(
            pnlPreviewCoverDnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );
        pnlPreviewCoverDnSLayout.setVerticalGroup(
            pnlPreviewCoverDnSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
        );

        pnlTableDnSongs.setBackground(new java.awt.Color(245, 247, 246));
        pnlTableDnSongs.setBorder(new javax.swing.border.MatteBorder(null));

        TableBoxDnSongs.setBackground(new java.awt.Color(245, 247, 246));
        TableBoxDnSongs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title", "Min", "Singer", "Genre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableBoxDnSongs.setFocusable(false);
        TableBoxDnSongs.setIntercellSpacing(new java.awt.Dimension(0, 0));
        TableBoxDnSongs.setSelectionBackground(new java.awt.Color(245, 247, 246));
        TableBoxDnSongs.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(TableBoxDnSongs);

        javax.swing.GroupLayout pnlTableDnSongsLayout = new javax.swing.GroupLayout(pnlTableDnSongs);
        pnlTableDnSongs.setLayout(pnlTableDnSongsLayout);
        pnlTableDnSongsLayout.setHorizontalGroup(
            pnlTableDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pnlTableDnSongsLayout.setVerticalGroup(
            pnlTableDnSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlDnSPanelLayout = new javax.swing.GroupLayout(pnlDnSPanel);
        pnlDnSPanel.setLayout(pnlDnSPanelLayout);
        pnlDnSPanelLayout.setHorizontalGroup(
            pnlDnSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDnSPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDnSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDnSPanelLayout.createSequentialGroup()
                        .addComponent(pnlTableDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDnSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlSettingDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 335, Short.MAX_VALUE)
                            .addComponent(pnlTableDnSongs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlDnSPanelLayout.createSequentialGroup()
                        .addGroup(pnlDnSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitleDnS)
                            .addComponent(pnlSettingDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPreviewCoverDnS, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlDnSPanelLayout.setVerticalGroup(
            pnlDnSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlDnSPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlDnSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDnSPanelLayout.createSequentialGroup()
                        .addComponent(lblTitleDnS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlSettingDiscsnS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlPreviewCoverDnS, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDnSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlDnSPanelLayout.createSequentialGroup()
                        .addComponent(pnlSettingDnSongs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlTableDnSongs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlTableDiscsnS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDnSPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDnSPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txfBoxSingerDiscsnSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxSingerDiscsnSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxSingerDiscsnSActionPerformed

    private void txfBoxTitleDiscsnSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxTitleDiscsnSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxTitleDiscsnSActionPerformed

    private void btnOthreDiscsnSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOthreDiscsnSActionPerformed
        // Add Other Discs
        try { String g = JOptionPane.showInputDialog(this, "New genre:"); if (g == null || g.isBlank()) return;
            String ensured = genreSvc.ensure(g.trim()); cbxDiscsnS.setSelectedItem(ensured);
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }//GEN-LAST:event_btnOthreDiscsnSActionPerformed

    private void txfBoxCoverDiscsnSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxCoverDiscsnSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxCoverDiscsnSActionPerformed

    private void btnBrowseDiscsnSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseDiscsnSActionPerformed
        // Browse cover
        try {
            javax.swing.JFileChooser ch = new javax.swing.JFileChooser();
            ch.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "png","jpg","jpeg"));
            if (ch.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
                String path = ch.getSelectedFile().getAbsolutePath();
                txfBoxCoverDiscsnS.setText(path); 
                loadCoverPreview(path);
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBrowseDiscsnSActionPerformed

    private void btnCrearDiscsnSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearDiscsnSActionPerformed
        // clear Discs form 
        clearDiscForm();
    }//GEN-LAST:event_btnCrearDiscsnSActionPerformed

    private void btnAddDiscsnS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDiscsnS1ActionPerformed
        try {
            String title  = req(txfBoxTitleDiscsnS.getText(), "title");
            String singer = req(txfBoxSingerDiscsnS.getText(), "singer");
            String genre  = req(cbxDiscsnS.getSelectedItem() != null ? cbxDiscsnS.getSelectedItem().toString() : null, "genre");
            if ("OTHER".equalsIgnoreCase(genre)) {
                String g = JOptionPane.showInputDialog(this, "New genre:");
                if (g == null || g.isBlank()) return;
                genre = genreSvc.ensure(g.trim());
                refreshGenresComboSimple();
                cbxDiscsnS.setSelectedItem(genre);
            }
            String coverPath = txfBoxCoverDiscsnS.getText();
            coverPath = (coverPath != null && !coverPath.isBlank()) ? coverPath.trim() : null;

            radio.model.Disc d = svc.registerDisc(title, singer, genre, coverPath);

            refreshDiscsTable();
            clearDiscForm();
            selectDiscInTable(d);

            JOptionPane.showMessageDialog(this, "Disc added: " + d.getTitle(), "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddDiscsnS1ActionPerformed

    private void btnRemoveSongDnSongsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveSongDnSongsActionPerformed
        // Remove Song
        JOptionPane.showMessageDialog(this,
            "Remove song is not implemented (not required by the spec).",
            "Info", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnRemoveSongDnSongsActionPerformed

    private void btnAddDnSongsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDnSongsActionPerformed
        // Add Song
        try {
            radio.model.Disc disc = getSelectedDisc();
            if (disc == null) throw new IllegalStateException("Select a disc first");

            String title  = req(txfBoxTitleDnSongs.getText(), "song title");
            String singer = req(txfBoxSingerDnSongs.getText(), "song singer");
            int minutes   = pos(parseIntSafe(txfBoxMinutesDnSongs.getText()), "song minutes");
            String genre  = req(cbxDnSongs.getSelectedItem() != null ? cbxDnSongs.getSelectedItem().toString() : null, "song genre");
            if ("OTHER".equalsIgnoreCase(genre)) {
                String g = JOptionPane.showInputDialog(this, "New genre:");
                if (g == null || g.isBlank()) return;
                genre = genreSvc.ensure(g.trim());
                refreshGenresComboSimple();
                cbxDnSongs.setSelectedItem(genre);
            }

            radio.model.Song s = svc.addSongToDisc(disc, title, minutes, singer, genre);

            refreshSongsTable(disc);
            clearSongForm();

            JOptionPane.showMessageDialog(this, "Song added: " + s.getTitle(), "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddDnSongsActionPerformed

    private void txfBoxSingerDnSongsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxSingerDnSongsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxSingerDnSongsActionPerformed

    private void btnOthreDnSongsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOthreDnSongsActionPerformed
        // Add other songs
        try { String g = JOptionPane.showInputDialog(this, "New genre:"); if (g == null || g.isBlank()) return;
            String ensured = genreSvc.ensure(g.trim()); cbxDnSongs.setSelectedItem(ensured);
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }//GEN-LAST:event_btnOthreDnSongsActionPerformed

    private void txfBoxMinutesDnSongsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxMinutesDnSongsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxMinutesDnSongsActionPerformed

    private void txfBoxTitleDnSongsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxTitleDnSongsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxTitleDnSongsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableBoxDiscsnS;
    private javax.swing.JTable TableBoxDnSongs;
    private javax.swing.JButton btnAddDiscsnS1;
    private javax.swing.JButton btnAddDnSongs;
    private javax.swing.JButton btnBrowseDiscsnS;
    private javax.swing.JButton btnCrearDiscsnS;
    private javax.swing.JButton btnOthreDiscsnS;
    private javax.swing.JButton btnOthreDnSongs;
    private javax.swing.JButton btnRemoveSongDnSongs;
    private javax.swing.JComboBox<String> cbxDiscsnS;
    private javax.swing.JComboBox<String> cbxDnSongs;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCoverDiscsnS;
    private javax.swing.JLabel lblGenreDiscsnS;
    private javax.swing.JLabel lblGenreDnSongs;
    private javax.swing.JLabel lblMinutesDnSongs;
    private javax.swing.JLabel lblSingerDiscsnS;
    private javax.swing.JLabel lblTitleDiscsnS;
    private javax.swing.JLabel lblTitleDnS;
    private javax.swing.JLabel lblTitleDnSongs;
    private javax.swing.JLabel lblsingerDnSongs;
    private javax.swing.JPanel pnlDnSPanel;
    private javax.swing.JPanel pnlPreviewCoverDnS;
    private javax.swing.JPanel pnlSettingDiscsnS;
    private javax.swing.JPanel pnlSettingDnSongs;
    private javax.swing.JPanel pnlTableDiscsnS;
    private javax.swing.JPanel pnlTableDnSongs;
    private javax.swing.JTextField txfBoxCoverDiscsnS;
    private javax.swing.JTextField txfBoxMinutesDnSongs;
    private javax.swing.JTextField txfBoxSingerDiscsnS;
    private javax.swing.JTextField txfBoxSingerDnSongs;
    private javax.swing.JTextField txfBoxTitleDiscsnS;
    private javax.swing.JTextField txfBoxTitleDnSongs;
    // End of variables declaration//GEN-END:variables
}
