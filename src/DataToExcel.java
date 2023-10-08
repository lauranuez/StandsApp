import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class DataToExcel {
    private static String[] days = {"lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"};
    private static int nat;
    private static int aat;
    private static ArrayList<Flight> flightsInExcel;
    private static ArrayList<Aerolinea> aerolineas;
    private static Workbook workbook;

    public static void generateExcel(ArrayList<Flight> flightListWeek, String numWeek, ArrayList<LocalTime> columnNames, File selectedDirectory, File filePath) throws FileNotFoundException {
        aerolineas = LoadStands.loadCias(filePath);

        workbook = new XSSFWorkbook();

        final String nombreArchivo = "AsignacionWB.xlsx";

        Sheet hojaNAT = workbook.createSheet("NAT");
        head(hojaNAT, numWeek, "NAT");
        Sheet hojaAAT = workbook.createSheet("AAT");
        head(hojaAAT, numWeek, "AAT");

        writeData(hojaNAT, hojaAAT, flightListWeek, numWeek, columnNames);
        //Crear archivo
        File directorioActual = new File(selectedDirectory, nombreArchivo);
        String ubicacionArchivoSalida = directorioActual.getAbsolutePath();
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(ubicacionArchivoSalida);
            workbook.write(outputStream);
            workbook.close();
            JOptionPane.showMessageDialog(null, "Libro guardado correctamente",
                    "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error de filenotfound: cierra el excel y vuelve a intentarlo",
                    "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error de IOException",
                    "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void head(Sheet sheet, String numWeek, String terminal){
        XSSFCellStyle primeraCabecera = EstilosExcel.getAzulGrisaceo(workbook);
        XSSFCellStyle segCabecera = EstilosExcel.getAzulClaro(workbook);
        Row firstRow = sheet.createRow(0);
        Cell ter = firstRow.createCell(0);
        ter.setCellValue(terminal);
        ter.setCellStyle(primeraCabecera);
        Cell info = firstRow.createCell(1);
        info.setCellStyle(primeraCabecera);
        info.setCellValue("S"+ numWeek + "Asignación WideBody");
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                1,
                9
        ));
        Cell lunes = firstRow.createCell(10);
        lunes.setCellValue("Lunes");
        lunes.setCellStyle(primeraCabecera);
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                10,
                13
        ));
        Cell martes = firstRow.createCell(14);
        martes.setCellValue("Martes");
        martes.setCellStyle(primeraCabecera);
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                14,
                17
        ));
        Cell mie = firstRow.createCell(18);
        mie.setCellValue("Miercoles");
        mie.setCellStyle(primeraCabecera);
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                18,
                21
        ));
        Cell jue = firstRow.createCell(22);
        jue.setCellValue("Jueves");
        jue.setCellStyle(primeraCabecera);
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                22,
                25
        ));
        Cell vie = firstRow.createCell(26);
        vie.setCellValue("Viernes");
        vie.setCellStyle(primeraCabecera);
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                26,
                29
        ));
        Cell sab = firstRow.createCell(30);
        sab.setCellValue("Sabado");
        sab.setCellStyle(primeraCabecera);
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                30,
                33
        ));
        Cell dom = firstRow.createCell(34);
        dom.setCellValue("Domingo");
        dom.setCellStyle(primeraCabecera);
        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                34,
                37
        ));
        Row secondRow = sheet.createRow(1);
        Cell AAHH = secondRow.createCell(0);
        AAHH.setCellValue("AAHH");
        AAHH.setCellStyle(segCabecera);
        Cell aerolinea = secondRow.createCell(1);
        aerolinea.setCellValue("Aerolínea");
        aerolinea.setCellStyle(segCabecera);
        Cell nombreAerolinea = secondRow.createCell(2);
        nombreAerolinea.setCellValue("Nombre aerolínea");
        nombreAerolinea.setCellStyle(segCabecera);
        Cell lineaLlegada = secondRow.createCell(3);
        lineaLlegada.setCellValue("Linea llegada");
        lineaLlegada.setCellStyle(segCabecera);
        Cell lineaSalida = secondRow.createCell(4);
        lineaSalida.setCellValue("Línea salida");
        lineaSalida.setCellStyle(segCabecera);
        Cell horaLlegada = secondRow.createCell(5);
        horaLlegada.setCellValue("Hora llegada UTC");
        horaLlegada.setCellStyle(segCabecera);
        Cell horaSalida = secondRow.createCell(6);
        horaSalida.setCellValue("Hora salida UTC");
        horaSalida.setCellStyle(segCabecera);
        Cell origen = secondRow.createCell(7);
        origen.setCellValue("Origen");
        origen.setCellStyle(segCabecera);
        Cell destino = secondRow.createCell(8);
        destino.setCellValue("Destino");
        destino.setCellStyle(segCabecera);
        Cell tipoServicio = secondRow.createCell(9);
        tipoServicio.setCellValue("Tipo de servicio");
        tipoServicio.setCellStyle(segCabecera);
        for (int i = 10; i < 38; i++){
            Cell avo = secondRow.createCell(i);
            avo.setCellValue("Avo.");
            avo.setCellStyle(segCabecera);
            i++;
            Cell stand = secondRow.createCell(i);
            stand.setCellValue("Stand");
            stand.setCellStyle(segCabecera);
            i++;
            Cell gate = secondRow.createCell(i);
            gate.setCellValue("Gate");
            gate.setCellStyle(segCabecera);
            i++;
            Cell pernocta = secondRow.createCell(i);
            pernocta.setCellValue("Pernocta");
            pernocta.setCellStyle(segCabecera);
        }
    }

    private static void writeData(Sheet sheetNAT, Sheet sheetAAT, ArrayList<Flight> flightListWeek, String numWeek, ArrayList<LocalTime> columnNames){
        ArrayList<Flight> flights = flightListWeek;
        ArrayList<Flight> mtos = new ArrayList<>();
        ArrayList<Flight> pernoctas = new ArrayList<>();
        flightsInExcel = new ArrayList<>();
        nat = 2;
        aat = 2;

        flights.sort(Comparator.comparing(Flight::getTimeA));

        for (Flight flight : flights) {
            Boolean addFlight = false;
            Boolean searchFlight = false;
            if (flight.numWeek != Integer.parseInt(numWeek)) {
                flight.as = "ZZZ";
                flight.AA = "ZZZ";
                pernoctas.add(flight);
                continue;
            }
            if (flight.AH.equals("DIM")){
                mtos.add(flight);
                continue;
            }
            if (flightsInExcel.size() > 0) {
                for (Flight flightSaved : flightsInExcel) {
                    if (flightSaved.id2.equals(flight.id2)) {
                        if (!flight.carreteo.equals("Y") || flight.stand2 != null) {
                            Sheet targetSheet = (flight.terminal.equals("NAT")) ? sheetNAT : sheetAAT;
                            Row row = targetSheet.getRow(flightSaved.rowExcel);
                            flight.rowExcel = flightSaved.rowExcel;
                            addFlight = writeFlightData(flight, row);
                            searchFlight = true;
                        }
                    }
                }
                if (!searchFlight){
                    if (!flight.carreteo.equals("Y") || flight.stand2 != null) {
                        addFlight = newRowData(flight,sheetNAT, sheetAAT);
                    }
                }
            }
            else{
                addFlight = newRowData(flight,sheetNAT, sheetAAT);
            }
            if(addFlight){
                flightsInExcel.add(flight);
            }
        }
        if(pernoctas.size() > 0){
            for(Flight pernocta:pernoctas){
                Boolean addMTO = newRowData(pernocta,sheetNAT, sheetAAT);
                if(addMTO){
                    flightsInExcel.add(pernocta);
                }
            }
        }
        if(mtos.size() > 0){
            for(Flight mto:mtos){
                Boolean addMTO = newRowData(mto,sheetNAT, sheetAAT);
                if(addMTO){
                    flightsInExcel.add(mto);
                }
            }
        }
    }

    private static Boolean newRowData(Flight flight, Sheet sheetNAT, Sheet sheetAAT){
        Sheet targetSheet = (flight.terminal.equals("NAT")) ? sheetNAT : sheetAAT;
        int targetRowNum = (flight.terminal.equals("NAT")) ? nat++ : aat++;
        Row row = targetSheet.createRow(targetRowNum);
        flight.rowExcel = targetRowNum;
        Boolean setFlight = writeFlightData(flight, row);
        return setFlight;
    }

    private static Boolean writeFlightData(Flight flight, Row row) {
        XSSFCellStyle rojoAH = EstilosExcel.rojoAH(workbook);
        XSSFCellStyle azulAH = EstilosExcel.azulAH(workbook);
        XSSFCellStyle morado = EstilosExcel.morado(workbook);
        XSSFCellStyle verdeAH = EstilosExcel.verdeAH(workbook);
        XSSFCellStyle bordes = EstilosExcel.bordes(workbook);
        XSSFCellStyle amarilloP = EstilosExcel.amarilloP(workbook);
        XSSFCellStyle verde = EstilosExcel.verde(workbook);


        try {
            Cell AAHH = row.createCell(0);
            AAHH.setCellValue(flight.AH);
            Cell aerolinea = row.createCell(1);
            aerolinea.setCellValue(flight.airlineA);
            Cell nombreAerolinea = row.createCell(2);
            for (Aerolinea cia:aerolineas){
                if (flight.airlineA.equals(cia.OACI)){
                    nombreAerolinea.setCellValue(cia.name);
                }
            }
            if(flight.AH.equals("IBE") || flight.AH.equals("DIM") ){
                AAHH.setCellStyle(rojoAH);
                aerolinea.setCellStyle(rojoAH);
                nombreAerolinea.setCellStyle(rojoAH);
            } else if (flight.AH.equals("GK5")){
                AAHH.setCellStyle(azulAH);
                aerolinea.setCellStyle(azulAH);
                nombreAerolinea.setCellStyle(azulAH);
            } else if (flight.AH.equals("HS1")){
                AAHH.setCellStyle(verdeAH);
                aerolinea.setCellStyle(verdeAH);
                nombreAerolinea.setCellStyle(verdeAH);
            }
            Cell lineaLlegada = row.createCell(3);
            lineaLlegada.setCellValue(flight.numA);
            lineaLlegada.setCellStyle(bordes);
            Cell lineaSalida = row.createCell(4);
            lineaSalida.setCellValue(flight.numD);
            lineaSalida.setCellStyle(bordes);
            Cell horaLlegada = row.createCell(5);
            horaLlegada.setCellValue(flight.timeA.toString());
            horaLlegada.setCellStyle(bordes);
            Cell horaSalida = row.createCell(6);
            horaSalida.setCellValue(flight.timeD.toString());
            horaSalida.setCellStyle(bordes);
            Cell origen = row.createCell(7);
            if (flight.origenAirport.equals(flight.AA)) {
                origen.setCellValue(flight.origenAirport);
            } else {
                origen.setCellValue(flight.origenAirport + "/" + flight.AA);
            }
            origen.setCellStyle(bordes);
            Cell destino = row.createCell(8);
            if (flight.as.equals(flight.af)) {
                destino.setCellValue(flight.af);
            } else {
                destino.setCellValue(flight.as + "/" + flight.af);
            }
            destino.setCellStyle(bordes);
            Cell serviceType = row.createCell(9);
            serviceType.setCellValue(flight.flightTypeA);
            serviceType.setCellStyle(bordes);
            int i = 10;
            for (String day : days) {
                if (flight.dayWeek.equals(day)) {
                    Cell avo = row.createCell(i);
                    avo.setCellValue(flight.aircraftD);
                    avo.setCellStyle(bordes);
                    i++;
                    Cell stand = row.createCell(i);
                    if (flight.stand2 != null) {
                        stand.setCellValue(flight.stand + "/" + flight.stand2);
                        stand.setCellStyle(morado);
                    } else {
                        stand.setCellValue(flight.stand);
                        if(flight.stand.equals("340") || flight.stand.equals("245")){
                            stand.setCellStyle(verde);
                        }
                        else{
                            stand.setCellStyle(bordes);
                        }
                    }
                    i++;
                    Cell gate = row.createCell(i);
                    gate.setCellValue(flight.puerta);
                    gate.setCellStyle(bordes);
                    i++;
                    Cell pernocta = row.createCell(i);
                    pernocta.setCellValue(flight.pernocta);
                    if(flight.pernocta !=0){
                        pernocta.setCellStyle(rojoAH);
                    }
                    else{
                        pernocta.setCellStyle(bordes);
                    }
                    i++;
                } else {
                    i=i+4;
                }
            }
            return true;
        }catch (NullPointerException e){
            return false;
        }

    }
}