package com.bigvisible.kanbansimulator;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUI extends JFrame {
    private static final long serialVersionUID = -1045195339415169014L;

    /**
     * @param args
     */
    public static void main(String[] args) {
        GUI gui = new GUI();

        JButton runButton = new JButton("Run");

        String[] columnNames = { "Iteration", "BA", "Dev", "Web Dev", "QA" };

        Object[][] data = { { 1, 13, 12, 12, 10 }, { 2, 13, 12, 12, 10 }, { 3, 13, 12, 12, 10 }, { 4, 13, 12, 12, 10 },
                { 5, 13, 12, 12, 10 }, { 6, 13, 12, 12, 10 }, { 7, 13, 12, 12, 10 }, { 8, 13, 12, 12, 10 },
                { 9, 13, 12, 12, 10 }, { 10, 13, 12, 12, 10 },

        };

        JTable table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        gui.setLayout(new BorderLayout());
        gui.add(table.getTableHeader(), BorderLayout.PAGE_START);
        gui.add(table, BorderLayout.CENTER);
        gui.add(runButton, BorderLayout.SOUTH);

        gui.addWindowListener(gui.new GUIWindowListener());
        gui.setSize(400, 400);
        gui.setVisible(true);
    }

    private class GUIWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event) {
            System.exit(0);
        }
    }

}
