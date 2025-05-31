package com.aernaur.votingSystem.controller;

import com.aernaur.votingSystem.dto.ElectionDTO;
import com.aernaur.votingSystem.dto.SubElectionDTO;
import com.aernaur.votingSystem.exceptions.EntityNotFoundException;
import com.aernaur.votingSystem.service.ElectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/elections")
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping
    public List<ElectionDTO> listElections() {
        return electionService.listElections();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveElection(@RequestBody @Valid ElectionDTO electionDTO) {
        return ResponseEntity.ok(Map.of("id", electionService.saveElection(electionDTO)));
    }

    @GetMapping("/{electionId}")
    public ResponseEntity<ElectionDTO> getElection(@PathVariable("electionId") UUID electionId) throws EntityNotFoundException {
        return ResponseEntity.ok(electionService.getElection(electionId));
    }

    @GetMapping("/{electionId}/sub-election")
    public List<SubElectionDTO> listSubElections(@PathVariable("electionId") UUID electionId) {
        return electionService.listSubElections(electionId);
    }

    @PostMapping("/{electionId}/sub-election")
    public ResponseEntity<Map<String, Object>> saveSubElection(@PathVariable("electionId") UUID electionId,
                                                               @RequestBody SubElectionDTO subElectionDTO) {
        return ResponseEntity.ok(Map.of("id", electionService.saveSubElection(electionId, subElectionDTO)));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/{electionId}/count-votes")
    public void countVotes(@PathVariable("electionId") UUID electionId) {
        electionService.countVotes(electionId);
    }
}
