package com.aernaur.votingSystem.controller;

import com.aernaur.votingSystem.dto.CandidateDTO;
import com.aernaur.votingSystem.dto.SearchCandidateDTO;
import com.aernaur.votingSystem.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping
    public List<CandidateDTO> searchCandidates(@ModelAttribute SearchCandidateDTO filters) {
        return candidateService.searchCandidates(filters);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCandidate(@RequestBody @Valid CandidateDTO candidateDTO) {
        return ResponseEntity.ok(Map.of("id", candidateService.saveCandidate(candidateDTO)));
    }

    @GetMapping("sub-election/{subElectionId}/candidate/{candidateNumber}")
    public ResponseEntity<CandidateDTO> findCandidate(@PathVariable("subElectionId") UUID subElectionId,
                                                      @PathVariable("candidateNumber") Integer candidateNumber) {
        CandidateDTO candidateDTO = candidateService.findCandidate(subElectionId, candidateNumber);
        return ResponseEntity.ok(candidateDTO);
    }
}
