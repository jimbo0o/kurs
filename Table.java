import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class Table extends AbstractTableModel {
    public static ArrayList<String[]> books = new ArrayList<String[]>();
    public static String[] columnsNames = { "Название", "Автор", "Год" };

    public Table() {
        for (int i = 0; i < books.size(); i++) {
            books.add(new String[getColumnCount()]);
        }
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        String[] rows = books.get(rowIndex);
        return rows[columnIndex];
    }

    public void addInfo(String[] row) {
        String[] info = new String[getColumnCount()];
        info = row;
        books.add(info);
    }

    public String getColumnName(int columnIndex) {
        return columnsNames[columnIndex];
    }


}
