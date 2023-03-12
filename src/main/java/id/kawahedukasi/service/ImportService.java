package id.kawahedukasi.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import id.kawahedukasi.DTO.FileFormDTO;
import id.kawahedukasi.model.DataItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ImportService {
    @Transactional
    public Response importExcel(FileFormDTO request) throws IOException {

        //create object array input
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);

        //create new workbook by byteArrayInputStream
        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);

        //get sheet "data"
        XSSFSheet sheet = workbook.getSheetAt(0);

        //remove header excel
        sheet.removeRow(sheet.getRow(0));


        List<DataItem> toPersist = new ArrayList<>();
        //for each row
        for (Row row : sheet) {
            DataItem dataItem = new DataItem();
            dataItem.name = row.getCell(0).getStringCellValue();
            dataItem.count = Integer.valueOf((int) row.getCell(1).getNumericCellValue()).intValue();
            dataItem.price = (double) Double.valueOf(row.getCell(2).getNumericCellValue()).intValue();
            dataItem.type = row.getCell(3).getStringCellValue();
            dataItem.description = row.getCell(4).getStringCellValue();
            toPersist.add(dataItem);
        }
        DataItem.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public Response importCSV(FileFormDTO request) throws IOException, CsvValidationException {

        //create object array input
        File file = File.createTempFile("temp", "");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(request.file);


        CSVReader reader = new CSVReader(new FileReader(file));
        String [] nextLine;
        reader.skip(1);

        List<DataItem> toPersist = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            DataItem dataItem = new DataItem();
            dataItem.name = nextLine[0].trim();
            dataItem.count = Integer.valueOf(nextLine[1].trim());
            dataItem.price = Double.valueOf(nextLine[2].trim());
            dataItem.type = nextLine[3].trim();
            dataItem.description = nextLine[3].trim();
            toPersist.add(dataItem);
        }

        DataItem.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
}
