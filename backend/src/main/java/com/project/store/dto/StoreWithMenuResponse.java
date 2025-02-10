package com.project.store.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "메뉴가 포함된 가게 Dto")
public class StoreWithMenuResponse {
    private int id;

    @Schema(example = "서초구")
    private String local;

    @Schema(example = "한식")
    private String foodCategory;

    @Schema(example = "데이트")
    private String theme;

    private String name;

    private String address;

    private String detailAddress;

    @Schema(example = "00-000-0000")
    private String tel;

    @Schema(example = "/images/store_1.png")
    private String image;

    @Schema(example = "10000000.000", type = "number", format = "decimal")
    private BigDecimal x;

    @Schema(example = "20000000.000", type = "number", format = "decimal")
    private BigDecimal y;

    private String description;

    private int views;

    @ArraySchema(
            schema = @Schema(type = "object", description = "A menu item", example = "{\"id\": 1," +
                                                                                      "\"name\": \"Menu 1\"," +
                                                                                      "\"price\": 10000," +
                                                                                      "\"description\": \"맛있는 메뉴\"," +
                                                                                      "\"image\": \"/images/menu_1.png\"}")
    )
    private List<MenuDto> menus;
}
