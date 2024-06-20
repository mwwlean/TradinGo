package com.example.tradingo;

import java.util.ArrayList;
import java.util.List;

public class ProjectModel {

    private List<String> LinksOfImgsArray;
    private String ProductTitle, Description, Price, Stock;

    public ProjectModel(ArrayList<ImageLink> linksOfImgsArray, String productTitle, String description, String price, String stock) {
        this.LinksOfImgsArray = new ArrayList<>();
        for (ImageLink link : linksOfImgsArray) {
            this.LinksOfImgsArray.add(link.getUrl());
        }
        this.ProductTitle = productTitle;
        this.Description = description;
        this.Price = price;
        this.Stock = stock;
    }

    public List<String> getLinksOfImgsArray() {
        return LinksOfImgsArray;
    }

    public void setLinksOfImgsArray(List<String> linksOfImgsArray) {
        this.LinksOfImgsArray = linksOfImgsArray;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        this.ProductTitle = productTitle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        this.Stock = stock;
    }
}
