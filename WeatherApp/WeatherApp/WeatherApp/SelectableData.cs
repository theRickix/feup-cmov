﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApp
{
    public class SelectableData<T>
    {

        public T Data { get; set; }

        public bool Selected { get; set; }

    }
}