package panels_and_dialogs;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import imagenes.Imagen;

import java.awt.*;


@SuppressWarnings("serial")
public class Propiedades extends JTable {
	JPopupMenu popupMenu;
	
	static String[] rowsNames = new String[]{"Nombre", "Fecha", "Hora", "Latitude", "Latitude Ref", "Longitude", "Longitude Ref", "Width", "Height"};
	
    public Propiedades(Imagen img) {
        // Crear el modelo de tabla con las columnas "Elemento" y "Valor"
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir la edición solo en la segunda columna (Valor)
//                return column == 1;
            	return false;
            }
            
//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//            	Class<?> ret = super.getColumnClass(columnIndex);
//            	if (columnIndex == 0) ret = int[].class;
//                return ret;
//            }
            
        };
        
        
        tableModel.addColumn("Elemento");
        tableModel.addColumn("Valor");

        // Agregar filas con los elementos y valores iniciales
        
        tableModel.addRow(new Object[]{"Nombre", img.getName()});
        tableModel.addRow(new Object[]{"Fecha", img.getDateO().getDate()});
        tableModel.addRow(new Object[]{"Hora", img.getDateO().getTime()});
        tableModel.addRow(new Object[]{"Latitude", img.getLatitude()});
        tableModel.addRow(new Object[]{"Latitude Ref", img.getLatitudeRef()});
        tableModel.addRow(new Object[]{"Longitude", img.getLongitude()});
        tableModel.addRow(new Object[]{"Longitude Ref", img.getLongitudeRef()});
        tableModel.addRow(new Object[]{"Width", img.getWidth()});
        tableModel.addRow(new Object[]{"Height", img.getHeight()});        
        
        // Establecer el modelo de tabla
        setModel(tableModel);

        // Establecer el fondo y el color de la fuente
        setBackground(Color.DARK_GRAY);
        setForeground(Color.WHITE);

        // Establecer el color de fondo y de selección de las celdas
        setSelectionBackground(Color.GRAY);
        setSelectionForeground(Color.WHITE);

        // Ocultar la cabecera de la tabla
        setShowGrid(false);
        setTableHeader(null);

        // Deshabilitar la edición de la primera columna (Elemento)
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(0).setCellEditor(null);

        setDefaultRenderer(Object.class, new CustomCellRenderer());
        
        setRowHeight(45);
        setBorder(new LineBorder(Color.BLACK,3));
        
     
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dark Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Propiedades table = new Propiedades(new Imagen("TestImages/venta.Tiff"));
        frame.getContentPane().add(new JScrollPane(table));

        frame.setSize(300, 1000);
        frame.setVisible(true);
    }
    
	private class CustomCellRenderer extends DefaultTableCellRenderer {

        private static final int CELL_PADDING = 5;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                Border paddingBorder = BorderFactory.createEmptyBorder(CELL_PADDING, CELL_PADDING, CELL_PADDING, CELL_PADDING);
//                Border rowBorder = BorderFactory.createMatteBorder(ROW_BORDER_THICKNESS, ROW_BORDER_THICKNESS, 0, 0, ROW_BORDER_COLOR);
//                if(row == 6) rowBorder = BorderFactory.createMatteBorder(ROW_BORDER_THICKNESS, ROW_BORDER_THICKNESS, ROW_BORDER_THICKNESS, 0, ROW_BORDER_COLOR);
                jComponent.setBorder(BorderFactory.createCompoundBorder(null, paddingBorder));
            }

            return component;
        }
    }
}
