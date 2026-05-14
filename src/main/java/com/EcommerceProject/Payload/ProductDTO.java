package com.EcommerceProject.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private  Long productId;
    private String productName;
    private String image;
    private Integer quantity;
    private double price;//100
    private double discount;//25
    private double specialPrice;//75
}
