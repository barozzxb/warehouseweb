package vn.warehouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_product_supplier")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonBackReference
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    @JsonBackReference
    private Supplier supplier;

    @Column(name = "supply_date", nullable = false)
    private LocalDate supplyDate;

    @Column(name = "supply_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal supplyPrice;

    @Column(name = "supply_quantity", nullable = false)
    private Integer supplyQuantity;
}
