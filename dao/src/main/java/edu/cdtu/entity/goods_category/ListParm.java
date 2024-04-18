package edu.cdtu.entity.goods_category;

import lombok.Data;

@Data
public class ListParm {
    private Integer currentPage;
    private Integer pageSize;
    private String categoryName;
}
