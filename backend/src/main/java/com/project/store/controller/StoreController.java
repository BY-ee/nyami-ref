package com.project.store.controller;

import com.project.store.dto.StoreResponse;
import com.project.store.dto.StoreSearchRequest;
import com.project.store.dto.StoreWithMenuResponse;
import com.project.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
@Tag(name = "가게 API", description = "가게 API 명세서")
public class StoreController {
    private final StoreService storeService;

    @Operation(
        summary = "가게 목록",
        description = "입력된 쿼리 파라미터에 따라 가게 목록을 페이징 처리 후 조회합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "가게 목록 조회 성공",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = StoreResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 전송",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "입력값이 잘못되었습니다."))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "가게 목록 조회 실패",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "서버 내부 오류가 발생했습니다."))
            )
        }
    )
    @GetMapping("/stores")
    public ResponseEntity<Page<StoreResponse>> getStores(@Valid StoreSearchRequest request) {
        log.warn("파라미터: {}", request);
        if (request.getPage() < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Page<StoreResponse> stores = storeService.findStores(request);
        return ResponseEntity.ok(stores);
    }

    @Operation(
            summary = "특정 가게 조회",
            description = "가게 id를 입력하여 해당하는 가게 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "가게 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreWithMenuResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 전송",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "입력값이 잘못되었습니다."))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "일치하는 가게가 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema())
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "가게 조회 실패",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "서버 내부 오류가 발생했습니다."))
                    )
            }
    )
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreWithMenuResponse> getStore(@PathVariable int storeId) {
        if (storeId <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        return storeService.findStoreById(storeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "가게 조회수 증가",
            description = "가게 id를 입력하여 해당하는 가게의 조회수를 증가시킵니다."
    )
    @PatchMapping("/stores/{storeId}/view")
    public ResponseEntity<Void> increaseViewCount(@PathVariable int storeId) {
        storeService.increaseViewCount(storeId);
        return ResponseEntity.noContent().build();
    }
}
