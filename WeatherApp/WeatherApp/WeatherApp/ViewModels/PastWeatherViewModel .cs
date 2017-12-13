using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.CompilerServices;
using WeatherApp.ServicesHandler;
using WeatherApp.Helpers;
using WeatherApp.Models;
using Microcharts;
using SkiaSharp;

namespace WeatherApp.ViewModels
{
    class PastWeatherViewModel : INotifyPropertyChanged
    {

        Services services = new Services();

        private LineChart chart;
 


        public LineChart Chart
        {
            get
            {   
                return chart;
            }

            set
            {
                chart = value;
                OnPropertyChanged();
            }

        }

        private DateTime minDate;
        public DateTime MinimumDate
        {
            get
            {
                minDate = DateTime.Now.AddDays(-30);
                return minDate;
                
            }
        }


        private DateTime maxDate;
        public DateTime MaximumDate
        {
            get
            {
                maxDate = DateTime.Now.AddDays(-1);
                return maxDate;
            }
        }


        private PastWeatherModel weatherModel;

        public PastWeatherModel PastWeatherModel
        {
            get { return weatherModel; }
            set
            {
                weatherModel = value;
                IsVisible = true;
                OnPropertyChanged();
            }
        }

        private List<string> _favCities;

        public List<string> Cities
        {
            get
            {
                string favouriteCities = Settings.FavouriteCitiesSettings;
                _favCities = new List<string>(favouriteCities.Split(','));
                return _favCities;
            }
        }


        private string city;

        public string City
        {
            get { return city; }
            set
            {
                city = value;
                if(date!=null)
                    InitializeGetWeatherAsync();
                OnPropertyChanged();
            }
        }

        private DateTime date = DateTime.Now.AddDays(-1);

        public DateTime Date
        {
            get { return date; }
            set
            {
                date = value;
                if(city!=null)
                    InitializeGetWeatherAsync();
                OnPropertyChanged();
            }
        }


        private bool _isBusy;

        public bool IsBusy
        {
            get { return _isBusy; }
            set
            {
                _isBusy = value;
                OnPropertyChanged();
            }
        }

        private bool _isVisible;
        public bool IsVisible
        {
            get
            {
                return _isVisible;
            }
            set
            {
                _isVisible = value;
                OnPropertyChanged();
            }
        }


        private async Task InitializeGetWeatherAsync()
        {
            try
            {
                IsBusy = true;
                PastWeatherModel = await services.GetPastWeather(city,date);

            }
            finally
            {
                IsBusy = false;

                var entries = new List<Microcharts.Entry>();

                List<Models.Hour> hours = weatherModel.NeededHours;
                int i = 0;
                foreach (Models.Hour hour in hours)
                {

                    entries.Add(new Microcharts.Entry((float)hour.temp_c)
                    {
                        Label = i+":00",
                        ValueLabel = hour.temp_c.ToString()+"ºC",
                        Color = SKColor.Parse("#266489")

                    });
                    i+=3;
                }
                Chart = new LineChart() { Entries = entries }; ;
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

    }
}
