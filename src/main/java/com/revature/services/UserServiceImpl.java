package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repos.UserRepo;
import com.revature.utils.CognitoUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;

	@Autowired
	CognitoUtil cUtil;

	@Override
	public List<User> findAll() {
		return userRepo.findAll();

	}

	@Override
	public User findOneById(int id) {
		return userRepo.findOneByUserId(id);

	}

	@Override
	public List<User> findAllByCohortId(int id) {
		return userRepo.findAllByCohortsCohortId(id);

	}

	@Override
	public User saveUser(User u) {
		return userRepo.save(u);
	}

	@Override
	public User findOneByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public User findOneByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public User updateProfile(User u) {
		User tempAppUser = userRepo.findById(u.getUserId()).get();
		if (u.getFirstName() != null) {
			tempAppUser.setFirstName(u.getFirstName());
		}
		if (u.getLastName() != null) {
			tempAppUser.setLastName(u.getLastName());
		}
//	 	Email doesn't get updated with cognito so we
//		have to fix that before we can change our email
//		if (u.getEmail() != null) {
//			tempAppUser.setEmail(u.getEmail());
//		}
		userRepo.save(tempAppUser);
		return tempAppUser;
	}

	public User userInfo() {
		String email = cUtil.extractTokenEmail();
		return userRepo.findByEmail(email);
	}

}
