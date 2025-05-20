package vn.warehouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_inventory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "inventory")
    @Builder.Default
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    @JsonBackReference
    private WareHouse wareHouse;

}
