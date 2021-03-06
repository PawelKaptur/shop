package com.capgemini;


import com.capgemini.queries.QClientTest;
import com.capgemini.queries.QProductTest;
import com.capgemini.queries.QTransactionTest;
import com.capgemini.service.ClientTest;
import com.capgemini.service.ProductTest;
import com.capgemini.service.TransactionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ClientTest.class, ProductTest.class, TransactionTest.class, QClientTest.class, QProductTest.class, QTransactionTest.class})
public class AllTests {
}
