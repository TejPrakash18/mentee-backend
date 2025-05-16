package com.tej.smart_lms.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tej.smart_lms.dto.DSA;
import com.tej.smart_lms.dto.Project;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class DataLoaderService {

    private List<DSA> dsaList;
    private List<Project> projects;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (InputStream dsaStream = new ClassPathResource("dsa.json").getInputStream()) {
            dsaList = Arrays.asList(mapper.readValue(dsaStream, DSA[].class));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load DSA.json", e);
        }

        try (InputStream projectStream = new ClassPathResource("projects.json").getInputStream()) {
            projects = Arrays.asList(mapper.readValue(projectStream, Project[].class));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load projects.json", e);
        }
    }


    public List<DSA> getDsaList(){
        return dsaList;
  }

  public List<Project> getProjects(){
        return projects;
  }

}