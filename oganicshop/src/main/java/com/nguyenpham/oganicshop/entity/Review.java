package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer"})
@EqualsAndHashCode(exclude={"rootComment", "subReviews", "product", "user", "replyReviewSet"})
@ToString(exclude={"rootComment", "subReviews", "product", "user", "replyReviewSet"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "comment")
    private String comment;

    @Column(name = "ratting")
    private int ratting;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "numbers_of_like")
    private int numbersOfLike;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "root_comment_id")
    @JsonIgnore
    private Review rootComment;

    @OneToMany(mappedBy = "rootComment", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Review> subReviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private User user;

    public ResponseReviewDto convertReviewToReviewDto() {
        ResponseReviewDto response = new ResponseReviewDto();
        response.setId(this.id);
        response.setComment(this.comment);
        response.setTitle(this.title);
        response.setReviewerName(this.user.getFullName());
        response.setUserId(this.user.getId());
        response.setProductId(this.product.getId());
        response.setCreatedAt(this.createdAt);
        response.setUpdatedAt(this.updatedAt);
        if (this.subReviews != null) {
            for (Review subReview : subReviews) {
                ResponseReviewDto subReviewDto = new ResponseReviewDto();
                subReviewDto.setId(this.id);
                subReviewDto.setUserId(this.user.getId());
                subReviewDto.setReviewerName(this.user.getFullName());
                subReviewDto.setIdRootReview(this.rootComment.getId());
                subReviewDto.setComment(this.comment);
                subReviewDto.setTitle(this.title);
                subReviewDto.setCreatedAt(this.createdAt);
                subReviewDto.setUpdatedAt(this.updatedAt);
                response.addSubReview(subReviewDto);
            }
        }
        return response;
    }
}
