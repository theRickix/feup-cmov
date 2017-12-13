using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using Microcharts;
using SkiaSharp;
using WeatherApp.ViewModels;

namespace WeatherApp
{
    public partial class PastWeather : ContentPage
    {
        public PastWeather()
        {
            InitializeComponent();
            var entries = new Microcharts.Entry[8];

            var vm = (PastWeatherViewModel)this.BindingContext;
            List<Models.Hour> hours = vm.PastWeatherModel.NeededHours;
            int i = 0;
            foreach (Models.Hour hour in hours) {
                
                entries[i] = new Microcharts.Entry((float)hour.temp_c)
                {
                    Label = hour.time,
                    ValueLabel= hour.temp_c.ToString(),
                    Color=SKColor.Parse("#266489")

                };
                    i++;
            }
            var chart = new LineChart() { Entries = entries };
            this.chartView.Chart = chart;
        }
    }
}
