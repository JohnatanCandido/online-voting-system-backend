package com.aernaur.votingSystem.controller;

import com.aernaur.votingSystem.dto.VoteRequestWrapper;
import com.aernaur.votingSystem.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void vote(@RequestBody @Valid VoteRequestWrapper votes) {
        voteService.saveVotes(votes);
    }
}
