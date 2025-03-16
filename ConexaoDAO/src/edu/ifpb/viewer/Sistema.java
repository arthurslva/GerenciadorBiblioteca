package edu.ifpb.viewer;

import javax.swing.SwingUtilities;

public class Sistema {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginViewer login = new LoginViewer(null);
            login.setVisible(true);  
        });
    }
}
