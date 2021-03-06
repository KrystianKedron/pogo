//+======================================================================
//
// Project:   Tango
//
// Description:  java source code for a simple edition class.
//
// $Author: verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2009,2010,2011,2012,2013,2014
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: $
// $Date:  $
//
// $HeadURL: $
//
//-======================================================================

package org.tango.pogo.pogo_gui;

import fr.esrf.tangoatk.widget.util.ATKGraphicsUtils;
import org.tango.pogo.pogo_gui.tools.Utils;

import javax.swing.*;
import java.awt.*;


/**
 * @author verdier
 */
public class EditDialog extends JDialog {
    private int returnStatus = JOptionPane.CANCEL_OPTION;
    //===================================================================
    /**
     * Creates new form EditDialog
     *
     * @param parent parent instance
     * @param text   text to edit.
     */
    //===================================================================
    public EditDialog(JDialog parent, String text) {
        this(parent, text, null);
    }
    //===================================================================
    /**
     * Creates new form EditDialog
     *
     * @param parent parent instance
     * @param text   text to edit.
     * @param dimension set scrolled text to specified dimension if not null
     */
    //===================================================================
    public EditDialog(JDialog parent, String text, Dimension dimension) {
        super(parent, true);
        initComponents();
        text = Utils.strReplace(text, "\\n", "\n");
        editText.setText(text.trim());

        if(dimension!=null) {
            //  Do it on scrolled pane
            Component scrollPane = editText.getParent().getParent();
            scrollPane.setPreferredSize(dimension);
        }

        pack();
        ATKGraphicsUtils.centerDialog(this);
    }

    //===================================================================
    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //===================================================================
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JButton okBtn = new javax.swing.JButton();
        javax.swing.JButton cancelBtn = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        editText = new javax.swing.JTextArea();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });
        jPanel1.add(okBtn);

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        jPanel1.add(cancelBtn);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setViewportView(editText);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //===================================================================
    //===================================================================
    @SuppressWarnings({"UnusedDeclaration"})
    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        doClose(JOptionPane.OK_OPTION);
    }//GEN-LAST:event_okBtnActionPerformed

    //===================================================================
    //===================================================================
    @SuppressWarnings({"UnusedDeclaration"})
    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        doClose(JOptionPane.CANCEL_OPTION);
    }//GEN-LAST:event_cancelBtnActionPerformed

    //===================================================================
    //===================================================================
    @SuppressWarnings({"UnusedDeclaration"})
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(JOptionPane.CANCEL_OPTION);
    }//GEN-LAST:event_closeDialog

    //===================================================================
    //===================================================================
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    //===================================================================
    //===================================================================
    public String getText() {
        return editText.getText();
    }

    //===================================================================
    //===================================================================
    public void setPreferredSize(Dimension dimension) {
        //  Do it on scrolled pane
        editText.getParent().setPreferredSize(dimension);
    }
    //===================================================================
    //===================================================================
    public int showDialog() {
        setVisible(true);
        return returnStatus;
    }
    //===================================================================
    //===================================================================


    //===================================================================
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea editText;
    // End of variables declaration//GEN-END:variables
//===================================================================

}
