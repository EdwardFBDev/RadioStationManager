package radio.ui;

import radio.service.GenreService;
import radio.service.PlaylistGenerator;
import radio.service.RadioManagerService;
import radio.model.RadioStation;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author funes
 */
public class RadioPanel extends javax.swing.JPanel {

    private GenreService genreSvc;
    private PlaylistGenerator generator;
    private RadioManagerService svc;

    public void bindServices(GenreService genreSvc, PlaylistGenerator generator, RadioManagerService svc) {
        this.genreSvc = genreSvc;
        this.generator = generator;
        this.svc = svc;
        preloadStationIfExists();
    
    }
    
    private void preloadStationIfExists() {
        // rellena los campos si ya hay estación creada
        if (svc == null) return;
        try {
            RadioStation rs = svc.getStation(); // si no existe lanza IllegalStateException
            // Rellena los campos con lo que ya hay
            txfBoxNameRadio.setText(rs.getName());
            txfBoxFraquencyRadio.setText(rs.getFrequency());
            txfBoxWbsiteURLRadio.setText(rs.getUrl());
        } catch (IllegalStateException ex) {
            // Aún no hay estación: dejamos los campos vacíos
            txfBoxNameRadio.setText("");
            txfBoxFraquencyRadio.setText("");
            txfBoxWbsiteURLRadio.setText("");
        }
    }
    
    private void clearRadioForm() {
        txfBoxNameRadio.setText("");
        txfBoxFraquencyRadio.setText("");
        txfBoxWbsiteURLRadio.setText("");
        
        
    }
    
    public RadioPanel() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitleRadioPanel = new javax.swing.JLabel();
        pnlInputRadio = new javax.swing.JPanel();
        lblNameRadio = new javax.swing.JLabel();
        lblFraquencyRadio = new javax.swing.JLabel();
        lblWebsiteURLRadio = new javax.swing.JLabel();
        btnSaveRadio = new javax.swing.JButton();
        txfBoxNameRadio = new javax.swing.JTextField();
        txfBoxFraquencyRadio = new javax.swing.JTextField();
        txfBoxWbsiteURLRadio = new javax.swing.JTextField();

        setBackground(new java.awt.Color(77, 98, 91));
        setName(""); // NOI18N

        lblTitleRadioPanel.setBackground(new java.awt.Color(168, 178, 130));
        lblTitleRadioPanel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTitleRadioPanel.setForeground(new java.awt.Color(168, 178, 130));
        lblTitleRadioPanel.setText("Radio (Station Settings)");

        pnlInputRadio.setBackground(new java.awt.Color(245, 247, 246));
        pnlInputRadio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 2, 1, new java.awt.Color(0, 0, 0)));

        lblNameRadio.setForeground(new java.awt.Color(60, 63, 65));
        lblNameRadio.setText("Name: ");

        lblFraquencyRadio.setForeground(new java.awt.Color(60, 63, 65));
        lblFraquencyRadio.setText("Fraquency:");

        lblWebsiteURLRadio.setForeground(new java.awt.Color(60, 63, 65));
        lblWebsiteURLRadio.setText("Website URL:");

        btnSaveRadio.setBackground(new java.awt.Color(168, 178, 130));
        btnSaveRadio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSaveRadio.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveRadio.setText("Save");
        btnSaveRadio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSaveRadio.setBorderPainted(false);
        btnSaveRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveRadioActionPerformed(evt);
            }
        });

        txfBoxNameRadio.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxNameRadio.setText("Name...");
        txfBoxNameRadio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxNameRadio.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxNameRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxNameRadioActionPerformed(evt);
            }
        });

        txfBoxFraquencyRadio.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxFraquencyRadio.setText("Fraquency...");
        txfBoxFraquencyRadio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxFraquencyRadio.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxFraquencyRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxFraquencyRadioActionPerformed(evt);
            }
        });

        txfBoxWbsiteURLRadio.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxWbsiteURLRadio.setText("URL...");
        txfBoxWbsiteURLRadio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxWbsiteURLRadio.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxWbsiteURLRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxWbsiteURLRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInputRadioLayout = new javax.swing.GroupLayout(pnlInputRadio);
        pnlInputRadio.setLayout(pnlInputRadioLayout);
        pnlInputRadioLayout.setHorizontalGroup(
            pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputRadioLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNameRadio)
                    .addComponent(lblFraquencyRadio)
                    .addComponent(lblWebsiteURLRadio))
                .addGap(27, 27, 27)
                .addGroup(pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInputRadioLayout.createSequentialGroup()
                        .addComponent(txfBoxWbsiteURLRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 217, Short.MAX_VALUE)
                        .addComponent(btnSaveRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInputRadioLayout.createSequentialGroup()
                        .addGroup(pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfBoxFraquencyRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfBoxNameRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlInputRadioLayout.setVerticalGroup(
            pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputRadioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNameRadio)
                    .addComponent(txfBoxNameRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFraquencyRadio)
                    .addComponent(txfBoxFraquencyRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(pnlInputRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWebsiteURLRadio)
                    .addComponent(btnSaveRadio)
                    .addComponent(txfBoxWbsiteURLRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitleRadioPanel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlInputRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitleRadioPanel)
                .addGap(40, 40, 40)
                .addComponent(pnlInputRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txfBoxNameRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxNameRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxNameRadioActionPerformed

    private void txfBoxFraquencyRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxFraquencyRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxFraquencyRadioActionPerformed

    private void txfBoxWbsiteURLRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxWbsiteURLRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxWbsiteURLRadioActionPerformed

    private void btnSaveRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveRadioActionPerformed
        String name = txfBoxNameRadio.getText().trim();
        String freq = txfBoxFraquencyRadio.getText().trim();
        String url  = txfBoxWbsiteURLRadio.getText().trim();

        try {
            if (svc == null) {
                JOptionPane.showMessageDialog(this, "Internal error: services not bound.");
                return;
            }

            if (name.isEmpty() || freq.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Frequency are required");
                return;
            }

            // URL opcional; validación mínima
            if (!url.isEmpty() && !looksLikeUrl(url)) {
                int opt = JOptionPane.showConfirmDialog(
                    this,
                    "The URL doesn’t look valid. Save anyway?",
                    "Confirm URL",
                    JOptionPane.YES_NO_OPTION
                );
                if (opt != JOptionPane.YES_OPTION) return;
            }

            // ¿Ya hay estación? (avisar reemplazo)
            boolean exists;
            try { svc.getStation(); exists = true; }
            catch (IllegalStateException ex) { exists = false; }

            if (exists) {
                int opt = JOptionPane.showConfirmDialog(
                    this,
                    "Replacing the station will reset all data (announcers, programs, discs, songs). Continue?",
                    "Replace station",
                    JOptionPane.YES_NO_OPTION
                );
                if (opt != JOptionPane.YES_OPTION) return;
            }

            // Guardar
            svc.createStation(name, freq, url);
            JOptionPane.showMessageDialog(this, exists ? "Station replaced." : "Station created.");

            // Actualizar label del frame
            java.awt.Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof RadioManagerFrame) {
                ((RadioManagerFrame) window).updateRadioInfo(name, freq);
            }

            // ✅ Limpiar solo si se guardó correctamente
            clearRadioForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // ❌ NO limpiar aquí: deja los campos para que el usuario corrija
        }
        
    }   
        
    private boolean looksLikeUrl(String s) {
        // Validación simple; URL puede quedar vacía
        return s.startsWith("http://") || s.startsWith("https://");
    }
    
    // Variables declaration - do not modify 
    private javax.swing.JButton btnSaveRadio;
    private javax.swing.JLabel lblFraquencyRadio;
    private javax.swing.JLabel lblNameRadio;
    private javax.swing.JLabel lblTitleRadioPanel;
    private javax.swing.JLabel lblWebsiteURLRadio;
    private javax.swing.JPanel pnlInputRadio;
    private javax.swing.JTextField txfBoxFraquencyRadio;
    private javax.swing.JTextField txfBoxNameRadio;
    private javax.swing.JTextField txfBoxWbsiteURLRadio;  
    
    
    }//GEN-LAST:event_btnSaveRadioActionPerformed

/*
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSaveRadio;
    private javax.swing.JLabel lblFraquencyRadio;
    private javax.swing.JLabel lblNameRadio;
    private javax.swing.JLabel lblTitleRadioPanel;
    private javax.swing.JLabel lblWebsiteURLRadio;
    private javax.swing.JPanel pnlInputRadio;
    private javax.swing.JTextField txfBoxFraquencyRadio;
    private javax.swing.JTextField txfBoxNameRadio;
    private javax.swing.JTextField txfBoxWbsiteURLRadio;
    // End of variables declaration//GEN-END:variables
}*/
