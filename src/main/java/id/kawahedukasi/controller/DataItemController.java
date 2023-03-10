package id.kawahedukasi.controller;

import com.opencsv.exceptions.CsvValidationException;
import id.kawahedukasi.DTO.FileFormDTO;
import id.kawahedukasi.model.DataItem;
import id.kawahedukasi.service.DataItemService;
import id.kawahedukasi.service.ExportService;
import id.kawahedukasi.service.ImportService;
import net.sf.jasperreports.engine.JRException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DataItemController {

    @Inject
    DataItemService dataItemService;

    @Inject
    ExportService exportService;

    @Inject
    ImportService importService;

    @GET
    public Response get() {
        return DataItemService.get();
    }
    @GET
    @Path("/export/pdf")
    @Produces("application/pdf")
    public Response export() throws JRException {
        return exportService.exportPdfItem();
    }
    @GET
    @Path("/export/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportExcel() throws JRException, IOException {
        return exportService.exportExcelItem();
    }
    @GET
    @Path("/export/csv")
    @Produces("text/csv")
    public Response exportCSV() throws IOException {
        return exportService.exportCsvItem();
    }

    @GET
    @Path("/{id}")
    public Response getItemById(@PathParam("id") Long id) {
    return DataItemService.getItemById(id);
    }

    @POST
    public Response post(Map<String, Object> request) {
        return DataItemService.post(request);
    }
    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcel(@MultipartForm FileFormDTO request) {
        try{
            return importService.importExcel(request);
        } catch (IOException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/import/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCSV(@MultipartForm FileFormDTO request) {
        try{
            return importService.importCSV(request);
        } catch (IOException | CsvValidationException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Long id, Map<String, Object> request) {
        return DataItemService.put(id,request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id, Map<String, Object> request) {
        return DataItemService.delete(id,request);
    }

}
