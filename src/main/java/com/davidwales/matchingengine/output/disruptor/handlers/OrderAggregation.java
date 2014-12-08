package com.davidwales.matchingengine.output.disruptor.handlers;

public class OrderAggregation implements Aggregation
{

	private volatile int news;
	
	private volatile int fills;
	
	private volatile int partials;
	
	public OrderAggregation()
	{
		news = 0;
		fills = 0;
		partials = 0;
	}

	public int getNews() 
	{
		return news;
	}

	public void setNews(int news) 
	{
		this.news = news;
	}

	public int getFills() 
	{
		return fills;
	}

	public void setFills(int fills) 
	{
		this.fills = fills;
	}

	public int getPartials() 
	{
		return partials;
	}

	public void setPartials(int partials) 
	{
		this.partials = partials;
	}
	
	@Override
	public void incrementNews(){
		//only one thread will be writing
		news++;
	}

}
