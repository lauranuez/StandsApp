import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class EstilosExcel {

    public static XSSFCellStyle getAzulGrisaceo(Workbook workbook){
        XSSFCellStyle primeraCabecera = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor myColor = new XSSFColor(new java.awt.Color(172, 185, 202));
        primeraCabecera.setFillForegroundColor(myColor);
        primeraCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        primeraCabecera.setBorderTop(BorderStyle.THIN);
        primeraCabecera.setBorderBottom(BorderStyle.THIN);
        primeraCabecera.setBorderLeft(BorderStyle.THIN);
        primeraCabecera.setBorderRight(BorderStyle.THIN);

        return  primeraCabecera;
    }

    public static XSSFCellStyle getAzulClaro(Workbook workbook){
        XSSFCellStyle segCabecera = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor azulClaro = new XSSFColor(new java.awt.Color(214, 220, 228));
        segCabecera.setFillForegroundColor(azulClaro);
        segCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        segCabecera.setBorderTop(BorderStyle.THIN);
        segCabecera.setBorderBottom(BorderStyle.THIN);
        segCabecera.setBorderLeft(BorderStyle.THIN);
        segCabecera.setBorderRight(BorderStyle.THIN);

        return segCabecera;
    }

    public static XSSFCellStyle rojoAH(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(255, 204, 204));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle azulAH(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(217, 225, 242));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle verdeAH(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(226, 239, 218));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle morado(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(204, 153, 255));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle rojo(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(255, 0, 0));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle amarilloP(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(255 , 192, 0));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle amarillo(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(255 , 255, 0));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle verde(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor color = new XSSFColor(new java.awt.Color(146 , 208, 80));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }


    public static XSSFCellStyle bordes(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static XSSFCellStyle bordesEsquina(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.MEDIUM);

        return style;
    }
}
