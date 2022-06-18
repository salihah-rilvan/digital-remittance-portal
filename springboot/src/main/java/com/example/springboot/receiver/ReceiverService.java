package com.example.springboot.receiver;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService {

	@Autowired
	private ReceiverRepository receiverRepository;
	

	public Optional<Receiver> findReceiverById(long id) {
		return receiverRepository.findById(id);
   }

	// Deletes receiver from database
	public void deleteReceiver(Long receiverId){
		boolean exists = receiverRepository.existsById(receiverId);
		if (!exists){
			throw new IllegalStateException("Receiver with id " + receiverId + " does not exists");
		}
		receiverRepository.deleteById(receiverId);
	}

}