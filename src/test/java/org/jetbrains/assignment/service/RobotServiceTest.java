package org.jetbrains.assignment.service;

import static org.junit.jupiter.api.Assertions.*;

import org.jetbrains.assignment.entity.RequestResponseLog;
import org.jetbrains.assignment.models.Location;
import org.jetbrains.assignment.models.Move;
import org.jetbrains.assignment.repository.RequestResponseLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RobotServiceTest {

    @Mock
    private RequestResponseLogRepository requestResponseLogRepository;

    @InjectMocks
    private RobotService robotService;

    @Test
    @DisplayName("Test processMoves with valid moves")
    void testProcessMoves() {
        // Arrange
        List<Move> moves = Arrays.asList(new Move("NORTH", 10), new Move("EAST", 10));

        // Act
        List<Location> locations = robotService.processMoves(moves);

        // Assert
        assertEquals(3, locations.size());
        assertEquals(new Location(0, 10), locations.get(1));
        assertEquals(new Location(10, 10), locations.get(2));
    }

    @Test
    @DisplayName("Test processLocations with valid locations")
    void testProcessLocations() {
        // Arrange
        List<Location> locations = Arrays.asList(new Location(0, 0), new Location(0, 10), new Location(10, 10));

        // Act
        List<Move> moves = robotService.processLocations(locations);

        // Assert
        assertEquals(2, moves.size());
        assertEquals(new Move("NORTH", 10), moves.get(0));
        assertEquals(new Move("EAST", 10), moves.get(1));
    }

    @Test
    @DisplayName("Test save Request and Response")
    void testSaveRequestAndResponse() {
        // Arrange
        String request = "some_request";
        String response = "some_response";
        RequestResponseLog mockLog = mock(RequestResponseLog.class);
        when(requestResponseLogRepository.save(any(RequestResponseLog.class))).thenReturn(mockLog);

        // Act
        robotService.saveRequestAndResponse(request, response);

        // Assert
        verify(requestResponseLogRepository, times(1)).save(any(RequestResponseLog.class));
    }


    @Test
    @DisplayName("Test processMoves with invalid direction in Move")
    void testProcessMovesInvalidDirection() {
        // Arrange
        List<Move> moves = List.of(new Move("INVALID", 10));

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.processMoves(moves);
        });

        String expectedMessage = "Invalid direction: INVALID";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test processLocations with empty locations")
    void testProcessLocationsEmptyLocations() {
        // Arrange
        List<Location> locations = new ArrayList<>();

        // Act
        List<Move> moves = robotService.processLocations(locations);

        // Assert
        assertTrue(moves.isEmpty());
    }


    private static Stream<Arguments> processMovesProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(new Move("NORTH", 10), new Move("EAST", 10)), Arrays.asList(new Location(0, 0), new Location(0, 10), new Location(10, 10))),
                Arguments.of(Arrays.asList(new Move("SOUTH", 5), new Move("WEST", 5)), Arrays.asList(new Location(0, 0), new Location(0, -5), new Location(-5, -5)))
        );
    }

    @ParameterizedTest
    @MethodSource("processMovesProvider")
    @DisplayName("Test processMoves with valid moves")
    void testProcessMoves(List<Move> moves, List<Location> expectedLocations) {
        List<Location> resultLocations = robotService.processMoves(moves);
        assertEquals(expectedLocations, resultLocations);
    }

    private static Stream<Arguments> processLocationsProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(new Location(0, 0), new Location(0, 10), new Location(10, 10)), Arrays.asList(new Move("NORTH", 10), new Move("EAST", 10))),
                Arguments.of(Arrays.asList(new Location(0, 0), new Location(0, -5), new Location(-5, -5)), Arrays.asList(new Move("SOUTH", 5), new Move("WEST", 5)))
        );
    }

    @ParameterizedTest
    @MethodSource("processLocationsProvider")
    @DisplayName("Test processLocations with valid locations")
    void testProcessLocations(List<Location> locations, List<Move> expectedMoves) {
        List<Move> resultMoves = robotService.processLocations(locations);
        assertEquals(expectedMoves, resultMoves);
    }
}