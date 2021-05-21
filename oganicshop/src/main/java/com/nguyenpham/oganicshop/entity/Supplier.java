package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Entity
@Table(name = "supplier")
@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude={"products"})
@ToString(exclude = {"products"})
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Supplier){
            return this.id == ((Supplier) obj).getId();
        } else {
            return false;
        }
    }
}
