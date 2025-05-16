package com.tej.smart_lms.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DSA {
    private Long id;
    private String title;
    private String difficulty;
    private String category;
    private String explanation;
    private List<ExampleDto> examples;

    @JsonProperty("expected_output")
    private String expectedOutput;


}
