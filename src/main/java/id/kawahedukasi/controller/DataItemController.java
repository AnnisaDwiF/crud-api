package id.kawahedukasi.controller;

import id.kawahedukasi.model.DataItem;
import id.kawahedukasi.service.DataItemService;
import id.kawahedukasi.service.ExportService;
import net.sf.jasperreports.engine.JRException;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @GET
    public Response get() {
        return DataItemService.get();
    }
    @GET
    @Path("/export")
    @Produces("application/pdf")
    public Response export() throws JRException {
        return exportService.exportItem();
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
