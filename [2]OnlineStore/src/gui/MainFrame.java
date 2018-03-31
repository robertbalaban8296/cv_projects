/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import databeans.DepartmentType;
import databeans.Item;
import databeans.OrderTypes;
import java.util.Random;
import javax.swing.JOptionPane;
import service.MainService;

/**
 *
 * @author robert.balaban
 */
public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        addButtons();
        addActionListeners();
        test();
    }
    
    public void modifyPrice() {
        if (jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Insert the new price");
        } else {
            if (getSelected() == null || jList1.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Select a product");
            } else {
                double newPrice = Double.parseDouble(jTextField1.getText());
                String [] idS = jList1.getSelectedValue().split(" ");
                int id = Integer.parseInt(idS[0]);
                MainService.getMainService().modifyPrice(getSelected(), id, newPrice);
                updateList(OrderTypes.DEFAULT, getSelected());
            }
        }     
    }

    public void test() {
        MainService m = MainService.getMainService();
        Random r = new Random(100);
        for (int i = 0; i < 9; i++) {
            m.adaugaItem(new Item("Book" + i, Double.parseDouble(String.format("%.2f", r.nextDouble()))), DepartmentType.BookDepartment);
        }
        for (int i = 0; i < 9; i++) {
            m.adaugaItem(new Item("Music" + i, Double.parseDouble(String.format("%.2f", r.nextDouble()))), DepartmentType.MusicDepartment);
        }
        for (int i = 0; i < 9; i++) {
            m.adaugaItem(new Item("Video" + i, Double.parseDouble(String.format("%.2f", r.nextDouble()))), DepartmentType.VideoDepartment);
        }
        for (int i = 0; i < 9; i++) {
            m.adaugaItem(new Item("Soft" + i, Double.parseDouble(String.format("%.2f", r.nextDouble()))), DepartmentType.SoftwareDepartment);
        }
    }
    
    public void addButtons() {
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        buttonGroup1.add(jRadioButton3);
        buttonGroup1.add(jRadioButton4);
        jComboBox1.removeAllItems();
        jComboBox1.addItem("10%");
        jComboBox1.addItem("20%");
        jComboBox1.addItem("25%");
    }
    
    public void addActionListeners() {
        jRadioButton1.addActionListener(ev -> updateList(OrderTypes.DEFAULT, DepartmentType.BookDepartment));
        jRadioButton2.addActionListener(ev -> updateList(OrderTypes.DEFAULT, DepartmentType.SoftwareDepartment));
        jRadioButton3.addActionListener(ev -> updateList(OrderTypes.DEFAULT, DepartmentType.VideoDepartment));
        jRadioButton4.addActionListener(ev -> updateList(OrderTypes.DEFAULT, DepartmentType.MusicDepartment));
        
        jButton1.addActionListener(ev -> {
            if (getSelected() == null) {
               JOptionPane.showMessageDialog(null, "Select a department");
            } else {
               new AddFrame(getSelected());
               updateList(OrderTypes.DEFAULT, getSelected());
            }
        });
        
        jButton2.addActionListener(ev -> {
            if (getSelected() == null) {
                JOptionPane.showMessageDialog(null, "Select a department");
            } else {
                if (jList1.getSelectedValue() != null) {
                    String [] id = jList1.getSelectedValue().split(" "); 
                    int x = Integer.parseInt(id[0]);
                    MainService.getMainService().removeItem(x, getSelected());
                    updateList(OrderTypes.DEFAULT, getSelected());
                } else {
                    JOptionPane.showMessageDialog(null, "Select a product to remove");
                }
            }
        });
        
        jButton3.addActionListener(ev -> {
            if (getSelected() == null) {
                JOptionPane.showMessageDialog(null, "Select a department");
            } else {
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        MainService.getMainService().applyDiscount("10%", getSelected());
                        break;
                    case 1:
                        MainService.getMainService().applyDiscount("20%", getSelected());
                        break;
                    case 2:
                        MainService.getMainService().applyDiscount("25%", getSelected());
                        break;
                }
                updateList(OrderTypes.DEFAULT, getSelected());
            }
        });
        
        jButton4.addActionListener(ev -> {
            if (getSelected() == null) {
                JOptionPane.showMessageDialog(null, "Select a department");
            } else {
                updateList(OrderTypes.ASC, getSelected());
            }
        });
        
        jButton5.addActionListener(ev -> {
            if (getSelected() == null) {
                JOptionPane.showMessageDialog(null, "Select a department");
            } else {
                updateList(OrderTypes.DESC, getSelected());
            }
        });
        
        jButton6.addActionListener(ev -> modifyPrice());
    }
    
    public DepartmentType getSelected() {
        if (jRadioButton1.isSelected()) {
            return DepartmentType.BookDepartment;
        }
        if (jRadioButton2.isSelected()) {
            return DepartmentType.SoftwareDepartment;
        }
        if (jRadioButton3.isSelected()) {
            return DepartmentType.VideoDepartment;
        } 
        if (jRadioButton4.isSelected()) {
            return DepartmentType.MusicDepartment;
        }
        return null;
    }
    
    public void updateList(OrderTypes o, DepartmentType d) {
        jList1.setModel(MainService.getMainService().getItems(o, d));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Add Product");

        jButton2.setText("Delete Product");

        jRadioButton1.setText("BookDepartment");

        jRadioButton2.setText("SoftwareDepartment");

        jRadioButton3.setText("VideoDepartment");

        jRadioButton4.setText("MusicDepartment");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {  };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("DISCOUNT: ");

        jButton3.setText("Apply");

        jLabel2.setText("ID | NUME | PRET");

        jButton4.setText("ASC Price");

        jButton5.setText("DESC Price");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Modify Price");

        jLabel3.setText("PRICE:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jRadioButton4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField1))
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(315, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton4)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(32, 32, 32))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
