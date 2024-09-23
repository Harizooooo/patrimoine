package patrimoine.hei;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import patrimoine.hei.Controller.PatrimoineController;
import patrimoine.hei.Model.Patrimoine;
import patrimoine.hei.Service.PatrimoineService;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class PatrimoineControllerTest {

    @InjectMocks
    private PatrimoineController patrimoineController;

    @Mock
    private PatrimoineService patrimoineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatrimoineSuccess() throws IOException {
        Patrimoine patrimoine = new Patrimoine("John Doe", LocalDateTime.now());
        when(patrimoineService.getPatrimoine("1")).thenReturn(patrimoine);
        ResponseEntity<Patrimoine> response = patrimoineController.getPatrimoine("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getPossesseur());
    }
    @Test
    void testGetPatrimoineNotFound() throws IOException {
        when(patrimoineService.getPatrimoine("2")).thenReturn(null);
        ResponseEntity<Patrimoine> response = patrimoineController.getPatrimoine("2");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateOrUpdatePatrimoine() throws IOException {
        Patrimoine patrimoine = new Patrimoine("John Doe", LocalDateTime.now());
        ResponseEntity<Void> response = patrimoineController.createOrUpdatePatrimoine("1", patrimoine);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(patrimoineService, times(1)).saveOrUpdatePatrimoine(eq("1"), any(Patrimoine.class));
    }

    @Test
    void testCreateOrUpdatePatrimoineIOException() throws IOException {
        Patrimoine patrimoine = new Patrimoine("John Doe", LocalDateTime.now());
        doThrow(new IOException()).when(patrimoineService).saveOrUpdatePatrimoine("1", patrimoine);
        ResponseEntity<Void> response = patrimoineController.createOrUpdatePatrimoine("1", patrimoine);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetPatrimoineIOException() throws IOException {
        when(patrimoineService.getPatrimoine("1")).thenThrow(new IOException());
        ResponseEntity<Patrimoine> response = patrimoineController.getPatrimoine("1");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    void testCreateOrUpdatePatrimoineException() throws IOException {
        Patrimoine patrimoine = new Patrimoine("Jane Doe", LocalDateTime.now());
        doThrow(new IOException()).when(patrimoineService).saveOrUpdatePatrimoine("1", patrimoine);

        ResponseEntity<Void> response = patrimoineController.createOrUpdatePatrimoine("1", patrimoine);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetPatrimoineWithInvalidId() throws IOException {
        ResponseEntity<Patrimoine> response = patrimoineController.getPatrimoine("invalid_id");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void testCreateOrUpdatePatrimoineWithNull() throws IOException {
        ResponseEntity<Void> response = patrimoineController.createOrUpdatePatrimoine("1", null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(patrimoineService, never()).saveOrUpdatePatrimoine(anyString(), any(Patrimoine.class));
    }


    @Test
    void testCreateOrUpdatePatrimoineWithValidData() throws IOException {
        Patrimoine patrimoine = new Patrimoine("Alice Smith", LocalDateTime.now());
        ResponseEntity<Void> response = patrimoineController.createOrUpdatePatrimoine("1", patrimoine);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(patrimoineService, times(1)).saveOrUpdatePatrimoine(eq("1"), eq(patrimoine));
    }
    @Test
    void testCreateOrUpdatePatrimoineAlreadyExists() throws IOException {
        Patrimoine patrimoine = new Patrimoine("Bob Brown", LocalDateTime.now());
        when(patrimoineService.getPatrimoine("1")).thenReturn(patrimoine);
        ResponseEntity<Void> response = patrimoineController.createOrUpdatePatrimoine("1", patrimoine);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(patrimoineService, times(1)).saveOrUpdatePatrimoine(eq("1"), eq(patrimoine));
    }
    @Test
    void testGetPatrimoineServiceException() throws IOException {
        when(patrimoineService.getPatrimoine("1")).thenThrow(new IOException("Service error"));
        ResponseEntity<Patrimoine> response = patrimoineController.getPatrimoine("1");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

}
