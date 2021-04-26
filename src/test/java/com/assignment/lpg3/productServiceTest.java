package com.assignment.lpg3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import com.assignment.lpg3.dao_management.dao.ProductDao;
import com.assignment.lpg3.dao_management.repository.ProductDaoRepository;
import com.assignment.lpg3.services.ProductService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class productServiceTest {


    ProductService prod = null;
    ProductDaoRepository articleRepo = mock(ProductDaoRepository.class);
    
    @Before
    public void setUp() {
        prod = new ProductService( articleRepo );
    }
    
    @Test
    public void testEmptyDatabase() throws Exception {
        Mockito.when(articleRepo.findAll()).thenReturn(null);
        List<ProductDao> res = prod.View( );
        List<ProductDao> cmp = null;
        assertEquals( res , cmp );
    }
    
    @Test
    public void testFullDatabase() throws Exception {

        List<ProductDao> list = dummyList();
        Mockito.when(articleRepo.findAll()).thenReturn(list);
        List<ProductDao> res = prod.View( );
        assertEquals( res , list );
    }
    
    @Test
    public void testSortById() throws Exception {

        List<ProductDao> list = dummyList();
        List<ProductDao> listSortedById = dummyListIdSorted();
        Mockito.when(articleRepo.findAll()).thenReturn(list);
        List<ProductDao> res = prod.singleSort( "Id" );
        assertEquals( res , listSortedById );
    }
    
    @Test
    public void testSortByName() throws Exception {

        List<ProductDao> list = dummyList();
        List<ProductDao> listSortedByName = dummyListNameSorted();
        Mockito.when(articleRepo.findAll()).thenReturn(list);
        List<ProductDao> res = prod.singleSort( "Name" );
        assertEquals( res , listSortedByName );
    }
    
    @Test
    public void testSortByCategory() throws Exception {

        List<ProductDao> list = dummyList();
        List<ProductDao> listSortedByCategory = dummyListCategorySorted();
        Mockito.when(articleRepo.findAll()).thenReturn(list);
        List<ProductDao> res = prod.singleSort( "Category" );
        assertEquals( res , listSortedByCategory );
    }
    
    @Test
    public void testClear() throws Exception {
        Mockito.when(articleRepo.findAll()).thenReturn(null);
        List<ProductDao> res = prod.Clear( );
        List<ProductDao> cmp = null;
        assertEquals( res , cmp );
    }

    List<ProductDao> dummyList() {

        List<ProductDao> list = new ArrayList<>();

        list.add( new ProductDao( 1 , "b" , "a" , "a" , "a" , "a" , "a" ) );
        list.add( new ProductDao( 2 , "a" , "a" , "b" , "a" , "a" , "a" ) );
        list.add( new ProductDao( 3 , "a" , "c" , "a" , "a" , "a" , "a" ) );

        return list;
    }

    List<ProductDao> dummyListIdSorted() {
        return dummyList();
    }

    List<ProductDao> dummyListNameSorted() {

        List<ProductDao> list = new ArrayList<>();

        list.add( new ProductDao( 2 , "a" , "a" , "b" , "a" , "a" , "a" ) );
        list.add( new ProductDao( 3 , "a" , "c" , "a" , "a" , "a" , "a" ) );
        list.add( new ProductDao( 1 , "b" , "a" , "a" , "a" , "a" , "a" ) );

        return list;
    }

    List<ProductDao> dummyListCategorySorted() {

        List<ProductDao> list = new ArrayList<>();

        list.add( new ProductDao( 1 , "b" , "a" , "a" , "a" , "a" , "a" ) );
        list.add( new ProductDao( 3 , "a" , "c" , "a" , "a" , "a" , "a" ) );
        list.add( new ProductDao( 2 , "a" , "a" , "b" , "a" , "a" , "a" ) );

        return list;
    }
}
