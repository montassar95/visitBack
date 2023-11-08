package com.cgpr.visitApp.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cgpr.visitApp.services.CalculatorService;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

	@InjectMocks
	CalculatorService calculatorService ;
    @Test
    public void testAdd() {
        
        int result = calculatorService.add(2, 3);
        assertEquals(5, result);
    }
}