/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.drools.in.action.domain.petstore;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code PetStoreUI}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/10
 */
public class PetStoreUI extends JPanel {

    private static final long serialVersionUID = 5072175451555143081L;

    private JTextArea output;
    private TableModel tableModel;
    private CheckoutCallback callback;

    public PetStoreUI(List<Product> items, CheckoutCallback callback) {
        super(new BorderLayout());
        this.callback = callback;

        // Create main vertical split panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        JPanel topHalf = new JPanel();
        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.X_AXIS));
        topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        topHalf.setMinimumSize(new Dimension(400, 50));
        topHalf.setPreferredSize(new Dimension(450, 250));
        splitPane.add(topHalf);

        JPanel bottomHalf = new JPanel(new BorderLayout());
        bottomHalf.setMinimumSize(new Dimension(400, 50));
        bottomHalf.setPreferredSize(new Dimension(450, 300));
        splitPane.add(bottomHalf);

        JPanel listContainer = new JPanel(new GridLayout(1, 1));
        listContainer.setBorder(BorderFactory.createTitledBorder("List"));
        topHalf.add(listContainer);

        JList list = new JList(items.toArray());
        ListSelectionModel listSelectionModel = list.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addMouseListener(new ListSelectionHandler());
        JScrollPane listPane = new JScrollPane(list);
        listContainer.add(listPane);

        JPanel tableContainer = new JPanel(new GridLayout(1, 1));
        tableContainer.setBorder(BorderFactory.createTitledBorder("Table"));
        topHalf.add(tableContainer);

        tableModel = new TableModel();
        JTable table = new JTable(tableModel);

        table.addMouseListener(new TableSelectionHandler());
        ListSelectionModel tableSelectionModel = table.getSelectionModel();
        tableSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableColumnModel tableColumnModel = table.getColumnModel();

        tableColumnModel.getColumn(0).setCellRenderer(new NameRenderer());
        tableColumnModel.getColumn(1).setCellRenderer(new PriceRenderer());
        tableColumnModel.getColumn(1).setMaxWidth(50);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setPreferredSize(new Dimension(150, 100));
        tableContainer.add(tablePane);

        JPanel checkoutPane = new JPanel();
        JButton button = new JButton("Checkout");
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.LEADING);

        button.addMouseListener(new CheckoutButtonHandler());
        button.setActionCommand("checkout");
        checkoutPane.add(button);
        bottomHalf.add(checkoutPane, BorderLayout.NORTH);

        button = new JButton("Reset");
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.TRAILING);

        button.addMouseListener(new ResetButtonHandler());
        button.setActionCommand("reset");
        checkoutPane.add(button);
        bottomHalf.add(checkoutPane, BorderLayout.NORTH);

        output = new JTextArea(1, 10);
        output.setEditable(false);

        JScrollPane outputPane = new JScrollPane(
            output,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        bottomHalf.add(outputPane, BorderLayout.CENTER);

        this.callback.setOutput(this.output);
    }

    public void createAndShowGUI(boolean exitOnClose) {
        // Create and set up the window.
        JFrame frame = new JFrame("Pet Store");
        frame.setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);

        setOpaque(true);
        frame.setContentPane(this);

        // Display the window.
        frame.pack();

        // Center in screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class ListSelectionHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            JList jlist = (JList) e.getSource();
            tableModel.addItem((Product) jlist.getSelectedValue());
        }
    }

    private class TableSelectionHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            JTable jtable = (JTable) e.getSource();
            TableModel tableModel = (TableModel) jtable.getModel();
            tableModel.removeItem(jtable.getSelectedRow());
        }
    }

    private class CheckoutButtonHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            callback.checkout((JFrame) button.getTopLevelAncestor(), tableModel.getItems());
        }
    }

    private class ResetButtonHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            output.setText(null);
            tableModel.clear();
            System.out.println("------ Reset ------");
        }
    }

    private static class NameRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 5793234951540106545L;

        public NameRenderer() {
            super();
        }

        public void setValue(Object object) {
            Product item = (Product) object;
            setText(item.getName());
        }
    }

    private static class PriceRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = -8165068655763096651L;

        public PriceRenderer() {
            super();
        }

        public void setValue(Object object) {
            Product item = (Product) object;
            setText(Double.toString(item.getPrice()));
        }
    }

    private static class TableModel extends AbstractTableModel {

        private static final long serialVersionUID = 510l;

        private String[] columnNames = {"Name", "Price"};

        private ArrayList<Product> items;

        public TableModel() {
            super();
            items = new ArrayList<>();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return items.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row,
                                 int col) {
            return items.get(row);
        }

        public Class<?> getColumnClass(int c) {
            return Product.class;
        }

        public void addItem(Product item) {
            items.add(item);
            fireTableRowsInserted(items.size(),
                items.size());
        }

        public void removeItem(int row) {
            items.remove(row);
            fireTableRowsDeleted(row,
                row);
        }

        public List<Product> getItems() {
            return items;
        }

        public void clear() {
            int lastRow = items.size();
            items.clear();
            fireTableRowsDeleted(0,
                lastRow);
        }
    }

    public static class CheckoutCallback {

        KieContainer kcontainer;
        JTextArea output;

        public CheckoutCallback(KieContainer kcontainer) {
            this.kcontainer = kcontainer;
        }

        public void setOutput(JTextArea output) {
            this.output = output;
        }

        public String checkout(JFrame frame, List<Product> items) {
            Order order = new Order();

            for (Product p : items) {
                order.addItem(new Purchase(order, p));
            }

            // its definition and configuration in the META-INF/kmodule.xml file
            KieSession ksession = kcontainer.newKieSession(
                "io.github.photowey.drools.kbase.ksession.petstore"
            );

            ksession.setGlobal("frame", frame);
            ksession.setGlobal("textArea", this.output);

            ksession.insert(new Product("Gold Fish", 5));
            ksession.insert(new Product("Fish Tank", 25));
            ksession.insert(new Product("Fish Food", 2));

            ksession.insert(new Product("Fish Food Sample", 0));

            ksession.insert(order);
            ksession.fireAllRules();

            return order.toString();
        }
    }
}
