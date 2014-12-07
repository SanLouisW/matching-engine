package com.davidwales.matchingengine.input.di;

import java.util.Comparator;

import com.davidwales.matchingengine.messages.TagValueMessage;

public class TagValueMessageComparator implements Comparator<TagValueMessage>
{
    @Override
    public int compare(TagValueMessage x, TagValueMessage y)
    {
        if (x.getPrice() < y.getPrice())
        {
            return -1;
        }
        if (x.getPrice() > y.getPrice())
        {
            return 1;
        }
        return 0;
    }
}
