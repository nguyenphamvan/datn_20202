package com.nguyenpham.oganicshop.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.ProductService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get_trending/{topN}")
    public ResponseEntity<?> recommendPopularity(@PathVariable("topN") String topN) throws IOException {
        String UrlRoot =  "http://127.0.0.1:5000/get_trending/" + topN;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlRoot).newBuilder();
        // Create body request
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Long>>(){}.getType();
        List<Long> listProductId = gson.fromJson(response.body().string(), type);
        List<ProductResponse> responses = productService.getProductRecommend(listProductId);
        return new ResponseEntity<Object>(responses, HttpStatus.OK);
    }

    @GetMapping("/similarity/{bookId}")
    public ResponseEntity<?> recommendForUser(@PathVariable("bookId") String bookId) throws IOException{
        String url = "";
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            url = "http://127.0.0.1:5000/get_hybridRecommendations/1/" + bookId;
        } else {
            User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            url = "http://127.0.0.1:5000/get_hybridRecommendations/" + user.getId() + "/" + bookId;
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Long>>(){}.getType();
        List<Long> listProductId = gson.fromJson(response.body().string(), type);
        List<ProductResponse> responses = productService.getProductRecommend(listProductId);
        return new ResponseEntity<Object>(responses, HttpStatus.OK);
    }
}
