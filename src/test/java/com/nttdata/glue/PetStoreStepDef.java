package com.nttdata.glue;

import com.nttdata.model.Order;
import com.nttdata.steps.PetStoreSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

public class PetStoreStepDef {
    @Steps
    PetStoreSteps tienda;
    Order order;

    @Given("la url es {string}")
    public void laUrlEs(String url) {
        tienda.definirURL(url);
    }


    @When("envío una solicitud POST con siguiente body para la orden")
    public void envioUnaSolicitudPOSTConSiguienteBodyParaLaOrden(DataTable dataTable) {
        order = tienda.dataOrden(dataTable);
        tienda.crearOrden(order);
    }

    @Then("valido el response con codigo de estado igual a {int}")
    public void validoElResponseConCodigoDeEstadoIgualA(int statusCode) {
        tienda.validacionRespuesta(statusCode);
    }

    @And("el response debe contener los mismos datos de la orden creada")
    public void elResponseDebeContenerLosMismosDatosDeLaOrdenCreada() {
        tienda.validarRespuesta(order);
    }

    @When("envío una solicitud GET a {string}")
    public void envioUnaSolicitudGETA(String pathUrl) {
        tienda.obtenerOrdenPorId(pathUrl);
    }
    @And("el response debe contener los mismos datos de la orden consultada")
    public void elResponseDebeContenerLosMismosDatosDeLaOrdenConsultada(DataTable dataTable) {
        order = tienda.dataOrden(dataTable);
        tienda.validarRespuesta(order);
    }
}
