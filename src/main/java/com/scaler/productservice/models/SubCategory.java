package com.scaler.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subcategory")
@Getter
@Setter
public class SubCategory extends BaseModel{
private String surname;
}
