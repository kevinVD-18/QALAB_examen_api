package com.nttdata.steps;

import com.nttdata.model.Order;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class PetStoreSteps {
    Response response;
    private String URL_BASE;

    public void definirURL(String url) {
        URL_BASE = url;
    }

    public Order dataOrden (DataTable dataTable){
        // Convertiendo DataTable a una lista de maps
        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = dataList.get(0);

        return new Order(
                Long.parseLong(data.get("id")),
                Long.parseLong(data.get("petId")),
                Integer.parseInt(data.get("quantity")),
                data.get("shipDate"),
                data.get("status"),
                Boolean.parseBoolean(data.get("complete"))
        );
    }
    public void crearOrden(Order order) {
        response = RestAssured
                .given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .body(order)
                .baseUri(URL_BASE)
                .post("/store/order")
                .then()
                .log().all()
                .extract().response();
    }

    public void validacionRespuesta(int statusCode) {
        Assert.assertEquals("Validacion de Respuesta", statusCode, response.statusCode());
    }

    public void obtenerOrdenPorId(String pathUrl) {
        response = RestAssured
                .given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .baseUri(URL_BASE)
                .get(pathUrl)
                .then()
                .log().all()
                .extract().response();
    }

    public void validarRespuesta(Order order ) {
        Assert.assertEquals("Validando Id: ", order.getId(), response.jsonPath().getLong("id"));
        Assert.assertEquals("Validando petId: ", order.getPetId(), response.jsonPath().getLong("petId"));
        Assert.assertEquals("Validando quantity: ", order.getQuantity(), response.jsonPath().getInt("quantity"));
        Assert.assertEquals("Validando status: ", order.getStatus(), response.jsonPath().getString("status"));
        Assert.assertEquals("Validando complete: ", order.isComplete(), response.jsonPath().getBoolean("complete"));
    }

}
