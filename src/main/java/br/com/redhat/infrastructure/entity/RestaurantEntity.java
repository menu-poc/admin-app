package br.com.redhat.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id @GeneratedValue
    private Long id;
    
    private String name;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<ProductEntity> products;

    @CreationTimestamp @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
