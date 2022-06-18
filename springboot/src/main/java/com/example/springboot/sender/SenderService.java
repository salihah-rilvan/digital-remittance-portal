package com.example.springboot.sender;
import java.util.*;

import javax.transaction.Transactional;

import com.example.springboot.login.Role;
import com.example.springboot.login.RoleRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

	@Autowired
	private SenderRepository senderRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 

	public Optional<Sender> findSenderByEmail(String email) {
	 	return senderRepository.findByEmail(email);
	}

	public Optional<Sender> findSenderById(long id) {
		return senderRepository.findById(id);
   }

   
	// Takes in posts requests with sender in Json format and adds to our database
	public void addNewSender(Sender sender){
		Optional<Sender> senderById = 
			senderRepository.findByEmail(sender.getEmail());
		if (senderById.isPresent()){
			throw new IllegalStateException("Email taken");
		}

		sender.setPassword(bCryptPasswordEncoder.encode(sender.getPassword()));
		sender.setActive(true);

		senderRepository.save(sender);
		
		Role userRole = new Role(sender.getId(), "USER_ROLE");
		roleRepository.save(userRole);
	}

	// Deletes students from database
	public void deleteSender(Long senderId){
		boolean exists = senderRepository.existsById(senderId);
		if (!exists){
			throw new IllegalStateException("Sender with id " + senderId + " does not exists");
		}
		senderRepository.deleteById(senderId);
	}

	// Edits sender in database
	@Transactional // Entity goes into managed state
	public void updateSenderCompany(Long senderId,
							String company){
			Sender sender = senderRepository.findById(senderId).orElseThrow(() -> new IllegalStateException(
				"sender with id " + senderId + " does not exist"
			));

		if (company != null && company.length() > 0 && !Objects.equals(sender.getCompany(), company)){
			sender.setCompany(company);
			senderRepository.save(sender);
		}
	}

	@Transactional // Entity goes into managed state
	public void updateSenderPassword(Long senderId, String password){
		Sender sender = senderRepository.findById(senderId).get();
		sender.setPassword(bCryptPasswordEncoder.encode(password));
		senderRepository.save(sender);
		
	}



}
