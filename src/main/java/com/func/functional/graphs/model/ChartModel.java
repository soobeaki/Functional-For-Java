package com.func.functional.graphs.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(title = "차트데이터")
public class ChartModel {

    /** xAxis */
    @Schema(title = "x축", example = "20230702")
    @JsonProperty("x")
    @NotBlank
    private String xAxis;

    /** yAxis */
    @Schema(title = "y축", example = "1.26")
    @JsonProperty("y")
    @NotNull
    private BigDecimal yAxis;

    /** marker */
    @Schema(title = "마커", format = "ADD_INV:추가금액투자, ADD_PRT:추가상품투자", example = "ADD_INV, ADD_PRT")
    @JsonProperty("marker")
    private List<String> marker = new ArrayList<>();

}
