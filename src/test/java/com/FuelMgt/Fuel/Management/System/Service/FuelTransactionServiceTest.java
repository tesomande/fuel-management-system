package com.FuelMgt.Fuel.Management.System.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.web.server.ResponseStatusException;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;
import com.FuelMgt.Fuel.Management.System.Entity.FuelTransaction;
import com.FuelMgt.Fuel.Management.System.Repository.FuelStockRepository;
import com.FuelMgt.Fuel.Management.System.Repository.FuelTransactionRepository;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class FuelTransactionServiceTest {

    @Mock
    private FuelTransactionRepository transactionRepo;

    @Mock
    private FuelStockRepository stockRepo;

    @InjectMocks
    private FuelTransactionService service;

    private FuelStock stock;

    @BeforeEach
    void setUp() {
        stock = new FuelStock();
        stock.setId(1L);
        stock.setFuelType("Diesel");
        stock.setQuantityLiters(1000.0);
    }

    // ---------------------------------------------------------
    // 1. SUCCESSFUL TRANSACTION
    // ---------------------------------------------------------

    @Test
    void shouldSaveTransactionWhenStockIsSufficient() {

        FuelTransaction tx = new FuelTransaction();
        tx.setFuelType("Diesel");
        tx.setLiters(200.0);

        when(stockRepo.findByFuelType("Diesel"))
                .thenReturn(stock);

        when(transactionRepo.save(any(FuelTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FuelTransaction result = service.save(tx);

        assertNotNull(result);

        assertEquals("Diesel", result.getFuelType());

        assertEquals(200.0, result.getLiters());

        assertNotNull(result.getTransactionDate());

        assertEquals(800.0, stock.getQuantityLiters());

        verify(stockRepo).save(stock);

        verify(transactionRepo).save(tx);
    }

    // ---------------------------------------------------------
    // 2. NULL FUEL TYPE
    // ---------------------------------------------------------

    @Test
    void shouldRejectTransactionWhenFuelTypeIsNull() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType(null);
        tx.setLiters(100.0);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.save(tx)
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        verifyNoInteractions(stockRepo);
        verifyNoInteractions(transactionRepo);
    }

    // ---------------------------------------------------------
    // 3. BLANK FUEL TYPE
    // ---------------------------------------------------------

    @Test
    void shouldRejectTransactionWhenFuelTypeIsBlank() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("   ");
        tx.setLiters(100.0);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.save(tx)
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        verifyNoInteractions(stockRepo);
        verifyNoInteractions(transactionRepo);
    }

    // ---------------------------------------------------------
    // 4. NULL LITERS
    // ---------------------------------------------------------

    @Test
    void shouldRejectTransactionWhenLitersIsNull() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("Diesel");
        tx.setLiters(null);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.save(tx)
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        verifyNoInteractions(stockRepo);
        verifyNoInteractions(transactionRepo);
    }

    // ---------------------------------------------------------
    // 5. ZERO LITERS
    // ---------------------------------------------------------

    @Test
    void shouldRejectTransactionWhenLitersIsZero() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("Diesel");
        tx.setLiters(0.0);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.save(tx)
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        verifyNoInteractions(stockRepo);
        verifyNoInteractions(transactionRepo);
    }

    // ---------------------------------------------------------
    // 6. NEGATIVE LITERS
    // ---------------------------------------------------------

    @Test
    void shouldRejectTransactionWhenLitersIsNegative() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("Diesel");
        tx.setLiters(-50.0);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.save(tx)
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        verifyNoInteractions(stockRepo);
        verifyNoInteractions(transactionRepo);
    }

    // ---------------------------------------------------------
    // 7. FUEL STOCK NOT FOUND
    // ---------------------------------------------------------

    @Test
    void shouldRejectTransactionWhenFuelStockDoesNotExist() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("Kerosene");
        tx.setLiters(100.0);

        when(stockRepo.findByFuelType("Kerosene"))
                .thenReturn(null);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.save(tx)
                );

        assertEquals(
                404,
                exception.getStatusCode().value()
        );

        verify(stockRepo)
                .findByFuelType("Kerosene");

        verify(stockRepo, never())
                .save(any(FuelStock.class));

        verifyNoInteractions(transactionRepo);
    }

    // ---------------------------------------------------------
    // 8. INSUFFICIENT STOCK
    // ---------------------------------------------------------

    @Test
    void shouldRejectTransactionWhenStockIsInsufficient() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("Diesel");
        tx.setLiters(1200.0);

        when(stockRepo.findByFuelType("Diesel"))
                .thenReturn(stock);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.save(tx)
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        assertEquals(
                1000.0,
                stock.getQuantityLiters()
        );

        verify(stockRepo, never())
                .save(any(FuelStock.class));

        verifyNoInteractions(transactionRepo);
    }

    // ---------------------------------------------------------
    // 9. EXISTING TRANSACTION DATE
    // ---------------------------------------------------------

    @Test
    void shouldKeepExistingTransactionDate() {

        LocalDateTime existingDate =
                LocalDateTime.of(2026, 8, 13, 10, 30);

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("Diesel");
        tx.setLiters(100.0);
        tx.setTransactionDate(existingDate);

        when(stockRepo.findByFuelType("Diesel"))
                .thenReturn(stock);

        when(transactionRepo.save(any(FuelTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FuelTransaction result = service.save(tx);

        assertEquals(
                existingDate,
                result.getTransactionDate()
        );

        assertEquals(
                900.0,
                stock.getQuantityLiters()
        );

        verify(stockRepo).save(stock);

        verify(transactionRepo).save(tx);
    }

    // ---------------------------------------------------------
    // 10. GET ALL TRANSACTIONS
    // ---------------------------------------------------------

    @Test
    void shouldGetAllTransactionsWithPagination() {

        FuelTransaction tx = new FuelTransaction();

        tx.setFuelType("Diesel");
        tx.setLiters(100.0);

        Page<FuelTransaction> page =
                new PageImpl<>(
                        Collections.singletonList(tx)
                );

        when(transactionRepo.findAll(PageRequest.of(0, 10)))
                .thenReturn(page);

        Page<FuelTransaction> result =
                service.getAll(0, 10);

        assertNotNull(result);

        assertEquals(1, result.getTotalElements());

        verify(transactionRepo)
                .findAll(PageRequest.of(0, 10));
    }
}