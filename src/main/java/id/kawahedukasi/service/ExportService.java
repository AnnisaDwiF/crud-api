package id.kawahedukasi.service;

import com.opencsv.CSVWriter;
import id.kawahedukasi.model.DataItem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ExportService {

    public Response exportPdfItem() throws JRException {

        //load template jasper
        File file = new File("src/main/resources/TemplateDataItem.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(DataItem.listAll());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        byte[] japerResult = JasperExportManager.exportReportToPdf(jasperPrint);

        return Response.ok().type("application/pdf").entity(japerResult).build();

    }
    public Response exportExcelItem() throws IOException, JRException {

        ByteArrayOutputStream outputStream = excelItem();

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment; filename=\"item_list_excel.xlsx\"")
                .entity(outputStream.toByteArray()).build();

    }
    public ByteArrayOutputStream excelItem() throws JRException, IOException {
        //get all data Item
        List<DataItem> dataItemList = DataItem.listAll();

        //create new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //create sheet
        XSSFSheet sheet = workbook.createSheet("data");

        //set header
        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("count");
        row.createCell(3).setCellValue("price");
        row.createCell(4).setCellValue("type");
        row.createCell(5).setCellValue("description");
        row.createCell(6).setCellValue("createdAt");
        row.createCell(7).setCellValue("updatedAt");

        //set data
        for(DataItem dataItem : dataItemList){
            row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(dataItem.id);
            row.createCell(1).setCellValue(dataItem.name);
            row.createCell(2).setCellValue(dataItem.count);
            row.createCell(3).setCellValue(dataItem.price);
            row.createCell(4).setCellValue(dataItem.type);
            row.createCell(5).setCellValue(dataItem.description);
            row.createCell(6).setCellValue(dataItem.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
            row.createCell(7).setCellValue(dataItem.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }

    public Response exportCsvItem() throws IOException {
        //get all data peserta
        List<DataItem> dataItemList = DataItem.listAll();

        File file = File.createTempFile("temp", "");

        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);

        String[] headers = {"id", "name", "count", "price", "type", "description", "createdAt", "updateAt"};
        writer.writeNext(headers);
        for(DataItem dataItem : dataItemList){
            String[] data = {
                    dataItem.id.toString(),
                    dataItem.name,
                    String.valueOf(dataItem.count),
                    String.valueOf(dataItem.price),
                    dataItem.type,
                    dataItem.description,
                    dataItem.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
                    dataItem.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            };
            writer.writeNext(data);
        }

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("text/csv")
                .header("Content-Disposition", "attachment; filename=\"item_list_csv.csv\"")
                .entity(file).build();

    }
}
