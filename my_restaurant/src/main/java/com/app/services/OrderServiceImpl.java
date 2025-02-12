package com.app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.custom_exception.NoContentException;
import com.app.custom_exception.OrderCancellationException;
import com.app.custom_exception.ResourceNotFound;
import com.app.custom_exception.TableSlotNotAvailableException;
import com.app.dao.DineTableDao;
import com.app.dao.FoodDao;
import com.app.dao.OrderDao;
import com.app.dao.OrderedFoodDao;
import com.app.dao.TableBookingSlotDao;
import com.app.dao.UserDao;
import com.app.dto.FoodOrderedDto;
import com.app.dto.OrderFoodResponseDTO;
import com.app.dto.OrderedFoodNameQuantityDTO;
import com.app.dto.ReservationDTO;
import com.app.entities.DineTable;
import com.app.entities.Food;
import com.app.entities.Order;
import com.app.entities.OrderStatus;
import com.app.entities.OrderedFood;
import com.app.entities.TableBookingSlot;
import com.app.entities.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private UserDao userDao;
	private FoodDao foodDao;
	private DineTableDao dineTableDao;
	private OrderDao orderDao;
	private OrderedFoodDao orderedFoodDao;
	private TableBookingSlotDao slotDao;
	private ModelMapper mapper;
	
	@Override
	public String bookTable(Long userId, ReservationDTO rsDto) {
		try {
			//GET User 
			User user=userDao.findById(userId)
			.orElseThrow(()->new ResourceNotFound("User Not Found"));
			
			//Get DineTable 
			DineTable dineTable=dineTableDao.findById(rsDto.getTableId())
			.orElseThrow(()->new ResourceNotFound("Table Not Found"));
			
			//Get Booking Slot
			TableBookingSlot slot = slotDao.findById(rsDto.getBookingSlotId())
			.orElseThrow(()->new ResourceNotFound("Slot Not Found"));
			
			//Check If Table Is Already Booked At That Time Slot
			if(dineTable.getBookingSlots().contains(slot)) {
				throw new TableSlotNotAvailableException("Table Is Not Available "+slot.getBookingSlot());
			}
			
			//Add Slot To Table
			dineTable.addSlot(slot);
			
			//Create Order Instance
			Order order=new Order();
			//Add List Of OrderedFoods To The Order
			rsDto.getFoodDtoList().stream()
			.forEach((orderedFood)->{
				//Get Food
				Food food = foodDao.findById(orderedFood.getFoodId())
				.orElseThrow(()->new ResourceNotFound("Food Not Found"));
				
				//Create OrderedFood instance and add it to list of ordered foods in order instance
				order.orderFood(new OrderedFood(orderedFood.getQuantity(),food));
				
			});
			
			//Add DineTable to Order
			order.setBookedTable(dineTable.getId());
			
			//Set Reservation Time
			order.setReservationTime(slot.getBookingSlot());;
			
			//Set Order Status
			order.setOrderStatus(OrderStatus.PLACED);
			//set user for order
			order.setUser(user);
			//Place Order
			user.addOrder(order); //Here,Order is persistent Now
			return "Table Booked Successfully";
			
		} catch (ResourceNotFound e) {
			throw new ResourceNotFound(e.getMessage());
		}catch(TableSlotNotAvailableException e) {
			throw new TableSlotNotAvailableException(e.getMessage());
		}
		catch(RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	

	@Override
	public OrderFoodResponseDTO getOrderById(Long orderId) {
		try {
			//Get Order From Database
			Order order = orderDao.findById(orderId)
			.orElseThrow(()->new ResourceNotFound("No Order Found By Id "+orderId));
			
			//Create object of response dto
			OrderFoodResponseDTO orderResponse=new OrderFoodResponseDTO();
			
			//Get List Of Ordered Food and them to list foodDesc
			List<OrderedFoodNameQuantityDTO> foodDesc=new ArrayList<>();
			order.getFoods().stream()
			.forEach((foodOrdered)->{
				foodDesc.add(new OrderedFoodNameQuantityDTO(foodOrdered.getId(),foodOrdered.getFood().getName(),foodOrdered.getQuantity(),foodOrdered.getFood().getImageURL()));
			});
		
			//get table 
			DineTable table = dineTableDao.findById(order.getBookedTable())
			.orElseThrow(()->new ResourceNotFound("Table Not Found"));
			
			//Get table number and location
			String tableLocation = table.getLocation().toString();
			Long tableNumber = table.getTableNumber();
			
			//Set all the fileds of OrderedFoodNameQuantityDTO
			orderResponse.setFoods(foodDesc);
			orderResponse.setTableLocation(tableLocation);
			orderResponse.setTableNumber(tableNumber);

			orderResponse.setBookedTime(order.getCreatedAt().toString());
			orderResponse.setId(orderId);
			orderResponse.setStatus(order.getOrderStatus().toString());
			return orderResponse;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String cancelOrder(Long userId,Long orderId) {
		System.out.println(userId+" "+orderId);
		try {
			//Find User By UserId or else throw exception
			User user = userDao.findById(userId).orElseThrow(()-> new ResourceNotFound("User Not Found"));
			System.out.println(userId+" "+orderId);
			//Get Order From Database
			Order order = orderDao.findById(orderId)
			.orElseThrow(()->new ResourceNotFound("No Order Found By Id "+orderId));
			System.out.println(LocalDate.now().toString());
			int now=Integer.parseInt(LocalDate.now().toString().substring(6, 8).replaceAll("[^0-9]", ""));
			System.out.println(order.getReservationTime());
			System.out.println(now);
			if(now>Integer.parseInt(order.getReservationTime().substring(0,2))) {
				throw new OrderCancellationException("Order Can Be cancelled Before one Hour");
			}
			order.setOrderStatus(OrderStatus.CANCELLED);
			//get table 
//			DineTable table = dineTableDao.findById(order.getBookedTable())
//			.orElseThrow(()->new ResourceNotFound("Table Not Found"));
//			//set table status to open
//			table.setStatus(TableStatus.OPEN);
//			
//			//Get Booking Slot;
//			TableBookingSlot slot = slotDao.findByBookingSlot(order.getReservationTime());
//			if(slot==null) {
//				throw new RuntimeException("Booking Slot Not Found");
//			}
//			//remove Slot from table;
//			table.removeSlot(slot);
//			//remove orderedfood record
//			order.getFoods().stream()
//				.forEach((orderedFood)->orderedFoodDao.delete(orderedFood));
//			
//			//delete order from user
//			user.cancelOrder(order);
//			//remove order from database
//			orderDao.delete(order);
			return "Order Cancelled Successfully";
			
		} catch(OrderCancellationException e) {
			System.out.println(e.getMessage());
			throw new OrderCancellationException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String addFoodItemToOrder(Long userId, Long orderId, FoodOrderedDto newFoodItem) {
		try {
			//Find User By UserId or else throw exception
			User user = userDao.findById(userId).orElseThrow(()-> new ResourceNotFound("User Not Found"));
			//Get Order From Database
			Order order = orderDao.findById(orderId)
			.orElseThrow(()->new ResourceNotFound("No Order Found By Id "+orderId));
			
			//Get Food that is to be added
			Food food = foodDao.findById(newFoodItem.getFoodId())
			.orElseThrow(()->new ResourceNotFound("No Food Item Found"));
			
			OrderedFood addedFood=new OrderedFood();
			addedFood.setFood(food);
			addedFood.setQuantity(newFoodItem.getQuantity());
			
			// Add new food item to the order
			order.orderFood(addedFood);
			return "New Food Item Added";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public String removeFoodItemFromOrder(Long orderId, Long foodId) {
		try {
			//Get Order From Database
			Order order = orderDao.findById(orderId)
			.orElseThrow(()->new ResourceNotFound("No Order Found By Id "+orderId));
			System.out.println("foodid "+foodId+" ");
			//Get food
			OrderedFood foodObj = order.getFoods().stream()
			.filter((food)->food.getId().equals(foodId)).findFirst().orElseThrow(()->new ResourceNotFound());
			order.removeFood(foodObj);
			System.out.println("vghvhvhgch"+" "+foodObj);
			orderedFoodDao.deleteById(foodObj.getId());
			return "Food Items Updated";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<OrderFoodResponseDTO> getAllOrders() {
		try {
			List<Order> orders = orderDao.findAll();
			if(orders.size()==0) {
				throw new NoContentException("No Orders Found");
			}
			List<OrderFoodResponseDTO> orderRes=new ArrayList<>();
			
			orders.stream().forEach((order)->{
				OrderFoodResponseDTO orderResponse=new OrderFoodResponseDTO();
				
				//Get List Of Ordered Food and them to list foodDesc
				List<OrderedFoodNameQuantityDTO> foodDesc=new ArrayList<>();
				order.getFoods().stream().filter(foodOrdered->foodOrdered.getFood()!=null)
				.forEach((foodOrdered)->{
					foodDesc.add(new OrderedFoodNameQuantityDTO(foodOrdered.getId(),foodOrdered.getFood().getName(),foodOrdered.getQuantity(),foodOrdered.getFood().getImageURL()));
				});
			
				//get table 
				DineTable table = dineTableDao.findById(order.getBookedTable())
				.orElseThrow(()->new ResourceNotFound("Table Not Found"));
				
				//Get table number and location
				String tableLocation = table.getLocation().toString();
				Long tableNumber = table.getTableNumber();
				
				//Set all the fileds of OrderedFoodNameQuantityDTO
//				orderResponse.setFoods(foodDesc);
//				orderResponse.setTableLocation(tableLocation);
//				orderResponse.setTableNumber(tableNumber);
//
//				orderResponse.setBookedTime(order.getCreatedAt().toString());
//				orderResponse.setId(order.getId());
//				orderResponse.setStatus(order.getOrderStatus().toString());
//				orderRes.add(orderResponse);
				orderResponse.setFoods(foodDesc);
				orderResponse.setTableLocation(tableLocation);
				orderResponse.setTableNumber(tableNumber);
				String t=order.getCreatedAt().toString();
				orderResponse.setBookedTime(t.substring(0,10)+" "+t.substring(12,19));
				orderResponse.setId(order.getId());
				orderResponse.setPrice(order.getPrice());
				orderResponse.setReservationTime(order.getReservationTime());
				orderResponse.setStatus(order.getOrderStatus().toString());
				orderRes.add(orderResponse);
			});
			
			return orderRes;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Internal Server Error");
		}
		
	}

	@Override
	public List<OrderFoodResponseDTO> getOrderByOrderStatus(String status) {
		try {
			List<Order> orders = orderDao.findByOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
			if(orders.size()==0) {
				throw new NoContentException("No Orders Found");
			}
			List<OrderFoodResponseDTO> orderRes=new ArrayList<>();
			
			orders.stream().forEach((order)->{
				OrderFoodResponseDTO orderResponse=new OrderFoodResponseDTO();
				
				//Get List Of Ordered Food and them to list foodDesc
				List<OrderedFoodNameQuantityDTO> foodDesc=new ArrayList<>();
				order.getFoods().stream()
				.forEach((foodOrdered)->{
					foodDesc.add(new OrderedFoodNameQuantityDTO(foodOrdered.getId(),foodOrdered.getFood().getName(),foodOrdered.getQuantity(),foodOrdered.getFood().getImageURL()));
				});
			
				//get table 
				DineTable table = dineTableDao.findById(order.getBookedTable())
				.orElseThrow(()->new ResourceNotFound("Table Not Found"));
				
				//Get table number and location
				String tableLocation = table.getLocation().toString();
				Long tableNumber = table.getTableNumber();
				
				//Set all the fileds of OrderedFoodNameQuantityDTO
//				orderResponse.setFoods(foodDesc);
//				orderResponse.setTableLocation(tableLocation);
//				orderResponse.setTableNumber(tableNumber);
//				orderResponse.setReservationTime(order.getReservationTime());
//				orderResponse.setBookedTime(order.getCreatedAt().toString());
//				orderResponse.setId(order.getId());
//				orderRes.add(orderResponse);
				orderResponse.setFoods(foodDesc);
				orderResponse.setTableLocation(tableLocation);
				orderResponse.setTableNumber(tableNumber);
				String t=order.getCreatedAt().toString();
				orderResponse.setBookedTime(t.substring(0,10)+" "+t.substring(12,19));
				orderResponse.setId(order.getId());
				orderResponse.setPrice(order.getPrice());
				orderResponse.setReservationTime(order.getReservationTime());
				orderResponse.setStatus(order.getOrderStatus().toString());
				orderRes.add(orderResponse);
			});
			
			return orderRes;
			
		} catch (Exception e) {
			throw new RuntimeException("Internal Server Error");
		}
	}

	@Override
	public List<OrderFoodResponseDTO> getOrderByUser(Long userId) {
		try {
			//Get User from userid
			User user=userDao.findById(userId)
			.orElseThrow(()->new ResourceNotFound("User Not Found"));
			
			//Get All Orders Of The User
			List<Order> orders = user.getOrders();
			if(orders.size()==0) {
				throw new NoContentException("No Orders");
			}
			
			List<OrderFoodResponseDTO> orderRes=new ArrayList<>();
			orders.stream().forEach((order)->{
				OrderFoodResponseDTO orderResponse=new OrderFoodResponseDTO();
				
				//Get List Of Ordered Food and them to list foodDesc
				List<OrderedFoodNameQuantityDTO> foodDesc=new ArrayList<>();
				order.getFoods().stream()
				.forEach((foodOrdered)->{
					foodDesc.add(new OrderedFoodNameQuantityDTO(foodOrdered.getId(),foodOrdered.getFood().getName(),foodOrdered.getQuantity()
							,foodOrdered.getFood().getImageURL()));
				});
				
				//get table 
				DineTable table = dineTableDao.findById(order.getBookedTable())
				.orElseThrow(()->new ResourceNotFound("Table Not Found"));
				
				//Get table number and location
				String tableLocation = table.getLocation().toString();
				Long tableNumber = table.getTableNumber();
				
				//Set all the fileds of OrderedFoodNameQuantityDTO
				orderResponse.setFoods(foodDesc);
				orderResponse.setTableLocation(tableLocation);
				orderResponse.setTableNumber(tableNumber);
				String t=order.getCreatedAt().toString();
				orderResponse.setBookedTime(t.substring(0,10)+" "+t.substring(12,19));
				orderResponse.setId(order.getId());
				orderResponse.setPrice(order.getPrice());
				orderResponse.setReservationTime(order.getReservationTime());
				orderResponse.setStatus(order.getOrderStatus().toString());
				orderRes.add(orderResponse);
			});
			
			return orderRes;
		} catch (Exception e) {
			throw new RuntimeException("Internal Server Error");
		}
		
	}

}















