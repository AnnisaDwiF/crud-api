package id.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Item")
public class DataItem extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "dataItemSequence",
            sequenceName = "dataItem_sequence",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(generator = "dataItem", strategy = GenerationType.SEQUENCE)

    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "count", nullable = false)
    public Integer count;

    @Column(name = "price", nullable = false)
    public Double price;

    @Column(name = "type")
    public String type;

    @Column(name = "description")
    public String description;

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

}
