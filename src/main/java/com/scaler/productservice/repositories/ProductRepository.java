package com.scaler.productservice.repositories;

import com.scaler.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Product save(Product p);

    @Override
    void delete(Product entity);

    List<Product> findAll();

    Optional<Product> findById(Long id);
    List<Product> findAllByCategory_Subcategories_SurnameEquals(String subcategorySurname);

    @Query("select p " +
            "from Product p " +
            "where p.category.subcategories.surname = :categorySurname")
        // select * from products p where p.id = ?
    List<Product> blablabla(@Param("categorySurname") String categorySurname);

    @Query("select p from Product p where p.id > :idGreaterThan")
    List<Product> somethingsomething(@Param("idGreaterThan") Long idGreaterThan);

    @Query(
            value = CustomQueries.GET_PRODUCTS_WITH_SUBCATEGORY_NAME,
            nativeQuery = true
    )
    List<Product> somesome2();
}
