package org.javah.pasichna.parserEpicentrkUA.service;

import org.javah.pasichna.parserEpicentrkUA.model.SearchProduct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserManager {
    public List<SearchProduct> getProductList(String searchQuery) throws IOException {
        String urlSearchQuery = "https://epicentrk.ua/ua/search/?q=" + searchQuery;
        Document doc = Jsoup.connect(urlSearchQuery).followRedirects(true).get();

        List<SearchProduct> productList = new ArrayList<>();
        int page = 1;

        while (true) {
            Elements products = doc.getElementsByClass("columns product-Wrap card-wrapper ");

            for (Element product : products) {
                String name = product.getElementsByClass("font-weight-700 nc").text();
                String url = product.getElementsByClass("link link--big link--inverted link--blue").attr("href");
                String oldPrice = product.getElementsByClass("card__price-sum card__price-sum--old").text();
                String actualPrice = product.getElementsByClass("card__price-actual").text();
                productList.add(new SearchProduct(name, url, oldPrice, actualPrice));
            }

            Element nextLink = doc.selectFirst("a[class=pagination__button pagination__button--next]");
            if (nextLink == null) {
                break;
            }

            urlSearchQuery = nextLink.absUrl("href");
            doc = Jsoup.connect(urlSearchQuery).followRedirects(true).get();
            page++;
        }

        return productList;
    }
}
