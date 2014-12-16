package com.davidwales.matchingengine.inputorder;

import java.util.Comparator;

public class InputOrderComparator implements Comparator<InputOrder>
{
    @Override
    public int compare(InputOrder x, InputOrder y)
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
