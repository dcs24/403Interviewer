/**
 * This class contains tests for the NetworkService class
 * 
 * @author Bennett Ng, 5/9/2013
 */

package com.huskysoft.interviewannihilator.service;

import java.util.ArrayList;
import java.util.List;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;

import junit.framework.TestCase;


public class NetworkServiceTest extends TestCase {

	private NetworkService networkService;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public NetworkServiceTest(String name) {
		super(name);
		this.networkService = NetworkService.getInstance();
	}

	/**
	 * Tests the method getQuestions in NetworkService.java that is called
	 * by QuestionService.java
	 * 
	 * @label Black-box testing
	 * @throws NetworkException
	 */
	public void testGetAllQuestions() throws NetworkException {
		assertNotNull(networkService.getQuestions(
				null, null, 10, 0, false, null));
	}

	/**
	 * Gets a random set of questions
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetQuestionsRandom() throws NetworkException {
		assertNotNull(networkService.getQuestions(
				null, null, 10, 0, true, null));
	}

	/**
	 * Gets a set of questions only of one category
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetQuestionsByCategory() throws NetworkException {
		List<Category> categories = new ArrayList<Category>();
		categories.add(Category.BRAINTEASER);
		assertNotNull(networkService.getQuestions(
				null, categories, 10, 0, false, null));		
	}

	/**
	 * Gets a set of questions only of certain categories
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetQuestionsByMultipleCategories() throws NetworkException {
		List<Category> categories = new ArrayList<Category>();
		categories.add(Category.COMPSCI);
		categories.add(Category.BUSINESS);
		assertNotNull(networkService.getQuestions(
				null, categories, 10, 0, false, null));
	}

	/**
	 * Gets a set of questions by a certain difficulty level
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetQuestionsByDifficulty() throws NetworkException {
		assertNotNull(networkService.getQuestions(
				Difficulty.MEDIUM, null, 5, 0, false, null));
	}

	/**
	 * Gets a set of questions by a certain difficulty level and a certain
	 * list of categories
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetQuestionsByCategoryAndDifficulty()
	throws NetworkException {
		List<Category> categories = new ArrayList<Category>();
		categories.add(Category.COMPSCI);
		categories.add(Category.BUSINESS);
		assertNotNull(networkService.getQuestions(
				Difficulty.MEDIUM, categories, 5, 0, false, null));		
	}

	/**
	 * Gets a set of questions starting at a certain offset within
	 * the database
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetQuestionsWithOffset() throws NetworkException {
		assertNotNull(networkService.getQuestions(
				null, null, 5, 5, false, null));
	}

	/**
	 * Tests the method getSolutions in NetworkService.java that is called by
	 * QuestionService.java
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetAllSolutions() throws NetworkException {
		String result = networkService.getSolutions(10, 1, 0);
		assertNotNull(result);
		System.out.println(result);
	}

	/**
	 * Tests the method getSolutions with a questionId that is not in the
	 * database
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 */
	public void testGetAllSolutionsWithBadQuestion() throws NetworkException {
		assertNotNull(networkService.getSolutions(0, 5, 0));
	}
}