package com.func.functional.biz.string.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.func.functional.annotation.OnPost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 마스킹 처리할 문자열을 포함하는 데이터 전송 객체(DTO)입니다. 이 클래스는 마스킹할 문자열을 나타내며, API 요청 및 응답에서
 * 사용됩니다.
 * </p>
 */
@Getter
@Setter
@ToString
public class MaskIn {
    
    /**
     * 마스킹할 문자열
     * 
     * <p>
     * 이 필드는 클라이언트로부터 전달받은 문자열로, 마스킹 처리 후 반환될 문자열의 원본 데이터입니다.
     * </p>
     */
    @Schema(title = "masking", example = "홍길동")
    @JsonView({ OnPost.class })
    private String mask;
}
