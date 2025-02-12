package com.app.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="orderedfood")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "food")
public class OrderedFood extends BaseEntity{
	private Long quantity;
//	@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne
	@JoinColumn(nullable = true)
	private Food food;
	
	public OrderedFood(Long quantity) {
		this.quantity=quantity;
	}

}
