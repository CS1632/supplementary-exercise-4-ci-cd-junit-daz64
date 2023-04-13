package edu.pitt.cs;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;
import org.mockito.internal.configuration.injection.MockInjection;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	@Before
	public void setUp() throws Exception {

		//Config.setBuggyRentACat(false);

		// Turn on automatic bug injection in the Cat class, to emulate a buggy Cat.
		// Your unit tests should work regardless of these bugs.
		Cat.bugInjectionOn = true;

		// INITIALIZE THE TEST FIXTURE
		// 1. Create a new RentACat object and assign to r
		r = RentACat.createInstance();

		// 2. Create an unrented Cat with ID 1 and name "Jennyanydots", assign to c1
		c1 = Mockito.mock(Cat.class);
		Mockito.when(c1.getId()).thenReturn(1);
		Mockito.when(c1.getName()).thenReturn("Jennyanydots");
		Mockito.when(c1.toString()).thenReturn("ID 1. Jennyanydots");
		//Mockito.when(c1.getRented()).thenReturn(false);
		r.addCat(c1);

		// 3. Create an unrented Cat with ID 2 and name "Old Deuteronomy", assign to c2
		c2 = Mockito.mock(Cat.class);
		Mockito.when(c2.getId()).thenReturn(2);
		Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
		Mockito.when(c2.toString()).thenReturn("ID 2. Old Deuteronomy");
		//Mockito.when(c2.getRented()).thenReturn(false);
		r.addCat(c2);

		// 4. Create an unrented Cat with ID 3 and name "Mistoffelees", assign to c3
		c3 = Mockito.mock(Cat.class);
		Mockito.when(c3.getId()).thenReturn(3);
		Mockito.when(c3.getName()).thenReturn("Mistoffelees");
		Mockito.when(c3.toString()).thenReturn("ID 3. Mistoffelees");
		//Mockito.when(c3.getRented()).thenReturn(false);
		r.addCat(c3);
	}

	@After
	public void tearDown() throws Exception {
		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * PARTNER 2: David Zapata
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 * </pre>
	 */

	@Test
	public void testGetCatNullNumCats0() {
		//Create instance of empty cat
		r = RentACat.createInstance();

		//Execution step: we get the cat and we check that it is null
		Cat cat = r.getCat(2);
		assertNull(cat);
	}

	/**
	 * PARTNER 2: David Zapata
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 */

	@Test
	public void testGetCatNumCats3() {

		// Get cat and assert it is not null and ID is 2
		Cat cat = r.getCat(2);
		assertNotNull(cat);
		assertEquals(2, cat.getId());
	}

	/**
	 * PARTNER 1: Ivan Bondarenko
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats0() {
		
		// reset RentACat list - r has no cats
		r = RentACat.createInstance();

		// Assert no cats in r
		String cats = r.listCats();
		assertEquals("", cats);

		boolean resultCatAvailable = r.catAvailable(c2.getId());
		assertFalse("Cat ID: 2 is not available to rent", resultCatAvailable);
	}

	/**
	 * PARTNER 1: Ivan Bondarenko
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c3 is rented.
	 *                c1 and c2 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatAvailableTrueNumCats3() {

		c3.rentCat();
		Mockito.when(c3.getRented()).thenReturn(true);

		boolean resultCatAvailable = r.catAvailable(c2.getId());
		assertTrue("Cat ID: 2 is available to rent", resultCatAvailable);
	}

	/**
	 * PARTNER 1: Ivan Bondarenko
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 *                c1 and c3 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats3() {

		c2.rentCat();
		Mockito.when(c2.getRented()).thenReturn(true);

		boolean resultCatAvailable = r.catAvailable(c2.getId());
		assertFalse("Cat ID: 2 is not available", resultCatAvailable);
	}

	/**
	 * PARTNER 1: Ivan Bondarenko
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatExistsFalseNumCats0() {

		// reset RentACat list - r has no cats
		r = RentACat.createInstance();

		// Assert no cats in r
		String cats = r.listCats();
		assertEquals("", cats);

		boolean resultCatExists = r.catExists(c2.getId());
		assertFalse("No cats are available for rent", resultCatExists);
	}

	/**
	 * PARTNER 1: Ivan Bondarenko
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatExistsTrueNumCats3() {

		boolean resultCatExists = r.catExists(c2.getId());
		assertTrue("Cat is available to rent", resultCatExists);
	}

	/**
	 * PARTNER 2: David Zapata
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats0() {

		//Create instance of empty cat
		r = RentACat.createInstance();

		// Assert no cats in r
		String cats = r.listCats();
		assertEquals("", cats);

	}

	/**
	 * PARTNER 2: David Zapata
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats3() {
		// Assert that all three cats have been added to r
		String cats = r.listCats();
		assertEquals("ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n", cats);
	}

	/**
	 * PARTNER 2: David Zapata
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testRentCatFailureNumCats0() {
		//Create instance of empty cat
		r = RentACat.createInstance();

		// Assert no cats in r
		String cats = r.listCats();
		assertEquals("", cats);

		boolean rent = r.rentCat(2);
		assertFalse("There are no cats yet", rent);
	}

	/**
	 * PARTNER 2: David Zapata
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c1.rentCat(), c2.rentCat(), c3.rentCat() are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testRentCatFailureNumCats3() {
		//Make sure that cat 2 is rented
		Mockito.when(c2.getRented()).thenReturn(true);

		//Try to rent cat 2
		boolean rent = r.rentCat(2);
		//Assert that we can't rent cat 2
		assertFalse("Cat 2 is not available", rent);
		//Verify that rentCat() was called for none of the cats
		Mockito.verify(c1, Mockito.times(0)).rentCat();
		Mockito.verify(c2, Mockito.times(0)).rentCat();
		Mockito.verify(c3, Mockito.times(0)).rentCat();
	}

	/**
	 * PARTNER 1: Ivan Bondarenko
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testReturnCatFailureNumCats0() {

		// reset RentACat list - r has no cats
		r = RentACat.createInstance();

		// Assert no cats in r
		String cats = r.listCats();
		assertEquals("", cats);

		//Mockito.when(c2.getId()).thenReturn(-1);

		boolean testReturnCat = r.returnCat(c2.getId());
		assertFalse("Cannot return Cat", testReturnCat);
	}

	/**
	 * PARTNER 1: Ivan Bondarenko
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2.returnCat() is called exactly once.
	 *                 c1.returnCat() and c3.returnCat are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testReturnCatNumCats3() {

		// Precondition: c2 is rented
		c2.rentCat();
		
		// Execution: returnCat(2) 
		Mockito.when(c2.getRented()).thenReturn(true);
		boolean testReturnCat = r.returnCat(c2.getId());

		// Assert rented cat is returned
		assertTrue("Cat not returned", testReturnCat);

		// Behavior verification
		Mockito.verify(c1, Mockito.times(0)).returnCat();
		Mockito.verify(c2, Mockito.times(1)).returnCat();
		Mockito.verify(c3, Mockito.times(0)).returnCat();
	}
}