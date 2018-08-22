package com.capgemini;


import com.capgemini.service.ClientTest;
import com.capgemini.service.ProductTest;
import com.capgemini.service.TransactionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ClientTest.class, ProductTest.class, TransactionTest.class})
public class AllTests {
}
