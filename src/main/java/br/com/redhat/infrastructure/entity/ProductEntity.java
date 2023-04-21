package br.com.redhat.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id @GeneratedValue
    private Long id;
    
    private String name;
    
    @Column(precision = 6, scale = 2)
    private BigDecimal price;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @CreationTimestamp @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
