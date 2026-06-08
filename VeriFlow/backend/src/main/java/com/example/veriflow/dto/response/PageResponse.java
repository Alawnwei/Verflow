package com.example.veriflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> list;

    private Long total;

    private Integer page;

    private Integer size;

    public static <T> PageResponse<T> of(List<T> list, Long total, Integer page, Integer size) {
        return PageResponse.<T>builder()
                .list(list)
                .total(total)
                .page(page)
                .size(size)
                .build();
    }
}
