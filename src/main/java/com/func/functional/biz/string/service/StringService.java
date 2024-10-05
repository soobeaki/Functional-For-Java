package com.func.functional.biz.string.service;

import org.springframework.stereotype.Service;

import com.func.functional.biz.string.model.MaskIn;
import com.func.functional.utils.StringUtils;

import lombok.RequiredArgsConstructor;

/**
 * StringService
 */
@Service
@RequiredArgsConstructor
public class StringService {

    /**
     * 주어진 문자열을 마스킹 처리합니다.
     * 
     * @param args 마스킹할 문자열을 포함한 객체
     * @return 마스킹 처리된 문자열을 포함한 객체
     */
    public MaskIn maskSensitiveData(MaskIn args) {

	// 민감한 데이터를 마스킹
	String maskedString = StringUtils.maskString(args.getMask());

	// 결과 객체에 마스킹된 문자열을 설정
	MaskIn result = new MaskIn();
	result.setMask(maskedString);

	return result;
    }

}
