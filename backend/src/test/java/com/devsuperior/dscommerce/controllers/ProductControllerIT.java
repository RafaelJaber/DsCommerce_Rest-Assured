package com.devsuperior.dscommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.tests.ProductFactory;
import com.devsuperior.dscommerce.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Tag("IT")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private Long existingProductId, nonExistingProductId;
    private Product product;
    private ProductDTO productDTO;
    private String productName;
    private String bearerTokenAdmin, bearerTokenClient;


    @BeforeEach
    void setUp() throws Exception {
        existingProductId = 1L;
        nonExistingProductId = 999L;
        productName = "MacBook";
        product = ProductFactory.createProduct(productName);
        productDTO = new ProductDTO(product);

        String clientUsername = "maria@gmail.com";
        String clientPassword = "123456";
        String adminUsername = "alex@gmail.com";
        String adminPassword = "123456";

        bearerTokenClient = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        bearerTokenAdmin = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {
        ResultActions result = mockMvc.perform(get("/products", productName)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {
        ResultActions result = mockMvc.perform(get("/products?name={productName}", productName)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(3));
    }

    @Test
    public void insertShouldReturnProductDTOCreatedWhenAdminLogged() throws Exception {
        productDTO.setId(null);
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result = mockMvc.perform(post("/products", productName)
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
        productDTO.setId(null);
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result = mockMvc.perform(post("/products", productName)
                .header("Authorization", "Bearer " + bearerTokenClient)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidBody() throws Exception {
        productDTO.setId(null);
        productDTO.setName(null);
        productDTO.setPrice(-1.1);
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result = mockMvc.perform(post("/products", productName)
                .header("Authorization", "Bearer " + bearerTokenClient)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldReturnProductDTOUpdatedWhenAdminLogged() throws Exception {
        String newName = "NEW_NAME";
        productDTO.setName(newName);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{productId}", existingProductId)
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value(newName));
    }

    @Test
    public void updateShouldReturnNotFoundWhenAdminLoggedAndProductIdNotExists() throws Exception {
        String newName = "NEW_NAME";
        productDTO.setName(newName);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{productId}", nonExistingProductId)
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnForbiddenWhenClientLogged() throws Exception {
        String newName = "NEW_NAME";
        productDTO.setName(newName);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{productId}", nonExistingProductId)
                .header("Authorization", "Bearer " + bearerTokenClient)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldReturnNoContentWhenAdminLoggedAndExistsProductId() throws Exception {
        ResultActions result = mockMvc.perform(delete("/products/{productId}", existingProductId)
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenAdminLoggedAndProductIdNotExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/products/{productId}", nonExistingProductId)
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnForbiddenWhenClientLoggedAndProductIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/products/{productId}", existingProductId)
                .header("Authorization", "Bearer " + bearerTokenClient)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isForbidden());
    }
}
