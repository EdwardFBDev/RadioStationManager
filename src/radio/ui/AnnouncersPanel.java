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
public class AnnouncersPanel extends javax.swing.JPanel {
    private GenreService genreSvc;
    private PlaylistGenerator generator; 
    private RadioManagerService svc;
    private DefaultTableModel announcersModel;
    
 
    public void bindServices(GenreService genreSvc, PlaylistGenerator generator, RadioManagerService svc) {
        this.genreSvc = genreSvc;
        this.generator = generator;
        this.svc = svc;

        setupAnnouncersTable();
        refreshAnnouncersTable();

        // UX: limitar teléfono a caracteres válidos
        attachPhoneDigitsFilter();

        //  Enter envía "Add" si el panel ya está en un contenedor con root pane
        if (getRootPane() != null) {
            getRootPane().setDefaultButton(btnAddAnnouncers);
        }
}
    
    
    // === Helpers de validación ===
    private static String req(String v, String field) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException(field + " is required");
        return v.trim();
    }

    /** Filtro simple para permitir solo dígitos, espacio, + y - en el teléfono */
    private void attachPhoneDigitsFilter() {
        txfBoxPhoneNumberAnnouncers.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                boolean ok = Character.isDigit(c) || c==' ' || c=='+' || c=='-' 
                         || c==java.awt.event.KeyEvent.VK_BACK_SPACE 
                         || c==java.awt.event.KeyEvent.VK_DELETE;
                if (!ok) evt.consume();
            }
        });
    }
    
    private void setupAnnouncersTable() {
        announcersModel = new DefaultTableModel(new Object[]{"ID","Name","Email","Phone"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    TableBoxAnnouncers.setModel(announcersModel);
    }
    
    private void refreshAnnouncersTable() {
        announcersModel.setRowCount(0);
        if (svc == null) return;
        try {
            for (Announcer a : svc.getStation().getAnnouncers()) {
             announcersModel.addRow(new Object[]{ a.getId(), a.getFullName(), a.getEmail(), a.getPhoneNumber() });
            }
        } catch (IllegalStateException ex) {
        // Aún no hay estación creada, no pasa nada
        }
    }
    
    private void clearAnnForm() {
        // clear 
        txfBoxIDAnnouncers.setText("");
        txfBoxFullNameAnnouncers.setText("");
        txfBoxEmailAnnouncers.setText("");
        txfBoxPhoneNumberAnnouncers.setText("");
    }
       public AnnouncersPanel() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAnnouncersPanel = new javax.swing.JPanel();
        lblTitleAnnouncersPanel = new javax.swing.JLabel();
        pnlSettingAnnouncersRadio = new javax.swing.JPanel();
        lblIDAnnouncers = new javax.swing.JLabel();
        lblFullNameAnnouncers = new javax.swing.JLabel();
        lblEmailAnnouncers = new javax.swing.JLabel();
        lblPhoneAnnouncers = new javax.swing.JLabel();
        txfBoxIDAnnouncers = new javax.swing.JTextField();
        txfBoxFullNameAnnouncers = new javax.swing.JTextField();
        txfBoxEmailAnnouncers = new javax.swing.JTextField();
        txfBoxPhoneNumberAnnouncers = new javax.swing.JTextField();
        btnAddAnnouncers = new javax.swing.JButton();
        btnClearAnnouncers = new javax.swing.JButton();
        pnlTableAnnouncers = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBoxAnnouncers = new javax.swing.JTable();

        pnlAnnouncersPanel.setBackground(new java.awt.Color(77, 98, 91));

        lblTitleAnnouncersPanel.setBackground(new java.awt.Color(168, 178, 130));
        lblTitleAnnouncersPanel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTitleAnnouncersPanel.setForeground(new java.awt.Color(168, 178, 130));
        lblTitleAnnouncersPanel.setText("Announcers");

        pnlSettingAnnouncersRadio.setBackground(new java.awt.Color(245, 247, 246));
        pnlSettingAnnouncersRadio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 2, 1, new java.awt.Color(0, 0, 0)));

        lblIDAnnouncers.setForeground(new java.awt.Color(60, 63, 65));
        lblIDAnnouncers.setText("ID:");

        lblFullNameAnnouncers.setForeground(new java.awt.Color(60, 63, 65));
        lblFullNameAnnouncers.setText("Full name:");

        lblEmailAnnouncers.setForeground(new java.awt.Color(60, 63, 65));
        lblEmailAnnouncers.setText("Email:");

        lblPhoneAnnouncers.setForeground(new java.awt.Color(60, 63, 65));
        lblPhoneAnnouncers.setText("Phone Number:");

        txfBoxIDAnnouncers.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxIDAnnouncers.setText("ID...");
        txfBoxIDAnnouncers.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxIDAnnouncers.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxIDAnnouncers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxIDAnnouncersActionPerformed(evt);
            }
        });

        txfBoxFullNameAnnouncers.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxFullNameAnnouncers.setText("Full name...");
        txfBoxFullNameAnnouncers.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxFullNameAnnouncers.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxFullNameAnnouncers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxFullNameAnnouncersActionPerformed(evt);
            }
        });

        txfBoxEmailAnnouncers.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxEmailAnnouncers.setText("Email...");
        txfBoxEmailAnnouncers.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxEmailAnnouncers.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxEmailAnnouncers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxEmailAnnouncersActionPerformed(evt);
            }
        });

        txfBoxPhoneNumberAnnouncers.setBackground(new java.awt.Color(245, 247, 246));
        txfBoxPhoneNumberAnnouncers.setText("Numbers...");
        txfBoxPhoneNumberAnnouncers.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(204, 204, 204)));
        txfBoxPhoneNumberAnnouncers.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txfBoxPhoneNumberAnnouncers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBoxPhoneNumberAnnouncersActionPerformed(evt);
            }
        });

        btnAddAnnouncers.setBackground(new java.awt.Color(168, 178, 130));
        btnAddAnnouncers.setForeground(new java.awt.Color(255, 255, 255));
        btnAddAnnouncers.setText("Add");
        btnAddAnnouncers.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddAnnouncers.setBorderPainted(false);
        btnAddAnnouncers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAnnouncersActionPerformed(evt);
            }
        });

        btnClearAnnouncers.setBackground(new java.awt.Color(168, 178, 130));
        btnClearAnnouncers.setForeground(new java.awt.Color(255, 255, 255));
        btnClearAnnouncers.setText("Clear");
        btnClearAnnouncers.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnClearAnnouncers.setBorderPainted(false);
        btnClearAnnouncers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAnnouncersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSettingAnnouncersRadioLayout = new javax.swing.GroupLayout(pnlSettingAnnouncersRadio);
        pnlSettingAnnouncersRadio.setLayout(pnlSettingAnnouncersRadioLayout);
        pnlSettingAnnouncersRadioLayout.setHorizontalGroup(
            pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingAnnouncersRadioLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIDAnnouncers)
                    .addComponent(lblFullNameAnnouncers))
                .addGap(19, 19, Short.MAX_VALUE)
                .addGroup(pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txfBoxFullNameAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfBoxIDAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSettingAnnouncersRadioLayout.createSequentialGroup()
                        .addComponent(lblEmailAnnouncers)
                        .addGap(66, 66, 66)
                        .addComponent(txfBoxEmailAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSettingAnnouncersRadioLayout.createSequentialGroup()
                        .addComponent(lblPhoneAnnouncers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfBoxPhoneNumberAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddAnnouncers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClearAnnouncers, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlSettingAnnouncersRadioLayout.setVerticalGroup(
            pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingAnnouncersRadioLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIDAnnouncers)
                    .addComponent(lblEmailAnnouncers)
                    .addComponent(txfBoxIDAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfBoxEmailAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddAnnouncers))
                .addGap(18, 18, 18)
                .addGroup(pnlSettingAnnouncersRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFullNameAnnouncers)
                    .addComponent(lblPhoneAnnouncers)
                    .addComponent(txfBoxFullNameAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfBoxPhoneNumberAnnouncers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearAnnouncers))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pnlTableAnnouncers.setBackground(new java.awt.Color(245, 247, 246));
        pnlTableAnnouncers.setBorder(new javax.swing.border.MatteBorder(null));

        TableBoxAnnouncers.setBackground(new java.awt.Color(245, 247, 246));
        TableBoxAnnouncers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Email", "Phone number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableBoxAnnouncers.setFocusable(false);
        TableBoxAnnouncers.setIntercellSpacing(new java.awt.Dimension(0, 0));
        TableBoxAnnouncers.setSelectionBackground(new java.awt.Color(245, 247, 246));
        TableBoxAnnouncers.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(TableBoxAnnouncers);

        javax.swing.GroupLayout pnlTableAnnouncersLayout = new javax.swing.GroupLayout(pnlTableAnnouncers);
        pnlTableAnnouncers.setLayout(pnlTableAnnouncersLayout);
        pnlTableAnnouncersLayout.setHorizontalGroup(
            pnlTableAnnouncersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlTableAnnouncersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE))
        );
        pnlTableAnnouncersLayout.setVerticalGroup(
            pnlTableAnnouncersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
            .addGroup(pnlTableAnnouncersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAnnouncersPanelLayout = new javax.swing.GroupLayout(pnlAnnouncersPanel);
        pnlAnnouncersPanel.setLayout(pnlAnnouncersPanelLayout);
        pnlAnnouncersPanelLayout.setHorizontalGroup(
            pnlAnnouncersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnnouncersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAnnouncersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSettingAnnouncersRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlAnnouncersPanelLayout.createSequentialGroup()
                        .addComponent(lblTitleAnnouncersPanel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlTableAnnouncers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAnnouncersPanelLayout.setVerticalGroup(
            pnlAnnouncersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnnouncersPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitleAnnouncersPanel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSettingAnnouncersRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTableAnnouncers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAnnouncersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlAnnouncersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txfBoxIDAnnouncersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxIDAnnouncersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxIDAnnouncersActionPerformed

    private void txfBoxFullNameAnnouncersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxFullNameAnnouncersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxFullNameAnnouncersActionPerformed

    private void txfBoxEmailAnnouncersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxEmailAnnouncersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxEmailAnnouncersActionPerformed

    private void txfBoxPhoneNumberAnnouncersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBoxPhoneNumberAnnouncersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBoxPhoneNumberAnnouncersActionPerformed

    private void btnAddAnnouncersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAnnouncersActionPerformed
       try {
            if (svc == null){
                JOptionPane.showMessageDialog(this, "Services not bound. Open panel from the main menu.");
                return;
            }
            String id    = req(txfBoxIDAnnouncers.getText(), "ID");
            String name  = req(txfBoxFullNameAnnouncers.getText(), "Full name");
            String email = req(txfBoxEmailAnnouncers.getText(), "Email");
            String phone = req(txfBoxPhoneNumberAnnouncers.getText(), "Phone number");

            // (Opcional) chequeo básico de formato de email
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Invalid email format", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Registrar en servicio (lanza error si el ID ya existe)
            svc.registerAnnouncer(id, name, email, phone);

            refreshAnnouncersTable();
            clearAnnForm();
            JOptionPane.showMessageDialog(this, "Announcer added");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddAnnouncersActionPerformed

    private void btnClearAnnouncersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAnnouncersActionPerformed
        clearAnnForm();
    }//GEN-LAST:event_btnClearAnnouncersActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableBoxAnnouncers;
    private javax.swing.JButton btnAddAnnouncers;
    private javax.swing.JButton btnClearAnnouncers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmailAnnouncers;
    private javax.swing.JLabel lblFullNameAnnouncers;
    private javax.swing.JLabel lblIDAnnouncers;
    private javax.swing.JLabel lblPhoneAnnouncers;
    private javax.swing.JLabel lblTitleAnnouncersPanel;
    private javax.swing.JPanel pnlAnnouncersPanel;
    private javax.swing.JPanel pnlSettingAnnouncersRadio;
    private javax.swing.JPanel pnlTableAnnouncers;
    private javax.swing.JTextField txfBoxEmailAnnouncers;
    private javax.swing.JTextField txfBoxFullNameAnnouncers;
    private javax.swing.JTextField txfBoxIDAnnouncers;
    private javax.swing.JTextField txfBoxPhoneNumberAnnouncers;
    // End of variables declaration//GEN-END:variables
}
