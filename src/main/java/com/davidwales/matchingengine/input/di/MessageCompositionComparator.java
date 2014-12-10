package com.davidwales.matchingengine.input.di;

import java.util.Comparator;

import com.davidwales.matchingengine.messagecomposition.MessageComposition;
import com.davidwales.matchingengine.messagecomposition.MessageCompositionImpl;

public class MessageCompositionComparator implements Comparator<MessageComposition>
{
    @Override
    public int compare(MessageComposition x, MessageComposition y)
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
