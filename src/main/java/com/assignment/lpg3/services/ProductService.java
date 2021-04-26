package com.assignment.lpg3.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.assignment.lpg3.models.ProductDaoRepository;
import com.assignment.lpg3.models.data.ProductDao;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator; 
import java.util.List; 

@Service
public class ProductService {
    
    // Get the Product Service Singleton
    @Autowired
    private ProductDaoRepository productDaoRepo;

    // Stores the map from Category_Id to Category_Name
    // The integers are continuous in the current case,
    // but the size cannot be predetermined,
    // so it has been chosen not to use an array of String
    Map<Integer , String> mp;

    String[] categoryMap;

    /**
     * Method to generatr the Catrgory Map
     * Loop through the input path and map the Category_Id to the Category Name
     * 
     * Return void
     */
    private void generateCategoryMap( String path ) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            mp = new HashMap<> (); 
            String line;
            int count=0;
            while((line = br.readLine()) != null) {
                String[] arrLine = line.split(",");
                if( count > 0 )
                    mp.put( Integer.parseInt( arrLine[0] ) , arrLine[1] );

                count++;
            }
        } catch( Exception e ) {
            e.printStackTrace() ;
        }
    }

    /**
     * Populate the Product List
     * Loop through he input path , generate the corresponding list
     * and store the result in the persistent layer
     * 
     * Input: Path to an *.xlsx file
     * Return void
     */
    private void populateProductList( String path ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm" );

        /**
         * Extract the data ftom the Excel file.
         * 1) Store each row on an array.
         * 2) Store the array content on a ProductDao
         * 3) Insert the ProductDao to the to the Database through the ProductDAORepository
         */
        try ( FileInputStream excelFile = new FileInputStream( new File( path ) );
        Workbook workbook = new XSSFWorkbook( excelFile ); ) {
            
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int count = 0;
            while ( iterator.hasNext() ) {
                Row currIterator = iterator.next();
                if( count == 0 ) {
                    count++;
                    continue;
                }
                Iterator<Cell> cellIterator = currIterator.iterator();
                String arr[] = new String[7];
                int i=0;
                int id = -1;
                int category = -1;
                while( cellIterator.hasNext() ) {
                    Cell currentCell = cellIterator.next();
                    if( currentCell.getCellType() == CellType.STRING ) 
                        arr[i] = currentCell.getStringCellValue();
                    else if( currentCell.getCellType() == CellType.NUMERIC ) {
                        if( i == 0 )
                            id = (int)currentCell.getNumericCellValue();
                        else if( i == 3 )
                            category = (int)currentCell.getNumericCellValue();
                        else if( i >= 4 ) // The date at instance 18 is not stored as a normal date
                            arr[i] = "" + LocalDateTime.ofInstant( Instant.ofEpochMilli( (long)currentCell.getNumericCellValue() ), TimeZone
                        .getDefault().toZoneId() ).format(formatter);
                        
                    }
                    i++;
                }

                // Create a ProductDao Instance
                ProductDao productDao = new ProductDao(
                        id ,
                        arr[1] ,
                        arr[2] ,
                        mp.get( category ) ,
                        arr[4] ,
                        arr[5] ,
                        arr[6] );

                // Update the persistence layer through the product repository
                productDaoRepo.save( productDao );
            }
            workbook.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Extract all the elements from the database as they are through the persistence layer
     * 
     * @return the product list
     */
    public List<ProductDao> getAllTheElementsFromTheTable() {
        
        return productDaoRepo.findAll();
    }
    
    /**
     * Populate the Product List
     * 
     * Calls :
     *  - generateCategoryMap()
     *  - populateProductList()
     * 
     * Potential change :
     *  - Make the Path as an argument of Pop() so that the paths could be managed by the controller.
     * 
     * @return the product list as they are present in the database through the persistence layer
     * @throws FileNotFoundException
     */
    public List<ProductDao> Pop( ) throws FileNotFoundException {

        String categoryPath  = "src/main/java/com/assignment/lpg3/data/categories.csv";
        String productsPath = "src/main/java/com/assignment/lpg3/data/products.xlsx";

        generateCategoryMap( categoryPath );
        populateProductList( productsPath );

        return getAllTheElementsFromTheTable();
        
    }

    /**
     * Sort the Product List
     * 
     * Get the productList and sort it by Name and then by Category
     * 
     * @return the product list as they are present in the database through the persistence layer
     * @throws FileNotFoundException
     */
    private void performTheSorting(List<ProductDao> productList) {
        
        productList.sort( Comparator.comparing(ProductDao::getName).thenComparing(ProductDao::getCategoryId));
    }

    /**
     * Sort the list by Name and then by Category in the Database through the persistence layer.
     * 
     * 1) Get the productList as it is in the database through the persistence layer
     * 2) erase the database through the persistence layer
     * 3) perform the sorting of the list by Name and then by Category
     * 4) Save the sorted list in the persistence layer
     * 5) Return the sorted productList
     * 
     * @return the sorted productList
     */
    public List<ProductDao> Sort() {

        List<ProductDao> productList = getAllTheElementsFromTheTable();
        productDaoRepo.deleteAll();

        performTheSorting(productList);

        for( ProductDao e : productList )
            productDaoRepo.save( e );

        return productList;
        
    }
    
    /**
     * View the productList as it exists in the database through the persistence laer
     * 
     * @return the list of productList
     */
    public List<ProductDao> View() {

        return getAllTheElementsFromTheTable();
    }
    
    /**
     * Clear the productList from the database throug the persistence layer
     * 
     * @return the list of productList ( Empty )
     */
    public List<ProductDao> Clear() {

        productDaoRepo.deleteAll();
        return getAllTheElementsFromTheTable();
    }

}
