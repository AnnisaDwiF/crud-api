package id.kawahedukasi.service;

import id.kawahedukasi.DTO.FileFormDTO;
import id.kawahedukasi.model.DataItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
}
