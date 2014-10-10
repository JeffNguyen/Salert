package com.example.salert;

public class Item
{
	private String imgUrl;
	private String description;
	private int id;
	private static int idCounter = 0;
	
	public Item()
	{
		this.imgUrl = null;
		this.description = "";
		id = 0;
	}
	
	public Item(String url, String description)
	{
		this.imgUrl = url;
		this.description = description;
		idCounter++;
		this.id = idCounter;
	}
	
	public Item(int id, String url, String description)
	{
		this.imgUrl = url;
		this.description = description;
		this.id = idCounter;
	}

	public int getID()
	{
		return this.id;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public String getImgUrl()
	{
		return this.imgUrl;
	}
	
	public void setImgUrl(String url)
	{
		this.imgUrl = url;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}

}