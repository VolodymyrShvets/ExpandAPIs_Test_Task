package com.expandapis.vshvets295.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String entryDate;
    private long itemCode;
    private String itemName;
    private int itemQuantity;
    private String status;
}
