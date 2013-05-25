/**
 * This class contains assorted helper methods for other test classes
 * 
 * @author Bennett Ng, 5/9/2013
 */

package com.huskysoft.interviewannihilator.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.model.UserInfo;

@SuppressLint("UseSparseArrays")
public class TestHelpers {

	public static final int VALID_QUESTION_ID = 10;
	public static final String TEST_USER_EMAIL = "admin@huskysoft.com";
	public static final int TEST_USER_ID = 0;

	public static Question createDummyQuestion(int i) {
		Question q = new Question();
		q.setAuthorId(i + 1);
		q.setCategory(Category.BRAINTEASER);
		q.setDateCreated(new Date());
		q.setDifficulty(Difficulty.MEDIUM);
		q.setQuestionId(i);
		q.setTitle("Question no. " + i);
		q.setText("Hello world" + i);
		return q;
	}

	public static Solution createDummySolution(int i) {
		Solution s = new Solution();
		s.setAuthorId(i + 1);
		s.setDateCreated(new Date());
		s.setSolutionId(i);
		s.setQuestionId(i);
		s.setText("Some solution" + i);
		return s;
	}

	public static UserInfo createTestUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserEmail(TEST_USER_EMAIL);
		userInfo.setUserId(TEST_USER_ID);
		return userInfo;
	}

	public static UserInfo createDummyUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserEmail("johndoe@email.com");
		userInfo.setUserId(1234);
		userInfo.setFavoriteQuestions(createIntegerDateMap(10, 0));
		userInfo.setViewedQuestions(createIntegerDateMap(20, 1024));
		userInfo.setVotedQuestions(createIntegerBooleanMap(10, 0));
		userInfo.setVotedSolutions(createIntegerBooleanMap(10, 50));
		return userInfo;
	}

	private static Map<Integer, Boolean> createIntegerBooleanMap(
			int size, int initial) {
		Map<Integer, Boolean> m = new HashMap<Integer, Boolean>();
		for (int i = 0; i < size; i++) {
			m.put(i + initial, i % 2 == 1);
		}
		return m;
	}

	private static SortedMap<Integer, Date> createIntegerDateMap(
			int size, int initial) {
		SortedMap<Integer, Date> m = new TreeMap<Integer, Date>();
		for (int i = 0; i < size; i++) {
			m.put(i + initial, new Date());
		}
		return m;
	}
}