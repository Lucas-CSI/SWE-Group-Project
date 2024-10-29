package com.example.seaSideEscape;

import com.example.seaSideEscape.service.BillingService;
import com.example.seaSideEscape.controller.BillingController;
import com.example.seaSideEscape.model.Bill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BillingController.class)
public class BillingControllerTest {

    @Mock
    private BillingService billingService;

    @InjectMocks
    private BillingController billingController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPaymentInformationTest() throws Exception {
        Bill bill = new Bill();
        bill.setTotalAmount(new BigDecimal("132.00"));
        bill.setTaxes(new BigDecimal("12.00"));
        bill.setSubTotal(new BigDecimal("120.00"));

        //when(billingService.generateBill(1L)).thenReturn(bill);

        mockMvc.perform(get("/billing/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalAmount", is(132.00)))
                .andExpect(jsonPath("$.taxes", is(12.00)))
                .andExpect(jsonPath("$.totalAmount", is(120.00)));
    }
}
