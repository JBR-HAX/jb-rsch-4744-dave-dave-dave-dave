package org.jetbrains.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.assignment.models.Location;
import org.jetbrains.assignment.models.Move;
import org.jetbrains.assignment.service.RobotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @PostMapping("/locations")
    public ResponseEntity<List<Location>> getLocations(@RequestBody List<Move> moves) {
        List<Location> locations = robotService.processMoves(moves);
        robotService.saveRequestAndResponse(moves.toString(), locations.toString());
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PostMapping("/moves")
    public ResponseEntity<List<Move>> getMoves(@RequestBody List<Location> locations) {
        List<Move> moves = robotService.processLocations(locations);
        robotService.saveRequestAndResponse(locations.toString(), moves.toString());
        return new ResponseEntity<>(moves, HttpStatus.OK);
    }
}
