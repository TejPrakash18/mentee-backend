package com.tej.smart_lms.services;


import com.tej.smart_lms.dto.DSA;
import com.tej.smart_lms.dto.Project;
import com.tej.smart_lms.exceptions.DSANotFoundException;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class DsaService {

    @Autowired
    private UserRepository userRepository;

    @Autowired private DataLoaderService dataLoaderService;

    public List<DSA> getAllDsaQuestion() {
        return dataLoaderService.getDsaList();
    }

    public DSA getById(String id) {
        Long parsedId;
        parsedId = Long.parseLong(id);
        return dataLoaderService.getDsaList().stream()
                .filter(p -> p.getId() != null && p.getId().equals(parsedId))
                .findFirst()
                .orElseThrow(() -> new DSANotFoundException("DSA not found with id: " + parsedId));
    }

    public List<DSA> getByCategory(String category) {
        return dataLoaderService.getDsaList().stream()
                .filter(dsa -> dsa.getCategory() != null && dsa.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public void markDsaCompleted(String username, String title) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Set<String> completed = user.getCompletedDsaQuestion();
        completed.add(title);
        user.setCompletedDsaQuestion(completed);
        userRepository.save(user);
    }

    public int getCompletedDsaCount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Set<String> completed = user.getCompletedDsaQuestion();
        return completed != null ? completed.size() : 0;
    }
    public Map<String, Long> countCompletedDsaByDifficulty(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Set<String> completedTitles = user.getCompletedDsaQuestion();
        if (completedTitles == null || completedTitles.isEmpty()) {
            return Collections.emptyMap();
        }

        return dataLoaderService.getDsaList().stream()
                .filter(dsa -> completedTitles.contains(dsa.getTitle()))
                .filter(dsa -> dsa.getDifficulty() != null && !dsa.getDifficulty().isEmpty())
                .collect(Collectors.groupingBy(
                        dsa -> dsa.getDifficulty().toLowerCase(),
                        Collectors.counting()
                ));
    }

    public List<String> getCompletedDsaQuestions(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            Set<String> completed = user.getCompletedDsaQuestion();
            if (completed != null) {
                return new ArrayList<>(completed);
            }
        }
        return Collections.emptyList();
    }



}
