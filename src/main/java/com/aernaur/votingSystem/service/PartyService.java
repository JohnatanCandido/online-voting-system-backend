package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.dto.PartyDTO;
import com.aernaur.votingSystem.entity.Party;
import com.aernaur.votingSystem.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

    public List<PartyDTO> searchParties() {
        return partyRepository.findAll().stream().map(PartyDTO::new).toList();
    }

    public UUID saveParty(PartyDTO partyDTO) {
        Party party;
        if (partyDTO.getPartyId() == null) {
            party = new Party();
        } else {
            party = partyRepository.findById(partyDTO.getPartyId()).orElseThrow();
        }
        party.setName(partyDTO.getPartyName());
        party.setAcronym(partyDTO.getPartyAcronym());
        party.setNumber(partyDTO.getPartyNumber());
        party = partyRepository.save(party);

        return party.getId();
    }

    public void deleteParty(UUID partyId) {
        partyRepository.deleteById(partyId);
    }
}
