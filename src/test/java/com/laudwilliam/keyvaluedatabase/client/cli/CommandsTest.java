package com.laudwilliam.keyvaluedatabase.client.cli;

import com.laudwilliam.keyvaluedatabase.client.Client;
import com.laudwilliam.keyvaluedatabase.utils.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommandsTest {
    @Mock
    private Client client;
    @Mock
    private ConsoleService consoleService;
    @Mock
    private FileManager fileManager;

    @InjectMocks
    private Commands commands;

    @BeforeEach
    void setUp() {
        when(client.login(anyString(), anyString())).thenReturn(true);
        when(consoleService.getPassword()).thenReturn("password");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loginTest() {
        // Given
        when(consoleService.getPassword()).thenReturn("password");

        // When
        String response = commands.login("database", "admin", true);

        // Then
        assertThat(response).isEqualTo("Successfully logged in");
    }

    @Test
    void  incorrectPasswordLoginTest() {
        // Given
        AtomicInteger callCount = new AtomicInteger();
        when(consoleService.getPassword()).thenAnswer(
                invocation -> (callCount.getAndIncrement() == 0) ? "pad" : "password");
        when(client.login("admin", "pad")).thenReturn(false);
        String response;

        // When
        response = commands.login("database", "admin", true);
        // Then
        verify(consoleService, times(1)).getPassword();
        assertThat(response).isEqualTo("Incorrect username or password");

        // When
        response = commands.login("database", "admin", true);
        // Then
        verify(consoleService, times(2)).getPassword();
        assertThat(response).isEqualTo("Successfully logged in");

    }

    @Test
    void incorrectUsernameLoginTest() {
        // Given
        when(client.login(anyString(), anyString())).thenReturn(false);
        when(client.login("admin", "password")).thenReturn(true);
        String response;

        // When
        response = commands.login("database", "ad", true);
        // Then
        assertThat(response).isEqualTo("Incorrect username or password");

        // When
        response = commands.login("database", "admin", true);
        assertThat(response).isEqualTo("Successfully logged in");
    }

    @Test
    void loginAvailabilityTest() {
        // Given
        String response;

        // When
        response = commands.login("database", "admin", true);

        // Then
        assertThat(commands.isLoggedIn()).isTrue();
    }

    @Test
    void testValidQuery() {
        // Given
        String response;
        commands.setLoggedIn(true);
        when(client.get("key")).thenReturn("SUCCESS");
        when(client.delete("key")).thenReturn("SUCCESS");
        when(client.set("key", "value")).thenReturn("SUCCESS");

        // When
        response = commands.query("DEL", "key", "");
        //Then
        assertThat(response).isEqualTo("SUCCESS");

        // When
        response = commands.query("GET", "key", "");
        //Then
        assertThat(response).isEqualTo("SUCCESS");

        // When
        response = commands.query("SET", "key", "value");
        //Then
        assertThat(response).isEqualTo("SUCCESS");
    }

    @Test
    void testInvalidQuery() {
        // Given
        String response;
        commands.setLoggedIn(true);

        // When
        response = commands.query("DELs", "key", "");
        //Then
        assertThat(response).contains(String.format("The query '%s' does not exist", "DELs"));

        // When
        response = commands.query("", "key", "");
        //Then
        assertThat(response).isEqualTo("Please enter a valid command");

        // When
        response = commands.query("DEL", "", "");
        //Then
        assertThat(response).isEqualTo("Please enter a valid command");

    }

    @Test
    void testValidRun() {
        // Given
        String response;
        commands.setLoggedIn(true);
        when(fileManager.isFile(anyString())).thenReturn(true);
        when(fileManager.getFile(anyString())).thenReturn(new File("file.text"));
        when(client.processCommands(any())).thenReturn("SUCCESS");
        when(client.processCommands(any(), any())).thenReturn("SUCCESS");
        when(fileManager.isFile("output.txt")).thenReturn(false);

        // When
        response = commands.run("file.txt", "");
        // Then
        assertThat(response).isEqualTo("SUCCESS");

        // When
        response = commands.run("file.txt", "output.txt");
        // Then
        assertThat(response).isEqualTo("SUCCESS");

    }

    @Test
    void testInvalidRun() {
        // Given
        String response;
        commands.setLoggedIn(true);
        when(fileManager.isFile(anyString())).thenReturn(true);
        when(fileManager.getFile(anyString())).thenReturn(new File("file.text"));
        when(client.processCommands(any())).thenReturn("SUCCESS");
        when(client.processCommands(any(), any())).thenReturn("SUCCESS");
        when(fileManager.isFile("output.txt")).thenReturn(true);

        // When
        response = commands.run("", "");
        // Then
        assertThat(response).isEqualTo("Please enter a valid command");

        // When
        response = commands.run("file.txt", "output.txt");
        // Then
        assertThat(response).isEqualTo("Output file already exist");

        // Given
        when(fileManager.isFile("file.txt")).thenReturn(false);
        // When
        response = commands.run("file.txt", "");
        // Then
        assertThat(response).isEqualTo(String.format("File not found %s: ", "file.txt"));
    }


}