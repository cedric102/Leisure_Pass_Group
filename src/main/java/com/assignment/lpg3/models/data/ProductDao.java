package com.assignment.lpg3.models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "entity")
@Data
public class ProductDao {

    @Id
    private int id;

    @Size( min=2 , message = "username must be at least 2 characters long" )
    private String name;

    @Size( min=2 , message = "username must be at least 2 characters long" )
    private String description;

    @Column(name="category_id")
    @Size( min=2 , message = "username must be at least 2 characters long" )
    private String categoryId;

    @Column(name="creation_date")
    @Size( min=2 , message = "username must be at least 2 characters long" )
    private String creationDate;

    @Column(name="update_date")
    @Size( min=2 , message = "username must be at least 2 characters long" )
    private String updateDate;

    @Column(name="last_purchase_date")
    @Size( min=2 , message = "username must be at least 2 characters long" )
    @Pattern( regexp = "^[0-9]+\\/[0-9]{2}\\/[0-9]{4}\\s[0-9]{1,2}" , message = "Please, enter a date" )
    private String lastPurchasedDate;

    public ProductDao( ) {
        
    }
    public ProductDao( 
        int id , 
        String name , 
        String description , 
        String categoryId , 
        String creationDate , 
        String updateDate , 
        String lastPurchasedDate ) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.lastPurchasedDate = lastPurchasedDate;

    }
    
}
