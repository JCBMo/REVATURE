import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.projects.dao.AccountDAO;
import com.revature.projects.model.Account;
import com.revature.projects.services.AccountSERVICES;

public class NewAccountTest {
   
    private AccountDAO mockAccountDAO;
    private AccountSERVICES accountService;

    @BeforeEach
    void setUp() {
        mockAccountDAO = Mockito.mock(AccountDAO.class);
        accountService = new AccountSERVICES(mockAccountDAO);
    }

    @Test
    void testAddAccount_Success() throws SQLException {
        Account newAccount = new Account(null, "test@email.com", "password123", true);
        Account createdAccount = new Account(1, "test@email.com", "password123", true);
        when(mockAccountDAO.createAccount(newAccount)).thenReturn(createdAccount);
        Account result = accountService.addAccount(newAccount);
        assertNotNull(result);
        assertEquals(1, result.getIdAccount());
        verify(mockAccountDAO, times(1)).createAccount(newAccount);
    }

    @Test
    void testAddAccount_Fails() throws SQLException {
        Account newAccount = new Account(null, "fail@email.com", "pass", false);
        when(mockAccountDAO.createAccount(newAccount)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            accountService.addAccount(newAccount);
        });

        verify(mockAccountDAO, times(1)).createAccount(newAccount);
    }

    ///////
    
    @Test
    void testGetAccountByID_Success() throws SQLException {
        // Datos de prueba
        int accountId = 1;
        Account account = new Account(accountId, "test@example.com", "password123", false);

        // Configurar comportamiento del mock
        when(mockAccountDAO.getAccountByID(accountId)).thenReturn(account);

        // Ejecutar el método
        Account result = accountService.getAccountByID(accountId);

        // Verificar resultados
        assertNotNull(result);
        assertEquals(accountId, result.getIdAccount());
        assertEquals("test@example.com", result.getEmail());
        verify(mockAccountDAO, times(1)).getAccountByID(accountId);
    }

    @Test
    void testGetAccountByID_NotFound() throws SQLException {
        // Datos de prueba
        int accountId = 999; // ID que no existe

        // Configurar comportamiento del mock
        when(mockAccountDAO.getAccountByID(accountId)).thenReturn(null);

        // Ejecutar el método
        Account result = accountService.getAccountByID(accountId);

        // Verificar resultados
        assertNull(result);
        verify(mockAccountDAO, times(1)).getAccountByID(accountId);
    }
}
