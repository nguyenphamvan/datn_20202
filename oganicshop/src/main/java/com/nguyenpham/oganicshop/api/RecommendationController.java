package com.nguyenpham.oganicshop.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.service.ProductService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    static OkHttpClient client = new OkHttpClient();

    @Autowired
    private ProductService productService;

    @GetMapping("/get_trending")
    public ResponseEntity<?> recommendPopularity() throws IOException {
        String UrlRoot =  "http://127.0.0.1:5000/get_trending";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlRoot).newBuilder();
        // Create body request
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Long>>(){}.getType();
        List<Long> idProductList = gson.fromJson(response.body().string(), type);
        return new ResponseEntity<Object>(idProductList, HttpStatus.OK);
//        List<Long> longList = Stream.of(idProducts).map(Long::valueOf).collect(Collectors.toList());
//        List<Product> productList = productService.findAll(longList);
    }

    @GetMapping("/similarity/{userId}/{bookId}")
    public ResponseEntity<?> recommendForUser(@PathVariable("userId") String userId, @PathVariable("bookId") String bookId) throws IOException{
        String UrlRoot =  "http://127.0.0.1:5000/get_hybridRecommendations/" + userId + "/" + bookId;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlRoot).newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        Response response = client.newCall(request).execute();
        return new ResponseEntity<Object>(response.body().string(), HttpStatus.OK);
//        List<Long> longList = Stream.of(idProducts).map(Long::valueOf).collect(Collectors.toList());
//        List<Product> productList = productService.findAll(longList);
    }
}
