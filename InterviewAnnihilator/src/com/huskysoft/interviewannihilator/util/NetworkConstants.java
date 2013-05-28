/**
 * Defines any network constants we use to avoid hard-coding them into the
 * application code
 * 
 * @author Dan Sanders, 05/04/2013
 */

package com.huskysoft.interviewannihilator.util;

public interface NetworkConstants {
	/** The URI of our server for both solution and question. */
	public static final String BASE_URI = 
			"https://students.washington.edu/bkng/cse403/403Interviewer-php/";

	/** The 'question' directory */
	public static final String QUESTION = "question/";

	/** The 'solution' directory */
	public static final String SOLUTION = "solution/";
	
	/** The 'authentication' directory */
	public static final String AUTHENTICATION = "authentication/";

	/** The script for getQuestions */
	public static final String GET_QUESTIONS_URL = 
			BASE_URI + QUESTION	+ "getQuestions.php";

	/** The script for getSolutions */
	public static final String GET_SOLUTIONS_URL = 
			BASE_URI + SOLUTION	+ "getSolutions.php";

	/** The script for postQuestion */
	public static final String POST_QUESTION_URL = 
			BASE_URI + QUESTION	+ "postQuestion.php";
	
	/** The script for postSolution */
	public static final String POST_SOLUTION_URL = 
			BASE_URI + SOLUTION	+ "postSolution.php";

	/** The script for getQuestionsById */
	public static final String GET_QUESTIONS_BYID_URL =
			BASE_URI + QUESTION + "getQuestionsById.php";
	
	/** The script for deleteQuestion */
	public static final String DELETE_QUESTION_URL =
			BASE_URI + QUESTION + "deleteQuestion.php";
	
	/** The script for deleteSolution */
	public static final String DELETE_SOLUTION_URL =
			BASE_URI + SOLUTION + "deleteSolution.php";
	
	/** The script for getUserId */
	public static final String GET_USERID_URL =
			BASE_URI + AUTHENTICATION + "getUserId.php";
	
	/** The script for voting on a question */
	public static final String VOTE_QUESTION_URL =
			BASE_URI + QUESTION + "voteQuestion.php";
	
	/** The script for voting on a solution */
	public static final String VOTE_SOLUTION_URL =
			BASE_URI + SOLUTION + "voteSolution.php";
	
	/**
	 * If a request for multiple categories happens, this token separates out
	 * the categories
	 */
	public static final String CATEGORY_DELIMITER = "-";

	/** Ampersand for get URL requests */
	public static final String AMPERSAND = "&";

	/** HTTP parameters */
	public static final String PARAM_LIMIT = "limit";
	public static final String PARAM_OFFSET = "offset";
	public static final String PARAM_DIFFICULTY = "difficulty";
	public static final String PARAM_CATEGORY = "category";
	public static final String PARAM_QUESTIONID = "questionId";
	public static final String PARAM_SOLUTIONID = "solutionId";
	public static final String PARAM_VOTE = "vote";
	public static final String PARAM_RANDOM = "random";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_AUTHORID = "authorId";
	
	/** The default connection timeout (ms) */
	public static final int CONN_TIMEOUT = 5000;
	
	/** The default socket timeout (ms) */
	public static final int SOCK_TIMEOUT = 8000;
}
