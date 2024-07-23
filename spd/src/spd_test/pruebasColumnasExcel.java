package spd_test;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class pruebasColumnasExcel {

    public static void main(String[] args) {
        String nombreArchivoEntrada = "C:/UTILS/1/testColumnasEntrada.xlsx";
        String nombreArchivoSalida = "C:/UTILS/1/testColumnasSalida.xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(nombreArchivoEntrada)) {
            Workbook workbookEntrada = new XSSFWorkbook(fileInputStream);

            // Verificar si hay al menos una hoja en el archivo de entrada
            if (workbookEntrada.getNumberOfSheets() > 0) {
                // Crear un nuevo libro de trabajo para el archivo de salida
                Workbook workbookSalida = new XSSFWorkbook();

                // Ordenar las columnas de izquierda a derecha y copiar al nuevo archivo
                ordenarColumnasSegunNumeros(workbookEntrada.getSheetAt(0), workbookSalida.createSheet());

                // Guardar el nuevo archivo
                try (FileOutputStream fileOutputStream = new FileOutputStream(nombreArchivoSalida)) {
                    workbookSalida.write(fileOutputStream);
                }

                System.out.println("Columnas ordenadas de izquierda a derecha en un nuevo archivo.");
            } else {
                System.out.println("El archivo de entrada no contiene hojas.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ordenarColumnasSegunNumeros(Sheet sheetEntrada, Sheet sheetSalida) {
        int numRows = sheetEntrada.getPhysicalNumberOfRows();

        // Crear un mapa para almacenar las celdas ordenadas por número de columna
        Map<Integer, List<Cell>> sortedCells = new TreeMap<>();

        // Obtener los números de la primera fila
        Row firstRow = sheetEntrada.getRow(0);
        for (Cell cell : firstRow) {
            sortedCells.put((int) cell.getNumericCellValue(), new ArrayList<>());
        }

        // Iterar sobre las filas y recolectar las celdas ordenadas en el mapa
        for (int i = 0; i < numRows; i++) {
            Row rowEntrada = sheetEntrada.getRow(i);

            for (Cell cell : rowEntrada) {
                sortedCells.get((int) firstRow.getCell(cell.getColumnIndex()).getNumericCellValue()).add(cell);
            }
        }

        // Crear una nueva fila con las celdas ordenadas en el nuevo archivo
        for (Map.Entry<Integer, List<Cell>> entry : sortedCells.entrySet()) {
            int columnIndex = entry.getKey();
            List<Cell> cells = entry.getValue();

            // Crear una nueva fila con las celdas ordenadas en el nuevo archivo
            Row newRow = sheetSalida.createRow(sheetSalida.getPhysicalNumberOfRows());
            for (Cell sourceCell : cells) {
                Cell newCell = newRow.createCell(sourceCell.getColumnIndex(), sourceCell.getCellType());

                switch (sourceCell.getCellType()) {
                    case NUMERIC:
                        newCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case STRING:
                        newCell.setCellValue(sourceCell.getStringCellValue());
                        break;
                    case BLANK:
                        // Puedes manejar celdas en blanco si es necesario
                        break;
                    // Puedes manejar otros tipos de celda si es necesario
                    default:
                        break;
                }
            }
        }
    }
}
