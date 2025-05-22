package vn.warehouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tbl_supplier")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @OneToMany(mappedBy = "supplier")
    @Builder.Default
    @JsonManagedReference
    private List<ProductSupplier> productSuppliers = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "supplier")
    @Builder.Default
    @JsonManagedReference
    private List<Transaction> transactions = new java.util.ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    @JsonBackReference
    private Warehouse warehouse;

}
