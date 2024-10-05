package com.func.functional.biz.chart.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.func.functional.biz.chart.model.ChartModel;
import com.func.functional.biz.chart.service.ChartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * ChartController
 * 
 * <p>
 * Chart 생성에 관련된 API 엔드포인트를 제공합니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/${api-version}/functional")
@Tag(name = "Functional", description = "Functional API")
public class ChartController {

    /** RandomGraphService */
    private final ChartService chartService;

    /**
     * 주어진 기간에 대한 랜덤 차트를 생성합니다.
     * 
     * @param fromDate 시작 날짜 (yyyyMMdd 형식)
     * @param toDate   종료 날짜 (yyyyMMdd 형식)
     * @return 주어진 기간에 해당하는 랜덤 차트를 반환합니다.
     */
    @Operation(summary = "randomChart")
    @GetMapping("/random/chart")
    public List<ChartModel> randomChart(
            @Parameter(description = "시작일자", example = "20240101") @RequestParam(name = "fromDate", required = true) @NotNull String fromDate,
            @Parameter(description = "종료일자", example = "20241231") @RequestParam(name = "toDate", required = true) @NotNull String toDate) {

        return chartService.randomChart(fromDate, toDate);
    }

}
