package com.tej.smart_lms.controller;


import com.tej.smart_lms.dto.DSA;
import com.tej.smart_lms.exceptions.DSANotFoundException;
import com.tej.smart_lms.services.DsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dsa")
public class DsaController {

    @Autowired private DsaService dsaService;


    @GetMapping
    public ResponseEntity<List<DSA>> getAllQuestions(){
        return ResponseEntity.ok(dsaService.getAllDsaQuestion());
    }

    @GetMapping("question/{id}")
    public ResponseEntity<DSA> getById(@PathVariable String id) {
        return ResponseEntity.ok(dsaService.getById(id));
    }

    @GetMapping("/category")
    public ResponseEntity<List<DSA>> getByCategory(@RequestParam String category) {
        List<DSA> dsas = dsaService.getByCategory(category);
        if (dsas.isEmpty()) {
            throw new DSANotFoundException("No DSA found in category: " + category);
        }
        return ResponseEntity.ok(dsas);
    }


    @PostMapping("/complete")
    public ResponseEntity<Void> completeDsa(@RequestParam String username, @RequestParam String title) {
        dsaService.markDsaCompleted(username, title);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/completed/count")
    public ResponseEntity<Integer> getCompletedDsaCounts(@RequestParam String username) {
        int count = dsaService.getCompletedDsaCount(username);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/completed/count/difficulty")
    public ResponseEntity<Map<String, Long>> getCompletedDsaCountByDifficulty(@RequestParam String username) {
        Map<String, Long> result = dsaService.countCompletedDsaByDifficulty(username);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<String>> getCompletedDsaQuestions(@RequestParam String username) {
        List<String> completedQuestions = dsaService.getCompletedDsaQuestions(username);
        return ResponseEntity.ok(completedQuestions);
    }


}
