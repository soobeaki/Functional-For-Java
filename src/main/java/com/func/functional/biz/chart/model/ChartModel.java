package com.func.functional.biz.chart.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 이 클래스는 차트의 X축 및 Y축 값을 나타내며, JSON 직렬화 및 Swagger 문서화를 지원합니다.
 * </p>
 */
@Getter
@Setter
@ToString
@Schema(title = "차트데이터", description = "차트에서 사용할 X축과 Y축 값을 포함한 데이터 모델")
public class ChartModel {

    /**
     * X축 값
     * 
     * <p>
     * 차트의 X축 값을 나타내며, 날짜 등의 정보를 담을 수 있습니다.
     * </p>
     */
    @Schema(title = "x축", example = "20230702", description = "차트의 X축 값 (예: 날짜)")
    @JsonProperty("x")
    @NotBlank(message = "X축 값은 필수입니다.")
    private String xAxis;

    /**
     * Y축 값
     * 
     * <p>
     * 차트의 Y축 값을 나타내며, 수치 데이터를 포함합니다.
     * </p>
     */
    @Schema(title = "y축", example = "1.26", description = "차트의 Y축 값 (예: 수치)")
    @JsonProperty("y")
    @NotNull(message = "Y축 값은 필수입니다.")
    private BigDecimal yAxis;

}
