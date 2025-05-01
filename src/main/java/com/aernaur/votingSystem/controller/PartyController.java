package com.aernaur.votingSystem.controller;

import com.aernaur.votingSystem.dto.PartyDTO;
import com.aernaur.votingSystem.service.PartyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping
    public List<PartyDTO> searchParties() {
        return partyService.searchParties();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveParty(@RequestBody @Valid PartyDTO partyDTO) {
        return ResponseEntity.ok(Map.of("id", partyService.saveParty(partyDTO)));
    }

    @DeleteMapping("/{partyId}")
    public ResponseEntity<String> deleteParty(@PathVariable("partyId") UUID partyId) {
        partyService.deleteParty(partyId);
        return ResponseEntity.ok().build();
    }
}
