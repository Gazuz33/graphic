package com.company;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")

public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }
    public int getColumnCount() {
// В данной модели 3 столбца
        return 3;
    }
    public int getRowCount() {
// Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to-from)/step)).intValue()+1;//??
    }

    public Object getValueAt(int row, int col) {
// Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;
        Double result;
        if (col == 0) {
// Если запрашивается значение 1-го столбца, то это X
            return x;
        } else {
// Если запрашивается значение 2-го столбца, то это значение многочлена
            if (col == 1) {
                result = coefficients[0];
                for (int i = 0; i < coefficients.length - 1; i++) {
                    result = result * x + coefficients[i + 1];
                }
                return result;
            } else {
// 3-й столбец
                result = coefficients[0];
                for (int i = 0; i < coefficients.length - 1; i++)
                {
                    result = result * x + coefficients[i + 1];
                }
                Double drob = result-result.intValue();
                Double drobMain =(double)Math.round(drob * 100) / 100;
                Double E = Math.pow (10,-6);
                int counter = 0; int prov = 0;
                while (drob>E){
                    drob*=10;
                    drob = drob - drob.intValue();
                    counter++;
                }
                drobMain*=Math.pow(10,counter);
                if (Math.abs(Math.sqrt(drobMain) - ((Double)Math.sqrt(drobMain)).intValue())<E)
                    {
                        prov++;
                        return true;
                    }else return false;
            }
        }
    }
    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 1:
// Название 2-го столбца
                return "Значение многочлена";
            default:
// Название 3-го столбца
                return "Является ли дробная часть полным квадратом?";
        }
    }
    public Class<?> getColumnClass(int col) {
        if (col!=2) {
            return Double.class;
        } else {
            return Boolean.class;
        }
    }
}