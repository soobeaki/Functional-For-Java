package com.func.functional.biz.string.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.func.functional.biz.string.model.MaskIn;
import com.func.functional.biz.string.service.StringService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * StringController
 * 
 * <p>
 * 문자열 관련 기능을 제공하는 API입니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/${api-version}/functional")
@Tag(name = "Functional", description = "Functional API")
public class StringController {

    /** StringService */
    private final StringService stringService;

    /**
     * 민감한 정보를 마스킹 처리합니다.
     * 
     * @param args 마스킹할 문자열을 포함하는 객체
     * @return 마스킹 처리된 문자열을 포함한 객체
     */
    @Operation(summary = "Sensitive Data Masking")
    @PostMapping("/mask")
    public MaskIn maskSensitiveData(@RequestBody MaskIn args) {
	return stringService.maskSensitiveData(args);
    }

}
