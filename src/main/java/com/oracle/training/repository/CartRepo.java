package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import com.oracle.training.model.Cart;
<<<<<<< HEAD
import com.oracle.training.model.Payment;


public interface CartRepo extends CrudRepository<Cart,Integer>
{
	Cart findById(int id);
}
=======
import com.oracle.training.model.User;


public interface CartRepo extends CrudRepository<Cart,Integer>{
	@Nullable
	Cart findByUser(User user);
}
>>>>>>> ae5fa43 (added CART Controller)
