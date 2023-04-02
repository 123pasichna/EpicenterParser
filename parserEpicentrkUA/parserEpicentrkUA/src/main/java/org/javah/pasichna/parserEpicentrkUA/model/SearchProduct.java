package org.javah.pasichna.parserEpicentrkUA.model;

public class SearchProduct {
    private String name;
    private String url;
    private String sale;
    private String price;

    public SearchProduct(String name, String url, String imageUrl, String price) {
        this.name = name;
        this.url = url;
        this.sale = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
