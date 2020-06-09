package com.assecor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER")
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String lastName;

	@Column(name = "ZIPCODE", length = 6)
	private Integer zipCode;

	private String city;

	@Transient
	private String color;

	@OneToOne
	@JoinColumn(name = "COLOR_ID")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Color colorMapping;

	public User(Long id, String name, String lastName, Integer zipCode, String city, String color) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.zipCode = zipCode;
		this.city = city;
		this.color = color;
	}

	public Long getId() {
		if (colorMapping != null) {
			this.color = colorMapping.getColor();
		}
		return id;
	}

}
