package com.bigvisible.kanbansimulator;

import static com.bigvisible.kanbansimulator.IterationParameter.startingAt;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class GUI extends JFrame {
    private static final long serialVersionUID = -1045195339415169014L;
    private static GUI instance;

    private JTextField batchSize = new JTextField();
    private JTextField storiesInBacklog = new JTextField();
    private JTextArea outputTextArea = new JTextArea();

    private JTable table;
    private JScrollPane scrollPane;
    private JScrollPane outputScrollPane;
    private JButton runButton = new JButton("Run");
    private JLabel statusLabel;

    public GUI() {
        JLabel storiesInBacklogLabel = new JLabel("Backlog:");
        storiesInBacklog.setName("storiesInBacklog");
        storiesInBacklog.setText("88");

        batchSize.setName("batchSize");
        batchSize.setText("11");

        String[] columnNames = { "Iteration", "BA", "Dev", "WebDev", "QA" };

        Object[][] data = { { 1, 13, 12, 12, 10 }, { 2, 13, 12, 12, 10 }, { 3, 13, 12, 12, 10 }, { 4, 13, 12, 12, 10 },
                { 5, 13, 12, 12, 10 }, { 6, 13, 12, 12, 10 }, { 7, 13, 12, 12, 10 }, { 8, 13, 12, 12, 10 },
                { 9, 13, 12, 12, 10 }, { 10, 13, 12, 12, 10 },

        };

        table = new JTable(data, columnNames);

        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        runButton.setName("runButton");
        runButton.addActionListener(this.new StartSimulationActionListener());

        statusLabel = new JLabel("Waiting for user to configure simulation.");
        statusLabel.setName("statusLabel");

        JLabel outputLabel = new JLabel("Output:");
        outputTextArea.setName("outputTextArea");
        outputTextArea.setText("Hello, world.");

        setLayout(new FlowLayout());

        add(table.getTableHeader());
        add(scrollPane);
        add(storiesInBacklogLabel);
        add(storiesInBacklog);
        add(batchSize);
        add(runButton);
        add(outputLabel);
        add(outputTextArea);
        add(statusLabel);

        addWindowListener(this.new GUIWindowListener());
        setSize(500, 950);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        instance = new GUI();
        instance.setVisible(true);
    }

    private class GUIWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event) {
            System.exit(0);
        }
    }

    private class StartSimulationActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO: do this in a worker thread, not on the Event Thread!!
            SimulatorEngine simulator = new SimulatorEngine();
            simulator.addStories(Integer.parseInt(storiesInBacklog.getText()));
            simulator.setBatchSize(Integer.parseInt(batchSize.getText()));

            Object[][] tableData = getTableData(table);

            for (int rowIdx = 0; rowIdx < tableData.length; rowIdx++) {
                Object[] cellsInRow = tableData[rowIdx];

                int iteration = getIntegerFromCell(cellsInRow[0]);
                int baCapacity = getIntegerFromCell(cellsInRow[1]);
                int devCapacity = getIntegerFromCell(cellsInRow[2]);
                int webDevCapacity = getIntegerFromCell(cellsInRow[3]);
                int qaCapacity = getIntegerFromCell(cellsInRow[4]);

                simulator.addParameter(startingAt(iteration).forStep("BA").setCapacity(baCapacity));
                simulator.addParameter(startingAt(iteration).forStep("Dev").setCapacity(devCapacity));
                simulator.addParameter(startingAt(iteration).forStep("WebDev").setCapacity(webDevCapacity));
                simulator.addParameter(startingAt(iteration).forStep("QA").setCapacity(qaCapacity));
            }

            simulator.run(null);

            StringBuffer output = new StringBuffer();
            for (IterationResult iterationResult : simulator.results()) {
                output.append(iterationResult.toCSVString());
                output.append("\n");
            }
            outputTextArea.setText(output.toString());

            statusLabel.setText("Done");
        }

        private int getIntegerFromCell(Object cell) {
            int value;
            if (cell instanceof Integer) {
                value = (Integer) cell;
            } else if (cell instanceof String) {
                value = Integer.parseInt((String) cell);
            } else {
                throw new RuntimeException("Cell in iteration parameter table must be either Integer or String."
                        + String.format("Type is %s; expected", cell.getClass().getName()));
            }
            return value;
        }
    }

    private static Object[][] getTableData(JTable table) {
        TableModel dtm = table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++)
            for (int j = 0; j < nCol; j++)
                tableData[i][j] = dtm.getValueAt(i, j);
        return tableData;
    }

}
