package com.votehaeduo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/votes")
@RestController
public class VoteApiController {

    @GetMapping
    public Object findAll() {
        return new Object();
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable Long id) {
        return new Object();
    }
}
