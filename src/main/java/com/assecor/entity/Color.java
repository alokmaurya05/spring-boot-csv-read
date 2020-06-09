package com.assecor.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="COLOR")
@Data
@NoArgsConstructor
public class Color
{
    @Id
    private int color_id;

    private String color;
    
    public Color (int id)
    {
    	this.color_id =id	;
    }
   
}
