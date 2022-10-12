package com.drifter.productservice.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
    private String id;
    private String product_type;
    private String model_number;
    private String name;
    private Object meta_data;
    private Object view_list;
    private Object dynamic_background_assets;
    private Object confirmed_dynamic_background_assets;
    private Object attribute_list;
    private Object breadcrumb_list;
    private Object callouts;
    private Object pricing_information;
    private Object product_description;
    private Object recommendationsEnabled;
    private Object product_link_list;
    private Object embellishment;

    // hold the average review score for product
    private float averageReviewScore;

    // hold the count of reviews for product
    private long numberOfReviews;
}


