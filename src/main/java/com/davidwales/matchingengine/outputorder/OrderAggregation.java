package com.davidwales.matchingengine.outputorder;

import com.davidwales.matchingengine.inputorder.OrderStatus;

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
	public void incrementDecrementAggregation(OrderStatus oldStatus, OrderStatus newStatus)
	{
		
		if(oldStatus == OrderStatus.NA && newStatus == OrderStatus.NEW)
		{
			increment(newStatus);
		}
		else if(oldStatus != newStatus)
		{
			increment(newStatus);
			decrement(oldStatus);
		} 
	}
	
	public void incrementOrDecrement(boolean isIncrement, OrderStatus status)
	{
		switch (status) 
		{
			case NEW:
				if(isIncrement) news++; else news--;
				break;
			case PARTIAL:
				if(isIncrement) partials++; else partials--;
				break;
			case FILLED:
				if(isIncrement) fills++; else fills--;
				break;
			default:
				throw new IllegalStateException("Transistionary non incrementable state");
		}	
	}
	
	public void increment(OrderStatus status)
	{
		incrementOrDecrement(true, status);
	}
	
	public void decrement(OrderStatus status)
	{
		incrementOrDecrement(false, status);
	}

}
