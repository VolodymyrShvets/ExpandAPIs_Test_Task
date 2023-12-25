package com.expandapis.vshvets295.service;

import com.expandapis.vshvets295.dto.ProductRecordsList;
import com.expandapis.vshvets295.dto.ProductDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final EntityManagerFactory emFactory;

    public ResponseEntity<String> addNewProducts(ProductRecordsList productRecordsList) {
        EntityManager em = emFactory.createEntityManager();

        try {
            String tableName = productRecordsList.getTable();
            List<ProductDto> records = productRecordsList.getRecords();

            createTable(tableName, em);
            addProductsToTable(tableName, records, em);

            return ResponseEntity.ok("Records saved successfully.");
        } catch (Exception e) {
            log.error("[{}][addNewProducts] -> unable to add products: {}", ProductService.class.getSimpleName(), e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving records.");
        } finally {
            em.close();
        }
    }

    private void createTable(String tableName, EntityManager em) {
        log.info("[{}][createTable] -> creating table with name: {}", ProductService.class.getSimpleName(), tableName);

        em.getTransaction().begin();

        em.createNativeQuery("CREATE TABLE IF NOT EXISTS " + tableName +
                        "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "entry_date VARCHAR(255), " +
                        "item_code VARCHAR(255), " +
                        "item_name VARCHAR(255), " +
                        "item_quantity VARCHAR(255), " +
                        "status VARCHAR(255))")
                .executeUpdate();

        em.getTransaction().commit();
    }

    private void addProductsToTable(String tableName, List<ProductDto> records, EntityManager em) {
        log.info("[{}][addProductsToTable] -> adding products to table: {}", ProductService.class.getSimpleName(), tableName);

        em.getTransaction().begin();

        for (ProductDto productDto : records) {
            String insertQuery = "INSERT INTO " + tableName + " (entry_date, item_code, item_name, item_quantity, status) VALUES (?, ?, ?, ?, ?)";

            em.createNativeQuery(insertQuery)
                    .setParameter(1, productDto.getEntryDate())
                    .setParameter(2, productDto.getItemCode())
                    .setParameter(3, productDto.getItemName())
                    .setParameter(4, productDto.getItemQuantity())
                    .setParameter(5, productDto.getStatus())
                    .executeUpdate();
        }

        em.getTransaction().commit();
    }

    public List<ProductDto> getAllProducts() {
        log.info("[{}][getAllProducts] -> receiving all products", ProductService.class.getSimpleName());

        String tableName = "products";
        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();

        String nativeQuery = "SELECT * FROM " + tableName;
        List<Object[]> products = em.createNativeQuery(nativeQuery).getResultList();
        List<ProductDto> productDtos = convertToProductDtoList(products);
        em.getTransaction().commit();

        return productDtos;
    }

    private List<ProductDto> convertToProductDtoList(List<Object[]> resultList) {
        List<ProductDto> productDtos = new ArrayList<>();

        for (Object[] row : resultList) {
            String entryDate = (String) row[1];
            long itemCode = Long.parseLong((String) row[2]);
            String itemName = (String) row[3];
            int itemQuantity = Integer.parseInt((String) row[4]);
            String status = (String) row[5];

            ProductDto productDto = new ProductDto(entryDate, itemCode, itemName, itemQuantity, status);
            productDtos.add(productDto);
        }

        return productDtos;
    }
}
