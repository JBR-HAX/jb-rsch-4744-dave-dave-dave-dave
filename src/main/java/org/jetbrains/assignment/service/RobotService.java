package org.jetbrains.assignment.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.assignment.entity.RequestResponseLog;
import org.jetbrains.assignment.models.Location;
import org.jetbrains.assignment.models.Move;
import org.jetbrains.assignment.repository.RequestResponseLogRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RobotService {

    private final RequestResponseLogRepository requestResponseLogRepository;

    public List<Location> processMoves(List<Move> moves) {
        List<Location> locations = new ArrayList<>();

        Location currentLocation = new Location(0,0); // Assuming the robot starts at (0,0)
        locations.add(currentLocation);

        for (Move move : moves) {
            switch (move.getDirection().toUpperCase()) {
                case "NORTH":
                    currentLocation = new Location(currentLocation.getX(), currentLocation.getY() + move.getSteps());
                    break;
                case "SOUTH":
                    currentLocation = new Location(currentLocation.getX(), currentLocation.getY() - move.getSteps());
                    break;
                case "WEST":
                    currentLocation = new Location(currentLocation.getX() - move.getSteps(), currentLocation.getY());
                    break;
                case "EAST":
                    currentLocation = new Location(currentLocation.getX() + move.getSteps(), currentLocation.getY());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid direction: " + move.getDirection());
            }
            locations.add(currentLocation);
        }

        return locations;
    }


    public List<Move> processLocations(List<Location> locations) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < locations.size() - 1; i++) {
            Location currentLocation = locations.get(i);
            Location nextLocation = locations.get(i + 1);

            if (currentLocation.getX() != nextLocation.getX()) {
                int steps = Math.abs(nextLocation.getX() - currentLocation.getX());
                String direction = nextLocation.getX() > currentLocation.getX() ? "EAST" : "WEST";
                moves.add(new Move(direction, steps));
            }

            if (currentLocation.getY() != nextLocation.getY()) {
                int steps = Math.abs(nextLocation.getY() - currentLocation.getY());
                String direction = nextLocation.getY() > currentLocation.getY() ? "NORTH" : "SOUTH";
                moves.add(new Move(direction, steps));
            }
        }
        return moves;
    }

    public void saveRequestAndResponse(String request, String respose){
        RequestResponseLog requestResponseLog = new RequestResponseLog();
        requestResponseLog.setRequest(request);
        requestResponseLog.setResponse(respose);
        RequestResponseLog save = requestResponseLogRepository.save(requestResponseLog);
        log.info("Saved to db with id {}", save.getId());
    }
}
