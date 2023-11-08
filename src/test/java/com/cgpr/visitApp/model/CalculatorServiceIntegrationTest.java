package com.cgpr.visitApp.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cgpr.visitApp.services.CalculatorService;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceIntegrationTest {

	@InjectMocks
    private CalculatorService calculatorService;

	@Test
    public void testAdd() {
        // Configuration du mock pour qu'il renvoie 5 lors de l'appel à add(2, 3)
       // when(calculatorService.add(2, 3)).thenReturn(5);

        int result = calculatorService.add(2, 3);
        assertEquals(5, result);
    }
}
