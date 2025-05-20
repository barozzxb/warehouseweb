package vn.warehouse.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_ware_house")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "wareHouse")
    @Builder.Default
    @JsonManagedReference
    private List<Supplier> suppliers = new ArrayList<>();

    @OneToMany(mappedBy = "wareHouse")
    @Builder.Default
    @JsonManagedReference
    private List<Inventory> inventory = new ArrayList<>();
}
