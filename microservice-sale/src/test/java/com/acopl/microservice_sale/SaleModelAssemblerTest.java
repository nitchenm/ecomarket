package com.acopl.microservice_sale;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;

import com.acopl.microservice_sale.assembler.SaleModelAssembler;
import com.acopl.microservice_sale.dto.SaleDTO;

@WebMvcTest 
class SaleModelAssemblerTest {

    @Autowired
    private SaleModelAssembler assembler;

    @Test
    void toModel_shouldAddLinks() {
        SaleDTO sale = new SaleDTO();
        sale.setId(10L);

        EntityModel<SaleDTO> model = assembler.toModel(sale);

        assertThat(model.getContent()).isEqualTo(sale);
        assertThat(model.getLinks()).isNotEmpty();
        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("branches")).isPresent();
        assertThat(model.getRequiredLink("self").getHref()).contains("/api/v2/sales/10");
    }
}