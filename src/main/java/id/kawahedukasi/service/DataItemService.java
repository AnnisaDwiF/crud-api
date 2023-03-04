package id.kawahedukasi.service;

import id.kawahedukasi.model.DataItem;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DataItemService {

    public static Response get() {
        return Response.status(Response.Status.OK).entity(DataItem.findAll().list()).build();
    }

    public static Response getItemById(@PathParam("id") Long id) {
        DataItem dataItem = DataItem.findById(id);
        return Response.status(Response.Status.OK).entity(DataItem.findAll().list()).build();
    }

    @Transactional
    public static Response post(Map<String, Object> request) {
        DataItem dataItem = new DataItem();
        dataItem.name = request.get("name").toString();
        dataItem.count = Integer.valueOf(request.get("count").toString());
        dataItem.price = Double.valueOf(request.get("price").toString());
        dataItem.type = request.get("type").toString();
        dataItem.description = request.get("description").toString();
        dataItem.createdAt = LocalDateTime.parse(request.get("createdAt").toString());
        dataItem.updatedAt = LocalDateTime.parse(request.get("updatedAt").toString());

        //save to database
        dataItem.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public static Response put(@PathParam("id") Long id, Map<String, Object> request) {
        DataItem dataItem = DataItem.findById(id);
        if(dataItem == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        dataItem.name = request.get("name").toString();
        dataItem.count = Integer.valueOf(request.get("count").toString());
        dataItem.price = Double.valueOf(request.get("price").toString());
        dataItem.type = request.get("type").toString();
        dataItem.description = request.get("description").toString();
        dataItem.createdAt = LocalDateTime.parse(request.get("createdAt").toString());
        dataItem.updatedAt = LocalDateTime.parse(request.get("updatedAt").toString());

        //save to database
        dataItem.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public static Response delete(@PathParam("id") Long id, Map<String, Object> request) {
        DataItem dataItem = DataItem.findById(id);
        if(dataItem == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //save to database
        dataItem.delete();

        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }
}
