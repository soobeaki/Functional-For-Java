package com.func.functional.biz.graphs.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.func.functional.biz.graphs.model.ChartModel;
import com.func.functional.biz.graphs.service.RandomGraphService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * RandomGraphController
 * 
 * <p>
 * 랜덤 그래프 생성에 관련된 API 엔드포인트를 제공합니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/${api-version}/functional")
@Tag(name = "Functional", description = "Functional API")
public class RandomGraphController {

    /** RandomGraphService */
    private final RandomGraphService ramdomGraphService;

    /**
     * 주어진 기간에 대한 랜덤 차트를 생성합니다.
     * 
     * @param fromDate 시작 날짜 (yyyyMMdd 형식)
     * @param toDate   종료 날짜 (yyyyMMdd 형식)
     */
    @Operation(summary = "randomChart")
    @GetMapping("/random/chart")
    public List<ChartModel> randomChart(
            @Parameter(description = "시작일자", example = "20240101") @RequestParam(name = "fromDate", required = true) @NotNull String fromDate,
            @Parameter(description = "종료일자", example = "20241231") @RequestParam(name = "toDate", required = true) @NotNull String toDate) {

        return ramdomGraphService.randomChart(fromDate, toDate);
    }

}
