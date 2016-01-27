import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Form extends JFrame{

    private JButton add;
    private JButton delete;
    private JButton edit;
    private JTable table;
    private JScrollPane scrollPane;

    private Table tableModel;

    Form() {
        setTitle("Библиотека");
        setSize(640, 480);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        tableModel = new Table();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 20, 565, 320);
        getContentPane().add(scrollPane);

        readFromFile();

        delete = new JButton("Удалить");
        delete.setBounds(30, 350, 260, 30);
        delete.setBackground(Color.RED);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(null, "Выберите контакт в таблице");
                } else {
                    tableModel.books.remove(table.getSelectedRow());
                    table.updateUI();
                    try {
                        saveToFile(table);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        getContentPane().add(delete);

        edit = new JButton("Редактировать");
        edit.setBackground(Color.YELLOW);
        edit.setBounds(335, 350, 260, 30);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(null, "Выберите контакт в таблице");
                } else {
                    Edit edit = new Edit(tableModel.books.get(table.getSelectedRow()));
                    edit.setVisible(true);
                }
            }
        });
        getContentPane().add(edit);

        add = new JButton("Добавить");
        add.setBounds(30, 400, 565, 30);
        add.setBackground(Color.GREEN);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add add = new Add();
                add.setVisible(true);
            }
        });
        getContentPane().add(add);
    }


    class Add extends JFrame {
        private JTextField nameText;
        private JTextField authorText;
        private JTextField yearText;
        private JButton addButton;
        private JLabel nameLabel;
        private JLabel authorLabel;
        private JLabel yearLabel;

        Add() {
            setTitle("Добавление");
            setSize(250, 200);
            setLocationRelativeTo(null);
            setResizable(false);
            setLayout(null);

            nameLabel = new JLabel("Имя");
            nameLabel.setBounds(50, 20, 30, 20);
            getContentPane().add(nameLabel);

            nameText = new JTextField();
            nameText.setBounds(80, 20, 100, 20);
            getContentPane().add(nameText);

            authorLabel = new JLabel("Автор");
            authorLabel.setBounds(40, 60, 60, 20);
            getContentPane().add(authorLabel);

            authorText = new JTextField();
            authorText.setBounds(80, 60, 100, 20);
            getContentPane().add(authorText);

            yearLabel = new JLabel("Год");
            yearLabel.setBounds(50, 100, 30, 20);
            getContentPane().add(yearLabel);

            yearText = new JTextField();
            yearText.setBounds(80, 100, 100, 20);
            getContentPane().add(yearText);

            addButton = new JButton("Добавить");
            addButton.setBounds(50, 130, 130, 30);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameText.getText();
                    String author = authorText.getText();
                    String year = yearText.getText();
                    String[] booksInfo = {
                            name, author, year
                    };
                    tableModel.addInfo(booksInfo);
                    table.updateUI();
                    setVisible(false);
                    try {
                        saveToFile(table);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            getContentPane().add(addButton);
        }
    }

        class Edit extends JFrame {
            private JTextField nameText;
            private JTextField authorText;
            private JTextField yearText;
            private JButton okButton;
            private JLabel nameLabel;
            private JLabel authorLabel;
            private JLabel yearLabel;

            Edit(String[] edit) {
                setTitle("Редактирование");
                setSize(250, 200);
                setLocationRelativeTo(null);
                setResizable(false);
                setLayout(null);

                nameLabel = new JLabel("Имя");
                nameLabel.setBounds(50, 20, 30, 20);
                getContentPane().add(nameLabel);

                nameText = new JTextField(tableModel.getValueAt(table.getSelectedRow(),0));
                nameText.setBounds(80, 20, 100, 20);
                getContentPane().add(nameText);

                authorLabel = new JLabel("Автор");
                authorLabel.setBounds(40, 60, 60, 20);
                getContentPane().add(authorLabel);

                authorText = new JTextField(tableModel.getValueAt(table.getSelectedRow(),1));
                authorText.setBounds(80, 60, 100, 20);
                getContentPane().add(authorText);

                yearLabel = new JLabel("Год");
                yearLabel.setBounds(50, 100, 30, 20);
                getContentPane().add(yearLabel);

                yearText = new JTextField(tableModel.getValueAt(table.getSelectedRow(),2));
                yearText.setBounds(80, 100, 100, 20);
                getContentPane().add(yearText);

                okButton = new JButton("Применить");
                okButton.setBounds(50, 130, 130, 30);
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameText.getText();
                        String author = authorText.getText();
                        String year = yearText.getText();
                        String[] booksInfo = {
                                name, author, year
                        };
                        tableModel.books.set(table.getSelectedRow(), booksInfo);
                        table.updateUI();
                        setVisible(false);
                        try {
                            saveToFile(table);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                getContentPane().add(okButton);
            }
        }

    public void saveToFile(JTable table) throws IOException {
        BufferedWriter bfw = new BufferedWriter(new FileWriter("library.txt"));
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                bfw.write((String) (table.getValueAt(i, j)));
                bfw.write("\t");
            }
            bfw.newLine();
        }
        bfw.close();
    }

    public void readFromFile() {
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new FileReader("library.txt"));
            String line = bfr.readLine();
            while (line != null) {
                String[] book = line.split("\t");
                tableModel.books.add(book);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

