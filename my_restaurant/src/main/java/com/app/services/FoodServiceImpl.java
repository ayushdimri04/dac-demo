package com.app.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.NoContentException;
import com.app.custom_exception.ResourceNotFound;
import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.FoodDao;
import com.app.dao.OrderedFoodDao;
import com.app.dto.FoodDTO;
import com.app.entities.Food;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodDao foodDao;
	
	@Autowired
	private OrderedFoodDao orderFoodDao;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String addFood(FoodDTO foodDto) {
		// TODO Auto-generated method stub
		try {

			foodDao.save(modelMapper.map(foodDto, Food.class));
			

			return "Food Added successfully!!";
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Unable to add food!");
		}
	}

	@Override
	public String deleteFood(Long foodId) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Food Id = " + foodId);

			Food food = foodDao.findById(foodId).orElseThrow(() -> new ResourceNotFound("Food Not Found"));
			
			orderFoodDao.findByFood(food).forEach((order)->{
				order.setFood(null);
			});
			
			foodDao.delete(food);
			System.out.println("deleting Food");
			return "Food deleted successfully!!";

		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new ResourceNotFound("Unable to delete food!");
		}
	}

	@Override
	public FoodDTO updateFood(Long foodId, FoodDTO foodDto) {
		// TODO Auto-generated method stub
		try {
			if (foodDao.existsById(foodId)) {
				Food food = foodDao.findById(foodId).orElseThrow(() -> new ResourceNotFound("Food Not Found"));
				;
				food.setName(foodDto.getName());
				food.setDescription(foodDto.getDescription());
				food.setPrice(foodDto.getPrice());
				food.setImageURL(foodDto.getImageURL());
				foodDao.save(food);
				return modelMapper.map(food, FoodDTO.class);
			} else {
				throw new ResourceNotFound("Resource Not Found Exception");
			}
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Unable to add food!");
		}
	}

	@Override
	public List<FoodDTO> getAllFood() {
		// TODO Auto-generated method stub
		try {
			List<Food> foods = foodDao.findAll();
			List<FoodDTO> foodListDTO = new ArrayList<>();
			for (Food f : foods) {
				foodListDTO.add(modelMapper.map(f, FoodDTO.class));
			}
			return foodListDTO;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new NoContentException("Unable to find food!");
		}
	}

}
